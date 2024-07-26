package com.hotdealwork.hotdealwork.board;

import com.hotdealwork.hotdealwork.reply.ReplyService;
import com.hotdealwork.hotdealwork.user.SiteUser;
import com.hotdealwork.hotdealwork.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ReplyService replyService;

    @GetMapping("/")
    public String boardHome() {

        return "boardhome";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm() {

        return "boardwrite";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/board/writePro")
    public String boardWritePro(Board board, Model model, Principal principal,
                                @RequestParam(name = "files", required = false) List<MultipartFile> files) throws Exception{

        boardService.boardWrite(board, files, userService.getUser(principal.getName()));

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("URL", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC ) Pageable pageable,
                            @RequestParam(name = "searchKeyword", required = false) String searchKeyword,
                            @RequestParam(name = "category", required = false) String category,
                            @RequestParam(name = "searchType", required = false) String searchType,
                            @RequestParam(name = "hot", required = false, defaultValue = "0") int hot) {

        Page<Board> list = boardService.boardList(searchKeyword, category, searchType, hot, pageable);

        int curPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(curPage - 4, 1);
        int endPage = Math.min(curPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("curPage", curPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("category", category);
        model.addAttribute("searchType", searchType);
        model.addAttribute("hot", hot);

        return "boardlist";
    }

    @GetMapping("/board/view") //localhost:8080/board/view?id=1
    public String boardView(Model model, @RequestParam(name="id") Integer id) {

        model.addAttribute("board", boardService.boardView(id));
        model.addAttribute("replys", replyService.getReplyByBoard(boardService.getBoard(id)));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(@RequestParam(name="id") Integer id) {

        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){

        Board board = boardService.boardView(id);

        model.addAttribute("board", board);

        return "boardmodify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board,
                              @RequestParam(name = "files", required = false) List<MultipartFile> files,
                              @RequestParam(name = "deleteImageIds", required = false) List<Long> deleteImageIds) throws Exception{

        Board boardTemp = boardService.boardView(id);

        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardTemp.setCategory(board.getCategory());

        if(deleteImageIds != null) {
            boardService.deleteImages(deleteImageIds);
        }

        boardService.boardWrite(boardTemp, files, board.getAuthor());

        return "redirect:/board/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/like/{id}")
    public String boardLike(Principal principal, @PathVariable("id") Integer id) {
        Board board = boardService.getBoard(id);
        SiteUser siteUser = userService.getUser(principal.getName());
        this.boardService.boardLike(board, siteUser);
        System.out.println("liked!");

        return String.format("redirect:/board/view?id=%s", id);
    }
}
