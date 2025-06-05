package org.koreait.trend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;

@SpringBootTest
public class PythonExecutionTest {

    @Test
    void test1() throws Exception{
        ProcessBuilder builder = new ProcessBuilder("C:/trend/.venv/Scripts/activate.bat");
        Process process = builder.start();
        int statusCode = process.waitFor();
        if (statusCode!=0) return;

        builder = new ProcessBuilder("C:/trend/.venv/Scripts/python.exe", "C:/ternd/trend.py", "C:uploads/trend/");
        process = builder.start();
        statusCode = process.waitFor();
        System.out.println("statusCode:"+statusCode);
        BufferedReader br = process.errorReader();
        br.lines().forEach(System.out::println);
    }
}
