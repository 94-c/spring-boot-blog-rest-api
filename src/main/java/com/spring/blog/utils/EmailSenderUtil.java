package com.spring.blog.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSenderUtil {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        javaMailSender.send(simpleMailMessage);
    }

}
