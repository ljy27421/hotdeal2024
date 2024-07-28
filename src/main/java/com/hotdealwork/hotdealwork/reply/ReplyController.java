package com.hotdealwork.hotdealwork.reply;

import com.hotdealwork.hotdealwork.board.Board;
import com.hotdealwork.hotdealwork.board.BoardService;
import com.hotdealwork.hotdealwork.commu.Commu;
import com.hotdealwork.hotdealwork.commu.CommuService;
import com.hotdealwork.hotdealwork.user.SiteUser;
import com.hotdealwork.hotdealwork.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/replys")
public class ReplyController {

    private final ReplyService replyService;
    private final BoardService boardService;
    private final UserService userService;
    private final CommuService commuService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write/{boardId}")
    public String writeReply(@PathVariable("boardId") Integer boardId, @RequestParam(name = "content") String content, Principal principal) {
        Board board = boardService.getBoard(boardId);
        SiteUser author = userService.getUser(principal.getName());
        replyService.create(board, author, content);
        return "redirect:/board/view?id=" + boardId;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{replyId}")
    public String updateReply(@PathVariable("replyId") Long replyId, @RequestParam(name = "content") String content) {
        Reply reply = replyService.getReplyById(replyId).orElseThrow();
        replyService.updateReply(reply, content);
        return "redirect:/board/view?id=" + reply.getBoard().getId();
    }

    @PostMapping("/delete/{replyId}")
    public String deleteReply(@PathVariable("replyId") Long replyId) {
        Reply reply = replyService.getReplyById(replyId).orElseThrow();
        replyService.deleteReply(reply);
        return "redirect:/board/view?id=" + reply.getBoard().getId();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/writec/{commuId}")
    public String writeCommuReply(@PathVariable("commuId") Integer commuId, @RequestParam(name = "content") String content, Principal principal) {
        Commu commu = commuService.getCommu(commuId);
        SiteUser author = userService.getUser(principal.getName());
        replyService.commuCreate(commu, author, content);
        return "redirect:/commu/view?id=" + commuId;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/updatec/{replyId}")
    public String updateCommuReply(@PathVariable("replyId") Long replyId, @RequestParam(name = "content") String content) {
        Reply reply = replyService.getReplyById(replyId).orElseThrow();
        replyService.updateReply(reply, content);
        return "redirect:/commu/view?id=" + reply.getCommu().getId();
    }

    @PostMapping("/deletec/{replyId}")
    public String deleteCommuReply(@PathVariable("replyId") Long replyId) {
        Reply reply = replyService.getReplyById(replyId).orElseThrow();
        replyService.deleteReply(reply);
        return "redirect:/commu/view?id=" + reply.getCommu().getId();
    }
}
