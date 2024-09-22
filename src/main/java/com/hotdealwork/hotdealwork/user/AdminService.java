package com.hotdealwork.hotdealwork.user;

import com.hotdealwork.hotdealwork.board.Board;
import com.hotdealwork.hotdealwork.board.BoardRepository;
import com.hotdealwork.hotdealwork.reply.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    // 회원 정보 조회
    public List<SiteUser> getAllUsers() {
        return userRepository.findAll();
    }

    // 특정 회원 조회
    public SiteUser getUserById(Long userId) {
        Optional<SiteUser> user = userRepository.findById(userId);
        return user.orElse(null); // 사용자가 없을 경우 null 반환
    }

    // 계정 정지
    public void suspendUser(Long userId) {
        Optional<SiteUser> user = userRepository.findById(userId);
        if (user.isPresent()) {
            // 계정 정지 처리 로직 (ex: 활성 상태를 비활성으로 변경)
            SiteUser siteUser = user.get();
            siteUser.setActive(false); // `active` 필드를 SiteUser에 추가해야 함.
            userRepository.save(siteUser);
        }
    }

    // 신고된 게시물 조회 (신고 필드는 별도로 추가해야 할 수 있음)
    public List<Board> getReportedBoards() {
        return boardRepository.findReportedBoards(); // 신고된 게시물을 찾는 커스텀 쿼리 필요
    }

    // 게시물 삭제
    public void deleteBoard(Integer boardId) {
        boardRepository.deleteById(boardId);
    }

    // 댓글 삭제
    public void deleteReply(Long replyId) {
        replyRepository.deleteById(replyId);
    }

    // 공지사항 게시 (공지사항 게시 관련 새로운 엔티티 및 테이블 추가 필요)
    public void postAnnouncement(String title, String content) {
        // 공지사항 저장 로직 추가
    }
}