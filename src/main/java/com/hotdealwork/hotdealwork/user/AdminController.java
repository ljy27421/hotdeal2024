package com.hotdealwork.hotdealwork.user;

import com.hotdealwork.hotdealwork.board.Board;  // Board 클래스 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 모든 회원 조회
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<SiteUser> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    // 신고된 게시물 조회
    @GetMapping("/reportedBoards")
    public String getReportedBoards(Model model) {
        List<Board> reportedBoards = adminService.getReportedBoards();
        model.addAttribute("boards", reportedBoards);
        return "reportedBoards";
    }
}