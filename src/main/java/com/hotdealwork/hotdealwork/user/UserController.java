package com.hotdealwork.hotdealwork.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")

public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword().equals(userCreateForm.getCheckPassword())) {
            bindingResult.rejectValue("checkPassword","passwordIncorrect","비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getEmail(), userCreateForm.getPassword());
        }catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("duplicated ID", "이미 등록된 ID 혹은 이메일 입니다.");
            return "signup_form";
        }catch (Exception e){
            e.printStackTrace();
            bindingResult.reject("duplicated ID", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/delete")
    public String deleteForm() {
        return "delete_form";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        boolean isDeleted = userService.deleteUser(username, password);

        if (isDeleted) {
            model.addAttribute("message", "회원 탈퇴가 성공적으로 완료되었습니다.");
            return "redirect:/"; // 회원탈퇴가 성공하면 홈으로 리다이렉트
        } else {
            model.addAttribute("message", "회원 탈퇴를 실패했습니다.");
            return "delete_form";
        }
    }
}
