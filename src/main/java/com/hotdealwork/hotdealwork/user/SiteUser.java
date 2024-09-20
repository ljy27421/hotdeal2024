package com.hotdealwork.hotdealwork.user;

import com.hotdealwork.hotdealwork.board.Board;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Data
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String password;

//    private List<Double> interestVector;

    private List<Integer> interest;

    // 계정 활성 상태 필드 추가 (기본값: true)
    private boolean active = true;

    @ManyToMany
    Set<Board> likes;

    // 닉네임 필드 추가
    private String nickname;

    // 비밀번호 찾기 질문 필드 추가 (선택된 질문과 답변)
    private String selectedQuestion;  // 선택된 질문
    private String securityAnswer;    // 질문에 대한 답변


    // 계정 활성 상태 설정 메서드
    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}