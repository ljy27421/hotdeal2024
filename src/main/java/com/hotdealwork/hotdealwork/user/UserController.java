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

    // 회원 정보 수정 페이지로 이동
    @GetMapping("/editProfile")
    public String editProfileForm(@RequestParam("username") String username,
                                  @RequestParam("password") String password,
                                  Model model) {
        // 유효한 사용자 인증
        if (userService.authenticateUser(username, password)) {
            SiteUser user = userService.getUser(username);
            model.addAttribute("user", user);
            return "edit_profile";  // 회원정보 수정 페이지로 이동
        } else {
            model.addAttribute("message", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "login_form";  // 로그인 폼으로 다시 이동
        }
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {
        if (userService.authenticateUser(username, password)) {
            // 인증 성공 후 회원정보 수정 페이지로 이동
            return "redirect:/user/editProfile?username=" + username + "&password=" + password;
        } else {
            model.addAttribute("message", "로그인에 실패했습니다. 아이디나 비밀번호를 확인하세요.");
            return "login_form";
        }
    }

    // 회원가입 페이지 이동
    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword().equals(userCreateForm.getCheckPassword())) {
            bindingResult.rejectValue("checkPassword", "passwordIncorrect", "비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(
                    userCreateForm.getUsername(),
                    userCreateForm.getEmail(),
                    userCreateForm.getPassword(),
                    userCreateForm.getNickname(),
                    userCreateForm.getSelectedQuestion(),
                    userCreateForm.getSecurityAnswer()
            );
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("duplicated ID", "이미 등록된 ID 혹은 이메일입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("duplicated ID", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

    // 비밀번호 찾기 페이지 이동
    @GetMapping("/findPassword")
    public String findPasswordForm() {
        return "find_password";
    }

    // 비밀번호 찾기 처리
    @PostMapping("/findPassword")
    public String findPassword(@RequestParam String username,
                               @RequestParam String selectedQuestion,
                               @RequestParam String securityAnswer,
                               Model model) {
        try {
            boolean isCorrectAnswer = userService.verifySecurityAnswer(username, selectedQuestion, securityAnswer);
            if (isCorrectAnswer) {
                model.addAttribute("message", "질문과 답변이 일치합니다. 비밀번호를 재설정하세요.");
                return "redirect:/user/resetPassword";  // 비밀번호 재설정 페이지로 이동
            } else {
                model.addAttribute("message", "질문 또는 답변이 일치하지 않습니다.");
            }
        } catch (Exception e) {
            model.addAttribute("message", "사용자를 찾을 수 없습니다.");
        }
        return "find_password";
    }

    // 비밀번호 재설정 페이지 이동
    @GetMapping("/resetPassword")
    public String resetPasswordForm() {
        return "reset_password";
    }

    // 비밀번호 재설정 처리
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String username,
                                @RequestParam String newPassword,
                                @RequestParam String confirmNewPassword,
                                Model model) {
        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            return "reset_password";
        }

        try {
            userService.updatePassword(username, newPassword);  // 비밀번호 업데이트
            model.addAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
        } catch (Exception e) {
            model.addAttribute("message", "비밀번호 변경에 실패했습니다.");
        }
        return "reset_password";
    }

    // 회원 탈퇴 페이지 이동
    @GetMapping("/delete")
    public String deleteForm() {
        return "delete_form";
    }

    // 회원 탈퇴 처리
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        boolean isDeleted = userService.deleteUser(username, password);

        if (isDeleted) {
            model.addAttribute("message", "회원 탈퇴가 성공적으로 완료되었습니다.");
            return "redirect:/";
        } else {
            model.addAttribute("message", "회원 탈퇴를 실패했습니다.");
            return "delete_form";
        }
    }
}