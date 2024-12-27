package org.koreait.admin.global.menu;

import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public interface SubMenus {
    String menuCode();

    @ModelAttribute("submenus") // 이 명령어만 사용할 수 있게 Repository 안에 코드 넣음
    default List<MenuDetail> submenus() {
        return Menus.getMenus(menuCode());
    }
}
