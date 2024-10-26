package com.hotdealwork.hotdealwork.commu;

import com.hotdealwork.hotdealwork.board.BoardDTO;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/commu")
public class CommuController {

    @Autowired
    private CommuService commuService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ReplyService replyService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write") //localhost:8080/commu/write
    public String commuWriteForm(Model model, Principal principal) {
        if (principal != null) {
            SiteUser loggedUser = userService.getUser(principal.getName());
            model.addAttribute("loggedUser", loggedUser);
        }
        return "commuwrite";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/writePro")
    public String commuWritePro(Commu commu, Model model, Principal principal,
                                @RequestParam(name = "files", required = false) List<MultipartFile> files) throws Exception{

        commuService.commuWrite(commu, files, userService.getUser(principal.getName()));

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("URL", "/commu/list");

        return "message";
    }

    @GetMapping("/list")
    public String commuList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC ) Pageable pageable,
                            @RequestParam(name = "searchKeyword", required = false) String searchKeyword,
                            @RequestParam(name = "category", required = false) String category,
                            @RequestParam(name = "searchType", required = false) String searchType,
                            @RequestParam(name = "hot", required = false, defaultValue = "0") int hot) {

        Page<CommuDTO> list = commuService.commuList(searchKeyword, category, searchType, hot, pageable);
        Page<CommuDTO> notice = commuService.commuNotice(pageable);

        int curPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(curPage - 4, 1);
        int endPage = Math.min(curPage + 5, list.getTotalPages());

        model.addAttribute("notice", notice);
        model.addAttribute("list", list);
        model.addAttribute("curPage", curPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("category", category);
        model.addAttribute("searchType", searchType);
        model.addAttribute("hot", hot);

        return "commulist";
    }

    @GetMapping("/view") //localhost:8080/commu/view?id=1
    public String commuView(Model model, Principal principal, @RequestParam(name="id") Integer id) {

        model.addAttribute("commu", commuService.getCommu(id));
        model.addAttribute("replys", replyService.getReplyByCommu(commuService.getCommu(id)));
        commuService.commuIncreaseViewCount(commuService.getCommu(id));

        if (principal != null) {
            SiteUser loggedUser = userService.getUser(principal.getName());
            model.addAttribute("loggedUser", loggedUser);
        }

        return "commuview";
    }

    @GetMapping("/delete")
    public String commuDelete(@RequestParam(name="id") Integer id, Model model) {

        commuService.commuDelete(id);
        model.addAttribute("message", "글을 삭제했습니다.");
        model.addAttribute("URL", "/commu/list");

        return "message";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String commuModify(@PathVariable("id") Integer id, Model model, Principal principal){

        Commu commu = commuService.getCommu(id);
        if (principal != null) {
            SiteUser loggedUser = userService.getUser(principal.getName());
            model.addAttribute("loggedUser", loggedUser);
        }

        model.addAttribute("commu", commu);

        return "commumodify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{id}")
    public String commuUpdate(Model model,
                              @PathVariable("id") Integer id, Commu commu, Principal principal,
                              @RequestParam(name = "files", required = false) List<MultipartFile> files,
                              @RequestParam(name = "deleteImageIds", required = false) List<Long> deleteImageIds) throws Exception{

        Commu commuTemp = commuService.getCommu(id);

        commu.setView(commuTemp.getView());
        commu.setLiked(commuTemp.getLiked());
        if(deleteImageIds != null) {
            commuService.deleteImages(deleteImageIds);
        }
        commu.setImages(commuTemp.getImages());

        commuService.commuWrite(commu, files, userService.getUser(principal.getName()));

        model.addAttribute("message", "글이 수정되었습니다.");
        model.addAttribute("URL", "/commu/list");

        return "message";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{id}")
    public String commuLike(Principal principal, @PathVariable("id") Integer id, Model model) {
        Commu commu = commuService.getCommu(id);
        SiteUser siteUser = userService.getUser(principal.getName());

        if (siteUser.getCommuLikes() == null){
            siteUser.setCommuLikes(new ArrayList<>());//            siteUser.setInterestVector(new ArrayList<>(Collections.nCopies(1536,0.0)));
        }

        if (siteUser.getCommuLikes().contains(commu.getId())){
            model.addAttribute("message", "이미 추천한 글입니다.");
        } else {
            model.addAttribute("message", "글을 추천했습니다.");
            this.commuService.commuLike(commu, siteUser);
        }
        model.addAttribute("URL", String.format("/commu/view?id=%s", id));

        return "message";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/dislike/{id}")
    public String commuDislike(Principal principal, @PathVariable("id") Integer id, Model model) {
        Commu commu = commuService.getCommu(id);
        SiteUser siteUser = userService.getUser(principal.getName());

        if (siteUser.getCommuDislikes() == null){
            siteUser.setCommuDislikes(new ArrayList<>());//            siteUser.setInterestVector(new ArrayList<>(Collections.nCopies(1536,0.0)));
        }

        if (siteUser.getCommuDislikes().contains(commu.getId())){
            model.addAttribute("message", "이미 비추천한 글입니다.");
        } else {
            model.addAttribute("message", "글을 비추천했습니다.");
            this.commuService.commuDislike(commu, siteUser);
        }
        model.addAttribute("URL", String.format("/commu/view?id=%s", id));

        return "message";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/report/{id}")
    public String reportPost(@PathVariable("id") Integer id, Model model) {
        commuService.reportPost(id);
        model.addAttribute("message", "신고가 완료되었습니다.");
        model.addAttribute("URL", "/commu/view?id="+id);
        return "message";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/unreport/{id}")
    public String unreportPost(@PathVariable("id") Integer id, Model model) {
        commuService.unreportPost(id);
        model.addAttribute("message", "신고가 해제되었습니다.");
        model.addAttribute("URL", "/commu/view?id="+id);
        return "message";
    }

    // 신고된 게시글 목록 조회
    @GetMapping("/reported")
    public String viewReportedPosts(Model model) {
        model.addAttribute("reportedPosts", commuService.getReportedPosts());
        return "reportedCommu";
    }
    // 신고된 게시글 삭제
    @PostMapping("/deleteReported/{id}")
    public String deleteReportedPost(@PathVariable Integer id, Model model) {
        commuService.deleteReportedPost(id);
        model.addAttribute("message", "신고된 글이 삭제되었습니다.");
        model.addAttribute("URL", "/commu/reported");
        return "message";
    }
}

