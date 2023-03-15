package com.example.labshop.service;

import com.example.labshop.model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    @Value("${spring.mail.username}")
    private String emailSenderValue;
    private final MailSender mailSender;

    public MailSenderService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendActivationMessage(UserModel userModel){
        String messageTemplate = String.format(
                """
                Привет, %s!
                Перейди по ссылке ниже, чтобы подтвердить свою эл.почту:
                http://localhost:8080/activate-email/{code}
                
                С уважением, Магазин Канцелярских Товаров:)
                """,
                userModel.getUsername()
        );
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userModel.getEmail());
        mailMessage.setFrom(emailSenderValue);
        mailMessage.setText(messageTemplate);

        mailSender.send(mailMessage);
    }
}
