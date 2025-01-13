package com.bookrental.service;

import com.bookrental.dto.PasswordResetRequestDto;
import com.bookrental.model.PasswordResetToken;

public interface PasswordResetService extends GenericService<PasswordResetToken, Integer>{

    boolean generateAndSendToken(PasswordResetRequestDto passwordResetRequestDto);
    boolean validateAndResetPassword(PasswordResetRequestDto passwordResetRequestDto);
}
