package org.koreait.koreait.admin.global.menu;

import org.koreait.admin.global.menu.MenuDetail;
import org.koreait.admin.global.menu.Menus;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public interface SubMenus {
    String menuCode();

    @ModelAttribute("submenus")
    default List<MenuDetail> submenus() {
        return Menus.getMenus(menuCode());
    }
}
