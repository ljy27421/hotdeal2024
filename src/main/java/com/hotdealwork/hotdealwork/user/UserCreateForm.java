package com.hotdealwork.hotdealwork.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateForm {

    @Size(min = 3, max = 25)
    @NotEmpty(message = "ID를 입력하세요.")
    private String username;

    @NotEmpty(message = "비밀번호를 입력하세요.")
    private String password;

    @NotEmpty(message = "비밀번호 확인을 입력하세요.")
    private String checkPassword;

    @NotEmpty(message = "이메일을 입력하세요.")
    @Email
    private String email;

    @NotEmpty(message = "닉네임을 입력하세요.")
    private String nickname;

    // 비밀번호 찾기 질문 선택 필드
    @NotEmpty(message = "비밀번호 찾기 질문을 선택하세요.")
    private String selectedQuestion;

    // 선택한 질문에 대한 답변 필드
    @NotEmpty(message = "비밀번호 찾기 질문의 답변을 입력하세요.")
    private String securityAnswer;
}