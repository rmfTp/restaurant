package org.koreait.admin.global.menus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menus {
    private static Map<String, List<Menu>> menus = new HashMap<>();

    static {
        menus.put("config",List.of(new Menu("basic","기본설정", "/admin/config")));
        menus.put("terms",List.of(new Menu("terms","약관설정", "/admin/terms")));
        menus.put("member",List.of(new Menu("list","회원목록", "/admin/member")));
        menus.put("trend",List.of(
            new Menu("news","뉴스", "/admin/trend"),
            new Menu("etc","기타", "/admin/trend/etc")
        ));
        menus.put("product",List.of(
                new Menu("list", "상품목록","/admin/product"),
                new Menu("register", "상품등록","/admin/product/register")
        ));
    }
    /**
     * 주 메뉴로 서브 메뉴 조회
     */
    public static List<Menu> getMenus(String mainCode) {
        return menus.getOrDefault(mainCode,List.of());
    }



}
