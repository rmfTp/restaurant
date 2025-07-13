package org.koreait.trend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.global.configs.FileProperties;
import org.koreait.global.configs.PythonProperties;
import org.koreait.trend.entities.CollectedTrend;
import org.koreait.trend.entities.Trend;
import org.koreait.trend.repositories.TrendRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Lazy
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties({PythonProperties.class, FileProperties.class})
public class NewsTrendService {
    private final PythonProperties properties;
    private final FileProperties fileProperties;

    private final WebApplicationContext ctx;
    private final TrendRepository repository;
    private final HttpServletRequest request;
    private final ObjectMapper om;

    public CollectedTrend process(){
        boolean isProduction = Arrays.stream(ctx.getEnvironment().getActiveProfiles())
                .anyMatch(s -> s.equals("prod"));

        //실행환경에 따른 실행 명령어 입력
        String activationCommend = null, pythonPath = null;
        if (isProduction){
            activationCommend = String.format("source %s/activate", properties.getBase());
            pythonPath = properties.getBase() + "/python";
        }else {
            activationCommend = String.format("%s/activate.bat", properties.getBase());
            pythonPath = properties.getBase() + "/python.exe";
        }

        try{
            ProcessBuilder builder = new ProcessBuilder(activationCommend);//가상환경 활성화
            Process process = builder.start();
            if(process.waitFor() == 0)/*실행성공시*/{
                builder = new ProcessBuilder(pythonPath,
                        properties.getTrend() + "/trend.py",
                        fileProperties.getPath() + "/trend"
                );
                process = builder.start();
                int statusCode = process.waitFor();
                if (statusCode == 0){
                    String json = process.inputReader().lines().collect(Collectors.joining());
                    return om.readValue(json, CollectedTrend.class);
                }else {
//                    System.out.println("statusCode:"+statusCode);
//                    process.errorReader().lines().forEach(System.out::println);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 매 시간마다 뉴스 트렌드 데이터 저장
     */
    @Scheduled(fixedRate = 1L, timeUnit = TimeUnit.HOURS)
    public void scheduledJob() throws JsonProcessingException {
        CollectedTrend item = process();
        if (item == null)return;

        String wordCloud = String.format("%s%s/trend/%s", request.getContextPath(), fileProperties.getUrl(), item.getImage());

        try {
            String keywords = om.writeValueAsString(item.getKeywords());
            Trend data = new Trend();
            data.setCategory("NEWS");
            data.setWordCloud(wordCloud);
            data.setKeywords(keywords);
            repository.save(data);
        }catch (JsonProcessingException e){}
    }
}
