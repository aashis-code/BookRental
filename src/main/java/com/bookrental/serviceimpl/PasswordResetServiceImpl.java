package com.bookrental.serviceimpl;

import com.bookrental.dto.PasswordResetRequestDto;
import com.bookrental.exceptions.AppException;
import com.bookrental.helper.email.EmailDetails;
import com.bookrental.helper.email.EmailService;
import com.bookrental.model.Member;
import com.bookrental.model.PasswordResetToken;
import com.bookrental.repository.MemberRepo;
import com.bookrental.repository.PasswordResetRepo;
import com.bookrental.service.PasswordResetService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl extends GenericServiceImpl<PasswordResetToken, Integer> implements PasswordResetService {

    private final PasswordResetRepo passwordResetRepo;
    private final MemberRepo memberRepo;
    private final EmailService<PasswordResetToken> emailService;
    private final PasswordEncoder passwordEncoder;

    PasswordResetServiceImpl(PasswordResetRepo passwordResetRepo, MemberRepo memberRepo, EmailService emailService, PasswordEncoder passwordEncoder) {
        super(passwordResetRepo);
        this.passwordResetRepo = passwordResetRepo;
        this.memberRepo = memberRepo;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean generateAndSendToken(PasswordResetRequestDto passwordResetRequestDto) {
        Member member = this.memberRepo.findByEmail(passwordResetRequestDto.getEmail()).orElseThrow(() -> new RuntimeException(String.format("Member of email: %s does not exist.", passwordResetRequestDto.getEmail())));
        String token = tokenGenerator();
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .member(member)
                .build();
        passwordResetRepo.save(passwordResetToken);
        emailService.sendMailWithAttachment(EmailDetails.builder()
                .subject("Password Reset Token Verification.")
                .recipient(member.getEmail())
                .attachment("password-reset-token")
                .build(), passwordResetToken);

        return false;
    }

    @Override
    public boolean validateAndResetPassword(PasswordResetRequestDto passwordResetRequestDto) {
        PasswordResetToken passwordResetToken = this.passwordResetRepo.findByToken(passwordResetRequestDto.getToken()).orElseThrow(() -> new AppException(String.format("Request token %s is invalid.", passwordResetRequestDto.getToken())));

        if (Boolean.TRUE.equals(passwordResetToken.getDeleted())) {
            throw new AppException("Requested token is already used.");
        }
        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new AppException("Request token has already expired.");
        }
        String prevPassword = passwordResetToken.getMember().getPassword();
        String newPassword = this.passwordEncoder.encode(passwordResetRequestDto.getNewPassword());

        if (prevPassword.equals(newPassword)) {
            throw new AppException("Requested new password is already used.");
        }
        passwordResetToken.setDeleted(true);
        passwordResetToken.getMember().setPassword(newPassword);
        this.passwordResetRepo.save(passwordResetToken);
        return true;
    }

    public String tokenGenerator() {
        return UUID.randomUUID().toString().substring(0, 10);
    }
}
