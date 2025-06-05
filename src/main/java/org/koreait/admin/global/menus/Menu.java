package org.koreait.admin.global.menus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Menu {
    private String code;
    private String name;
    private String link;
}
