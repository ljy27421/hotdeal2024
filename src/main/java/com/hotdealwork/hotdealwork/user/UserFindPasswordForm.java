package com.hotdealwork.hotdealwork.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserFindPasswordForm {

    @NotEmpty(message = "사용자명을 입력하세요.")
    private String username;

    @NotEmpty(message = "비밀번호 찾기 질문을 선택하세요.")
    private String selectedQuestion;

    @NotEmpty(message = "답변을 입력하세요.")
    private String securityAnswer;
}