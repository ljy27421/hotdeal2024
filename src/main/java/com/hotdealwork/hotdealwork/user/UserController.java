package com.hotdealwork.hotdealwork.user;

import com.hotdealwork.hotdealwork.board.BoardRepository;
import com.hotdealwork.hotdealwork.commu.CommuRepository;
import com.hotdealwork.hotdealwork.reply.ReplyRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommuRepository commuRepository;
    private final ReplyRepository replyRepository;

    // 로그인 페이지 이동 (GET 요청)
    @GetMapping("/login")
    public String loginForm() {
        return "login_form";  // login_form.html 파일로 이동
    }

    // 정지확인
    @GetMapping("/isSuspended")
    public String isSuspended(Principal principal, Model model) {
        SiteUser user = userService.getUser(principal.getName());

        if (user != null && user.isSuspended()){
            model.addAttribute("message","정지된 사용자입니다. \n정지 사유: "+user.getSuspensionReason());
            model.addAttribute("URL","/user/login");
            return "message";
        }
        return "redirect:/";
    }


    // 비밀번호 확인 페이지로 이동
    @GetMapping("/verifyPassword")
    public String showVerifyPasswordPage(@RequestParam(name="username") String username, Model model) {
        model.addAttribute("username", username);
        return "verifyPassword";  // verifyPassword.html 파일로 이동
    }

    // 비밀번호 확인 처리
    @PostMapping("/verifyPassword")
    public String verifyPassword(@RequestParam("username") String username,
                                 @RequestParam("password") String password, Model model) {
        if (userService.authenticateUser(username, password)) {
            return "redirect:/user/editProfile?username=" + username;  // 성공 시 프로필 수정 페이지로 리다이렉트
        } else {
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            return "verifyPassword";  // 비밀번호 확인 페이지로 다시 이동
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public String mypage(Principal principal, Model model) {
        SiteUser user = userService.getUser(principal.getName());
        model.addAttribute("user", user);
        return "mypage";
    }

    // 회원 정보 수정 페이지로 이동
    @PreAuthorize("isAuthenticated()") //로그인 여부 체크
    @GetMapping("/editProfile")
    public String editProfile(Principal principal, Model model, UserUpdateForm userUpdateForm) {
        // 사용자 정보 조회
        SiteUser user = userService.getUser(principal.getName());
        model.addAttribute("user", user);

        return "edit_profile";  // 회원정보 수정 페이지로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/updateProfile")
    public String updateProfile(Principal principal, Model model, UserUpdateForm form){

        SiteUser user = userService.getUser(principal.getName());
        user.setEmail(form.getEmail());
        user.setNickname(form.getNickname());

        this.userRepository.save(user);

        model.addAttribute("message","수정이 완료되었습니다.");
        model.addAttribute("URL","/");
        return "message";
    }

    // 아이디 찾기 페이지 이동
    @GetMapping("/findId")
    public String findIdForm() {
        return "find_id";  // find_id.html 파일로 이동
    }

    // 아이디 찾기 처리
    @PostMapping("/findId")
    public String findId(@RequestParam(name = "email") String email, Model model) {
        try {
            String foundUsername = userService.findUsernameByEmail(email);
            model.addAttribute("message", "해당 이메일로 등록된 ID는 " + foundUsername + "입니다.");
            model.addAttribute("URL", "/#");
        } catch (Exception e) {
            model.addAttribute("message", "해당 이메일로 등록된 사용자를 찾을 수 없습니다.");
            model.addAttribute("URL", "/user/findId");
        }
        return "message";  // 결과를 표시하기 위해 다시 find_id.html로 돌아감
    }

    // 회원가입 페이지 이동
    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";  // signup_form.html 파일로 이동
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword().equals(userCreateForm.getCheckPassword())) {
            bindingResult.rejectValue("checkPassword","passwordIncorrect","비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }

        if (userService.isUsernameExist(userCreateForm.getUsername())) {
            bindingResult.rejectValue("username", "duplicated", "이미 존재하는 아이디입니다.");
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
            bindingResult.reject("duplicated ID", "이미 등록된 ID 혹은 이메일 입니다.");
            return "signup_form";
        }catch (Exception e){
            e.printStackTrace();
            bindingResult.reject("duplicated ID", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";  // 회원가입 성공 시 메인 페이지로 리다이렉트
    }

    // 비밀번호 찾기 페이지 이동
    @GetMapping("/findPassword")
    public String findPasswordForm() {
        return "find_password";  // find_password.html 파일로 이동
    }

    // 비밀번호 찾기 처리
    @PostMapping("/findPassword")
    public String findPassword(@RequestParam(name="username") String username,
                               @RequestParam(name="selectedQuestion") String selectedQuestion,
                               @RequestParam(name="securityAnswer") String securityAnswer,
                               HttpSession session,
                               Model model) {
        try {
            boolean isCorrectAnswer = userService.verifySecurityAnswer(username, selectedQuestion, securityAnswer);
            if (isCorrectAnswer) {
                model.addAttribute("message", "질문과 답변이 일치합니다. 비밀번호를 재설정하세요.");
                model.addAttribute("URL","/user/resetPassword");
                session.setAttribute("username", username); // 비밀번호 재설정 페이지로 이동
            } else {
                model.addAttribute("message", "질문 또는 답변이 일치하지 않습니다.");
                model.addAttribute("URL","/user/findPassword"); // 비밀번호 찾기 페이지로 다시 이동
            }
        } catch (Exception e) {
            model.addAttribute("message", "사용자를 찾을 수 없습니다.");
            model.addAttribute("URL","/user/findPassword"); // 비밀번호 찾기 페이지로 다시 이동
        }
        return "message";
    }

    // 비밀번호 재설정 페이지 이동
    @GetMapping("/resetPassword")
    public String resetPasswordForm() {
        return "reset_password";  // reset_password.html 파일로 이동
    }

    // 비밀번호 재설정 처리
    @PostMapping("/resetPassword")
    public String resetPassword(HttpSession session,
                                @RequestParam(name="newPassword") String newPassword,
                                @RequestParam(name="confirmNewPassword") String confirmNewPassword,
                                Model model) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            model.addAttribute("message", "사용자 정보가 유효하지 않습니다.");  // username이 null인 경우 처리
            model.addAttribute("URL","/user/findPassword"); // 비밀번호 찾기 페이지로 다시 이동
        }
        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("URL","/user/resetPassword"); // 비밀번호 재설정 페이지로 다시 이동
            return "message";
        }

        try {
            userService.updatePassword(username, newPassword);  // 비밀번호 업데이트
            model.addAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
            model.addAttribute("URL","/user/login"); // 로그인 페이지로 이동
            session.removeAttribute("username");
        } catch (Exception e) {
            model.addAttribute("message", "비밀번호 변경에 실패했습니다.");
            model.addAttribute("URL","/user/resetPassword"); // 비밀번호 재설정 페이지로 다시 이동
        }
        return "message";  // 비밀번호 재설정 페이지로 다시 이동
    }


    // 회원 탈퇴 처리
    @PostMapping("/delete")
    public String deleteUser(Principal principal, RedirectAttributes redirectAttributes) {
        SiteUser user = userService.getUser(principal.getName());
        if (user != null) {
            user.getBoards().forEach(board -> boardRepository.delete(board));
            user.getCommus().forEach(commu -> commuRepository.delete(commu));
            user.getReplies().forEach(reply -> replyRepository.delete(reply));
            userRepository.delete(user); // 사용자를 삭제합니다.
            redirectAttributes.addFlashAttribute("message", "회원 탈퇴가 완료되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("error", "사용자를 찾을 수 없습니다.");
        }

        return "redirect:/user/logout";
    }
    @Controller
    public class UserProfileController {

        @GetMapping("/mypage/")
        public String myPage() {
            return "mypage"; // templates/mypage.html로 이동
        }
    }
}

