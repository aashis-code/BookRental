package com.bookrental.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CustomMessageSource {

private final MessageSource messageSource;


public String get(String code, Object... objects) {
return messageSource.getMessage(code, objects, Locale.ENGLISH);
}
}
