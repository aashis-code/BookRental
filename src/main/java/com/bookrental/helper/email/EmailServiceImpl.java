package com.bookrental.helper.email;

import com.bookrental.exceptions.AppException;
import com.bookrental.model.Book;
import com.bookrental.model.BookTransaction;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private final TemplateEngine templateEngine;

    @Override
    public String sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(sender);
            message.setTo(details.getRecipient());
            message.setSubject(details.getSubject());
            message.setText(details.getMsgBody());

            mailSender.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
//            throw new AppException("Error happen while sending mail.");
        }
        return "";
    }

    @Override
    public String sendMailWithAttachment(EmailDetails details, BookTransaction bookTransaction) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            Context context = new Context();
            context.setVariable("bookTransaction", bookTransaction);
//            String imageName = bookTransaction.getBook().getPhoto().substring(bookTransaction.getBook().getPhoto().lastIndexOf("\\")+1);
            context.setVariable("imageName", bookTransaction.getBook().getPhoto().substring(bookTransaction.getBook().getPhoto().lastIndexOf("\\")+1));

            String htmlContent = templateEngine.process(details.getAttachment(), context);

            helper.setFrom(sender);
            helper.setTo(details.getRecipient());
            helper.setSubject(details.getSubject());
            helper.setText(htmlContent , true);

            mailSender.send(mimeMessage);
        } catch (Exception ex) {
            ex.printStackTrace();
//            throw new AppException("Error happen while sending mail.");
        }
        return "";
    }
}
