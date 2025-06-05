package org.koreait.trend.services;

import lombok.RequiredArgsConstructor;
import org.koreait.global.search.CommonSearch;
import org.koreait.trend.entities.Trend;
import org.koreait.trend.exceptions.TrendNotFoundException;
import org.koreait.trend.repositories.TrendRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class TrendInfoService {
    private final TrendRepository repository;

    /**
     * 가장 최근
     * @param category
     * @return
     */
    public Trend getLastest(String category){
        Trend item = repository.getLatest(category).orElseThrow(TrendNotFoundException::new);
        return item;
    }

    /**
     * 특정 날짜
     * @param date
     * @param category
     * @return
     */
    public Trend get(String category, LocalDate date){
        return null;
    }

    /**
     * 특정 날짜 범위
     * @return
     */
    public List<Trend> getList(String category, CommonSearch search){
        return null;
    }
}
