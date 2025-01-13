package com.bookrental.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequestDto {

    private String email;

    private String newPassword;

    private String token;
}
