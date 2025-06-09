package org.koreait.global.search;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommonSearch {
    private LocalDate sDate;
    private LocalDate eDate;
    private String sopt;
    private String skey;
    private int page;
    private int limit;
}
