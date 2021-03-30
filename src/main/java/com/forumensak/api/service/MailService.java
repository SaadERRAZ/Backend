package com.forumensak.api.service;

import com.forumensak.api.exception.EmailSendingException;
import com.forumensak.api.payload.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j //used to log info or errorerrrr
public class MailService {
    private final JavaMailSender javaMailSender;
    @Async
    public void sendEmail(NotificationEmail notificationEmail)
    {
        MimeMessagePreparator messagePreparator= mimeMessage->{
            MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("forum");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody(),true);
        };
        try
        {
            javaMailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        }
        catch (MailException e)
        {
            log.error("Exception occurred when sending mail", e);
            throw new EmailSendingException("Exception occured when sending mail to "+notificationEmail.getRecipient(),e);
        }
    }
}