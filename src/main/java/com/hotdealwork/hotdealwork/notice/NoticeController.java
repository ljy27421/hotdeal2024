package com.hotdealwork.hotdealwork.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/notice/create")
    public String createNoticeForm() {
        return "createNotice";
    }

    @PostMapping("/notice/create")
    public String createNotice(@RequestParam String title, @RequestParam String content, Model model) {
        noticeService.createNotice(title, content);
        model.addAttribute("message", "공지사항이 생성되었습니다.");
        model.addAttribute("URL", "/");
        return "message";
    }
}
