package com.hotdealwork.hotdealwork.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserResetPasswordForm {

    @NotEmpty(message = "사용자명을 입력하세요.")
    private String username;

    @NotEmpty(message = "새 비밀번호를 입력하세요.")
    private String newPassword;

    @NotEmpty(message = "새 비밀번호 확인을 입력하세요.")
    private String confirmNewPassword;
}