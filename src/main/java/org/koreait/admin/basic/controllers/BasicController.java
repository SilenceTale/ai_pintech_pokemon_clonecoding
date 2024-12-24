package org.koreait.admin.basic.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.admin.global.menu.MenuDetail;
import org.koreait.admin.global.menu.Menus;
import org.koreait.global.annotations.ApplyErrorPage;
import org.koreait.global.entities.SiteConfig;
import org.koreait.global.services.CodeValueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@ApplyErrorPage
@RequiredArgsConstructor
@RequestMapping("/admin/basic")
public class BasicController {

    private final CodeValueService codeValueService;

    @ModelAttribute("menuCode")
    public String menuCode() {
        return "basic";
    }

    @ModelAttribute("submenus")
    public List<MenuDetail> submenus() {
        return Menus.getMenus(menuCode());
    }

    @GetMapping({"", "/siteConfig"})
    public String siteConfig(Model model) {
        commonProcess("siteConfig", model);

        SiteConfig form = codeValueService.get("siteConfig", SiteConfig.class);
        model.addAttribute("siteConfig", form);

        return "admin/basic/siteConfig";
    }

    @PatchMapping("/siteConfig")
    public String siteConfigPs(SiteConfig form, Model model) {
        commonProcess("siteConfig", model);

        codeValueService.save("siteConfig", form);

        return "admin/basic/siteConfig"; // 임시
    }

    /**
     * 기본설정 공통 처리 부분
     *
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model) {

        model.addAttribute("subMenuCode", mode);
    }
}