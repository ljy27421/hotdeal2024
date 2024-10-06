package com.hotdealwork.hotdealwork.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserUpdateForm {
    @NotEmpty(message = "아이디를 입력하세요.")
    private String username;

    @NotEmpty(message = "닉네임을 입력하세요.")
    private String nickname;

    @NotEmpty(message = "이메일을 입력하세요.")
    @Email
    private String email;
}
