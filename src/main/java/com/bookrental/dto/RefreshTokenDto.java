package com.bookrental.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RefreshTokenDto {

    @NotNull(message = "Token must not be null.")
    private String refreshToken;
}
