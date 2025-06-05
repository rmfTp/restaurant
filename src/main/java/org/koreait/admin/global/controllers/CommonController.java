package org.koreait.admin.global.controllers;

import org.koreait.admin.global.menus.Menu;
import org.koreait.admin.global.menus.Menus;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public abstract class CommonController {
    public abstract String mainCode();//주메뉴 코드, 각 컨트롤러에서 설정

    @ModelAttribute("subMenus")
    public List<Menu> subMenus(){
        return Menus.getMenus(mainCode());
    }
}
