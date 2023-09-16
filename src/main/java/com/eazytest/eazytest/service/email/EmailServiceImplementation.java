package com.eazytest.eazytest.service.email;

import com.eazytest.eazytest.dto.email.EmailDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import java.io.File;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.lang.String;

@Service
public class EmailServiceImplementation implements EmailServiceInterface{

    private JavaMailSender javaMailSender;
//    private ModelMapper modelMapper;

    public EmailServiceImplementation(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${spring.mail.username}")
    private String mailSender;


    @Override
    public String sendMessageWithAttachment(EmailDetails emailDetails) {
        try {
            //first tap into javaMailSender.createMimeMessage() to create Mime Message

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            // tap into  MimeMessageHelper.
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mailSender);
            mimeMessageHelper.setTo(emailDetails.getRecipient());
            mimeMessageHelper.setSubject(emailDetails.getSubject());
            mimeMessageHelper.setText(emailDetails.getMessageBody());

            //to handle attachment
            FileSystemResource fileSystemResource = new FileSystemResource(new File(emailDetails.getAttachment()));
            mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

            //tap into javaMailSender to send message
            javaMailSender.send(mimeMessage);
            return "Mail sent successfully.";

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendSimpleMessage(EmailDetails emailDetails) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(mailSender);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setSubject(emailDetails.getSubject());
            mailMessage.setText(emailDetails.getMessageBody());

            javaMailSender.send(mailMessage);
            return "Mail sent successfully.";
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }
}
