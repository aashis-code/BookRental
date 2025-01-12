package com.bookrental.helper.email;

import com.bookrental.model.Book;
import com.bookrental.model.BookTransaction;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details, BookTransaction bookTransaction);
}
