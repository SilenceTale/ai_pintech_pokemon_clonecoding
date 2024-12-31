package org.koreait.message.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.global.annotations.ApplyErrorPage;
import org.koreait.global.libs.Utils;
import org.koreait.message.validators.MessageValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@ApplyErrorPage
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final Utils utils;
    private final MessageValidator messageValidator;

    @ModelAttribute("addCss")
    public List<String> addCss() {
        return List.of("message/style");
    }
    /**
     * 쪽지 작성 양식
     * 겟 방식일때 양식을 불러옴
     * @return
     */
    @GetMapping
    public String form(@ModelAttribute RequestMessage form, Model model) {
        commonProcess("send", model); // 공통 기능 처리를 위한 코드(commonProcess) 추가

        form.setGid(UUID.randomUUID().toString()); // 그룹 ID 발급에 필요함

        return utils.tpl("message/form");
    }

    /**
     * 쪽지 작성
     * 양식에 대한 처리
     * @return
     */
    @PostMapping // @Valid 어노테이션 넣은 이유는 커맨드 객체 검증
    public String process(@Valid RequestMessage form, Errors errors, Model model) {
        commonProcess("send", model);

        messageValidator.validate(form, errors); // 추가 오류에 대한 검증

        if (errors.hasErrors()) {
            return utils.tpl("message/form");
        }

        return "redirect:/message/list";
    }

    /**
     * 보낸거나 받은 쪽지 목록
     *
     * @return
     */
    @GetMapping("/list")
    public String list(Model model) {
        commonProcess("list", model);

        return utils.tpl("message/list");
    }

    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq, Model model) {
        commonProcess("view", model);

        return utils.tpl("message/view");
    }

    @DeleteMapping
    public String delete(@RequestParam(name="seq", required = false) List<String> seq) {

        return "redirect:/message/list";
    }

    /**
     * 컨트롤러 공통 처리 코드 ( 공통 처리 부분 )
     *
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list"; // hasText에 mode 가 있다면 mode 값을, 없다면 "list" 값 주입
        String pageTitle = ""; // pageTitle 안에 비어있는 문자열 삽입
        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("send")) { // 쪽지 보내기
            pageTitle = utils.getMessage("쪽지_보내기");
            addCommonScript.add("fileManager");
            addCommonScript.add("ckeditor5/ckeditor");
            addScript.add("message/send");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
    }
}
