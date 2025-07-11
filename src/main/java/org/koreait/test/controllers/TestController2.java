package org.koreait.test.controllers;

import org.koreait.global.exceptions.BadRequestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test2")
public class TestController2 {
    @GetMapping
    public String test1() {
         boolean result = false;
         if (!result) {
             throw new BadRequestException("테스트 예외발생!");
         }
        return "test2";
    }
}
