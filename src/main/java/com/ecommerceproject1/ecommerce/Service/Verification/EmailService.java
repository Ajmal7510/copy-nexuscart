package com.ecommerceproject1.ecommerce.Service.Verification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendVerificationEmail(String to, String otp) {
//        Context context = new Context();
//        context.setVariable("otp", otp);
//        String body = templateEngine.process("otp-view", context);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Otp Verification Mail");
        message.setText("user otp is:"+otp+" expire 5 minutes");
        javaMailSender.send(message);
    }


}
