package com.vvi.btb.service.impl;

import com.vvi.btb.domain.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.vvi.btb.constant.UserImplConstant.*;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(User user){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject(ADMIN_REGISTER_EMAIL);
        simpleMailMessage.setText(body(user));
        javaMailSender.send(simpleMailMessage);
    }

    private String body(User user) {
        return " Hi " + user.getFirstName()+ " "+user.getLastName() + "you are" +
                " registered Successfully with username" + user.getUserName() +
                "and password " + user.getPassword() + " happy joining " + BANSAL_TRADERS;
    }

}
