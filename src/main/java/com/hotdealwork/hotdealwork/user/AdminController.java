package com.hotdealwork.hotdealwork.user;

import com.hotdealwork.hotdealwork.board.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 모든 회원 조회
    @GetMapping("/users")
    public String getAllUsers(Model model, @AuthenticationPrincipal SiteUser currentUser) {
        List<SiteUser> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser); // 현재 사용자 정보 추가
        return "users";  // src/main/resources/templates/users.html 사용
    }

    // 신고된 게시물 조회
    @GetMapping("/reportedBoards")
    public String getReportedBoards(Model model) {
        List<Board> reportedBoards = adminService.getReportedBoards();
        model.addAttribute("boards", reportedBoards);
        return "reportedBoards";  // src/main/resources/templates/reportedBoards.html 사용
    }

    // 사용자 계정 정지
    @PostMapping("/suspendUser")
    public String suspendUser(@RequestParam("userId") Long userId, @RequestParam("reason") String reason) {
        adminService.suspendUser(userId, reason);  // 사용자 정지 처리
        return "redirect:/admin/users";  // 처리 후 사용자 목록 페이지로 리다이렉트
    }

    // 사용자 계정 정지 해제
    @PostMapping("/unsuspendUser")
    public String unsuspendUser(@RequestParam("userId") Long userId) {
        adminService.unsuspendUser(userId);  // 사용자 정지 해제 처리
        return "redirect:/admin/users";  // 처리 후 사용자 목록 페이지로 리다이렉트
    }

    // 게시물 삭제
    @PostMapping("/deleteBoard")
    public String deleteBoard(@RequestParam("boardId") Integer boardId) {
        adminService.deleteBoard(boardId);  // 게시물 삭제 처리
        return "redirect:/admin/reportedBoards";  // 처리 후 신고된 게시물 페이지로 리다이렉트
    }

    // 회원 정보 수정 페이지로 이동
    @GetMapping("/editProfile/{userId}")
    public String editProfile(@PathVariable("userId") Long userId,
                              Model model,
                              @AuthenticationPrincipal SiteUser currentUser) {
        SiteUser user = adminService.getUserById(userId);
        model.addAttribute("user", user);
        return "edit_profile";  // src/main/resources/templates/edit_profile.html 사용
    }

    // 관리자 메뉴 페이지로 이동 (로그인 없이 접근 가능)
    @GetMapping("/adminMenu")
    public String adminMenu(Model model) {
        model.addAttribute("currentUser", new SiteUser()); // 기본 사용자 정보 추가 (null 처리)
        return "adminMenu";  // adminMenu.html 반환
    }

    // 계정 정지 페이지로 이동
    @GetMapping("/suspendUsers")
    public String suspendUsers(Model model) {
        List<SiteUser> users = adminService.getAllUsers(); // 모든 사용자 정보 가져오기
        model.addAttribute("users", users); // 사용자 목록 추가
        return "suspendUsers"; // suspendUsers.html 반환
    }
}