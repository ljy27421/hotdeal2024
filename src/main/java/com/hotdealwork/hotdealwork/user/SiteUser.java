package com.hotdealwork.hotdealwork.user;

import com.hotdealwork.hotdealwork.board.Board;
import com.hotdealwork.hotdealwork.commu.Commu;
import com.hotdealwork.hotdealwork.reply.Reply;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    private String password;

    private List<Integer> interest;
    private List<Integer> likes;

    // 계정 활성 상태 필드 추가 (기본값: true)
    private Boolean active = true;

    private List<Integer> dislikes;
    private String nickname;
    private List<Integer> commuLikes;

    // 비밀번호 찾기 질문 필드 추가 (선택된 질문과 답변)
    private String selectedQuestion;  // 선택된 질문
    private String securityAnswer;    // 질문에 대한 답변

    private List<Integer> commuDislikes;

    // 계정 정지 상태 필드 추가 (기본값: false)
    private Boolean suspended = false;

    // 계정 정지 사유 필드 추가
    @Column(length = 255)
    private String suspensionReason;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commu> commus;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies;


    // 계정 정지 상태 설정 메서드
    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public boolean isSuspended() {
        return suspended != null && suspended; // null 체크 후 boolean 반환
    }

    // 정지 사유 설정 및 조회 메서드
    public void setSuspensionReason(String suspensionReason) {
        this.suspensionReason = suspensionReason;
    }

    public String getSuspensionReason() {
        return suspensionReason;
    }
}