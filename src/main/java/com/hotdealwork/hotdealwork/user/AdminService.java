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

    // 모든 회원 조회
    public List<SiteUser> getAllUsers() {
        return userRepository.findAll();
    }

    // 특정 회원 조회
    public SiteUser getUserById(Long userId) {
        Optional<SiteUser> user = userRepository.findById(userId);
        return user.orElse(null); // 사용자가 없을 경우 null 반환
    }

    // 계정 정지 또는 활성화
    public void suspendUser(Long userId) {
        Optional<SiteUser> user = userRepository.findById(userId);
        if (user.isPresent()) {
            SiteUser siteUser = user.get();
            siteUser.setActive(!siteUser.isActive()); // 현재 활성 상태를 반전시킴
            userRepository.save(siteUser); // 변경된 상태를 저장
        }
    }

    // 신고된 게시물 조회
    public List<Board> getReportedBoards() {
        return boardRepository.findReportedBoards(); // 신고된 게시물 커스텀 쿼리 사용
    }

    // 게시물 삭제
    public void deleteBoard(Integer boardId) {
        if (boardRepository.existsById(boardId)) { // 게시물 존재 확인
            boardRepository.deleteById(boardId);
        } else {
            throw new IllegalArgumentException("게시물이 존재하지 않습니다."); // 존재하지 않는 게시물 삭제 시 예외 처리
        }
    }

    // 댓글 삭제
    public void deleteReply(Long replyId) {
        replyRepository.deleteById(replyId);
    }

    // 공지사항 게시
    public void postAnnouncement(String title, String content) {

    }
}