package com.hotdealwork.hotdealwork.home;

import com.hotdealwork.hotdealwork.board.Board;
import com.hotdealwork.hotdealwork.board.BoardService;
import com.hotdealwork.hotdealwork.commu.Commu;
import com.hotdealwork.hotdealwork.commu.CommuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class homeController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private CommuService commuService;

    @GetMapping("/")
    public String home(Model model) {
        List<Board> board = boardService.boardMainHot();
        List<Commu> commu = commuService.commuMainHot();

        model.addAttribute("board", board);
        model.addAttribute("commu", commu);

        return "home";
    }
}
