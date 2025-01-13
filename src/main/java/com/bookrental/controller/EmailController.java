package com.bookrental.controller;

import com.bookrental.helper.email.EmailDetails;
import com.bookrental.helper.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
public class EmailController {

    private final  EmailService emailService;

    EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("")
    public String sendMail(@RequestBody EmailDetails details)
    {
        return emailService.sendSimpleMail(details);
    }

    @PostMapping("/send-mail-with-attachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details)
    {
//        return emailService.sendMailWithAttachment(details);
        return null;
    }
}
