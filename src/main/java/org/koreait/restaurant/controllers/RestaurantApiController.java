package org.koreait.restaurant.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.koreait.restaurant.entities.Restaurant;
import org.koreait.restaurant.repositories.RestaurantRepository;
import org.koreait.restaurant.services.RestaurantInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantApiController {
    private final RestaurantRepository repository;
    private final RestaurantInfoService infoService;
    private final ObjectMapper om;

    // 카테고리 검색 API
    @GetMapping("/searchByCategories")
    public List<Restaurant> searchByCategories(@RequestParam List<String> categories) {
        System.out.println("검색 카테고리: " + categories);
        return repository.findAll().stream()
                .filter(r -> categories.contains(r.getCategory()))
                .toList();
    }

    // 최근접 검색 API
    @GetMapping("/search")
    public List<Restaurant> search(@ModelAttribute RestaurantSearch search) {
        return infoService.getNearest(search);
    }

    // 전체 데이터 학습 API
    @GetMapping("/train")
    public List<Restaurant> train() {
        return repository.findAll();
    }
}
