package com.hotdealwork.hotdealwork.controller;

import com.hotdealwork.hotdealwork.Service.BoardService;
import com.hotdealwork.hotdealwork.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String boardHome() {

        return "boardhome";
    }

    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm() {

        return "boardwrite";
    }

    @PostMapping("/board/writePro")
    public String boardWritePro(Board board) throws Exception{

        boardService.boardWrite(board);

        return "redirect:/board/list";
    }

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC ) Pageable pageable,
                            @RequestParam(name = "searchKeyword",required = false) String searchKeyword,
                            @RequestParam(name = "category",required = false) String category){

        Page<Board> list = null;

        if(StringUtils.hasText(searchKeyword) && StringUtils.hasText(category)) {
            list = boardService.boardCategorySearchList(searchKeyword, category, pageable);
        } else if(StringUtils.hasText(searchKeyword) && !StringUtils.hasText(category)) {
            list = boardService.boardSearchList(searchKeyword, pageable);
        } else if(!StringUtils.hasText(searchKeyword) && StringUtils.hasText(category)) {
            list = boardService.boardCategoryList(category, pageable);
        } else {
            list = boardService.boardList(pageable);
        }

        int curPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(curPage - 4, 1);
        int endPage = Math.min(curPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("curPage", curPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("category", category);

        return "boardlist";
    }

    @GetMapping("/board/view") //localhost:8080/board/view?id=1
    public String boardView(Model model, @RequestParam(name="id") Integer id) {

        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(@RequestParam(name="id") Integer id) {

        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){

        model.addAttribute("board", boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board){

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardTemp.setCategory(board.getCategory());
        boardService.boardWrite(boardTemp);
        return "redirect:/board/list";
    }
}
