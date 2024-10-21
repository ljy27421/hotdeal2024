package com.hotdealwork.hotdealwork.board;

import com.hotdealwork.hotdealwork.embedding.EmbeddingService;
import com.hotdealwork.hotdealwork.image.ImageRepository;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmbeddingService embeddingService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ReplyService replyService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String boardWriteForm() {
        return "boardwrite";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/writePro")
    public String boardWritePro(Board board, Model model, Principal principal,
                                @RequestParam(name = "files", required = false) List<MultipartFile> files) throws Exception {
        boardService.boardWrite(board, files, userService.getUser(principal.getName()));
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("URL", "/board/list");
        return "message";
    }

    @GetMapping("/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(name = "searchKeyword", required = false) String searchKeyword,
                            @RequestParam(name = "category", required = false) String category,
                            @RequestParam(name = "searchType", required = false) String searchType,
                            @RequestParam(name = "hot", required = false, defaultValue = "0") int hot) {

        Page<BoardDTO> list = boardService.boardList(searchKeyword, category, searchType, hot, pageable);
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

    @GetMapping("/view")
    public String boardView(Model model, Principal principal, @RequestParam(name = "id") Integer id) {
        model.addAttribute("board", boardService.getBoard(id));
        model.addAttribute("replys", replyService.getReplyByBoard(boardService.getBoard(id)));
        boardService.boardIncreaseViewCount(boardService.getBoard(id));

        if (principal != null) {
            SiteUser loggedUser = userService.getUser(principal.getName());
            model.addAttribute("loggedUser", loggedUser);
        }

        return "boardview";
    }

    @GetMapping("/delete")
    public String boardDelete(@RequestParam(name = "id") Integer id, Model model) {
        boardService.boardDelete(id);
        model.addAttribute("message", "글을 삭제했습니다.");
        model.addAttribute("URL", "/board/list");
        return "message";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {
        Board board = boardService.getBoard(id);
        model.addAttribute("board", board);
        return "boardmodify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{id}")
    public String boardUpdate(Model model,
                              @PathVariable("id") Integer id, Board board, Principal principal,
                              @RequestParam(name = "files", required = false) List<MultipartFile> files,
                              @RequestParam(name = "deleteImageIds", required = false) List<Long> deleteImageIds) throws Exception {
        Board boardTemp = boardService.getBoard(id);
        board.setView(boardTemp.getView());
        board.setLiked(boardTemp.getLiked());
        board.setEmbeddingVector(boardTemp.getEmbeddingVector());

        if (deleteImageIds != null) {
            boardService.deleteImages(deleteImageIds);
        }
        board.setImages(boardTemp.getImages());

        boardService.boardWrite(board, files, userService.getUser(principal.getName()));
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("URL", "/board/list");

        return "message";
    }

    // 1. 신고 버튼 클릭 시 완료 메시지 출력
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/report/{id}")
    public String reportPost(@PathVariable("id") Integer id, Model model) {
        boardService.reportPost(id);
        model.addAttribute("message", "신고가 완료되었습니다.");
        model.addAttribute("URL", "/board/view?id="+id);
        return "message";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/unreport/{id}")
    public String unreportPost(@PathVariable("id") Integer id, Model model) {
        boardService.unreportPost(id);
        model.addAttribute("message", "신고가 해제되었습니다.");
        model.addAttribute("URL", "/board/view?id="+id);
        return "message";
    }

    // 2. 신고된 게시글 목록 페이지를 /admin/reportedBoards로 이동
    @GetMapping("/reported")
    public String reportedPosts(Model model) {
        model.addAttribute("boards", boardService.getReportedBoards());
        return "admin/reportedBoards";  // 이동 경로 변경
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/interest/{id}")
    public String boardInterest(Model model, Principal principal, @PathVariable("id") Integer id) {
        Board board = boardService.getBoard(id);
        SiteUser siteUser = userService.getUser(principal.getName());
        this.boardService.boardInterest(board, siteUser);

        if (siteUser.getInterest().contains(id)) {
            model.addAttribute("message", "글을 관심 목록에 추가했습니다.");
        } else {
            model.addAttribute("message", "글을 관심 목록에서 제거했습니다.");
        }
        model.addAttribute("URL", String.format("/board/view?id=%s", id));

        return "message";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/recommand")
    public String boardRecommand(Model model, Principal principal) {
        SiteUser siteUser = userService.getUser(principal.getName());
        List<Board> recommandedBoards = embeddingService.recommandBoards(siteUser);
        model.addAttribute("recommandedBoards", recommandedBoards);
        return "boardrecommand";
    }
}

