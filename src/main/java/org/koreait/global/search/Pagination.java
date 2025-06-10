package org.koreait.global.search;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@ToString
public class Pagination {
    private int page;
    private int total;
    private int range;
    private int limit;
    private int firstRangePage;
    private int lastRangePage;
    private int prevRangePage;
    private int nextRangePage;
    private int lastPage;
    private String baseUrl;

    /**
     *
     */
    public Pagination(int page, int total, int range, int limit) {
        this(page,total,range,limit,null);
    }
    public Pagination(int page, int total) {
        this(page,total,0,0,null);
    }
    public Pagination(int page, int total, int range, int limit, HttpServletRequest request){
        page = Math.max(page, 1);
        range = range < 1 ? 10 : range;
        limit = limit < 1 ? 20 : limit;

        if (total < 1) return;

        // 전체 페이지 갯수
        int totalPages = (int)Math.ceil(total / (double)limit);

        // 페이지 구간 번호 - 0, 1, 2 ...
        int rangeNum = (page - 1) / range;
        int firstRangePage = rangeNum * range + 1; // 현재 구간에서의 첫번째 페이지 번호
        int lastRangePage = firstRangePage + range - 1; // 현재 구간에서의 마지막 페이지 번호
        lastRangePage = Math.min(totalPages, lastRangePage); // 마지막 페이지는 총 페이지의 갯수 이하

        // 0이면 해당 값이 없다.
        int prevRangePage = rangeNum > 0 ? firstRangePage - 1 : 0;
        int lastPageNum = (totalPages - 1) / range;
        int nextRangePage = rangeNum < lastPageNum ? lastRangePage + 1 : 0;

        // 쿼리스트링
        String qs = request == null ? "" : request.getQueryString();
        String baseUrl = "?";
        if (StringUtils.hasText(qs)){
            baseUrl += Arrays.stream(qs.split("&")).filter(s -> !s.contains("page=")).collect(Collectors.joining("&"));
        }
        baseUrl = "page=";

        this.page = page;
        this.total = total;
        this.range = range;
        this.limit = limit;
        this.lastPage = totalPages;
        this.firstRangePage = firstRangePage;
        this.lastRangePage = lastRangePage;
        this.prevRangePage = prevRangePage;
        this.nextRangePage = nextRangePage;
        this.baseUrl = baseUrl;
    }

    /**
     * String 배열의 [0] = 페이지번호
     * String 배열의 [0] = ?page=번호  와 같은 쿼리스트링
     * @return
     */
    public List<String[]> getPages(){
        return total < 1 ? List.of() : IntStream.rangeClosed(firstRangePage, lastRangePage)
                .mapToObj(i -> new String[]{""+i,baseUrl+i}).toList();
    }
}
