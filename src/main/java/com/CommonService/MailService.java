package com.CommonService;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sngv on 10/02/15.
 */

@RestController
@Service("mailService")
public class MailService {


    private MailSender mailSender;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String from, String to, String subject, String msg) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(msg);
            mailSender.send(message);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
