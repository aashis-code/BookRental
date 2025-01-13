package com.bookrental.helper.email;

public interface EmailService<T> {

    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details, T t);
}
