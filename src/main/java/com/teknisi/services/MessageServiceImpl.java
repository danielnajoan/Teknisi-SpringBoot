package com.teknisi.services;

import java.io.IOException;
import java.io.InputStream;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class MessageServiceImpl implements MessageService{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired private JavaMailSender javaMailSender;
    @Autowired private SpringTemplateEngine templateEngine;
	
	@Value("${template.mail.subject:Hello }")
	private String templateSubject;

	@Value("${template.mail.message:Testing Spring Boot Application}")
	private String templateMessage;
	

    @Override
    public void sendEmail(String email, String username) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(templateSubject+username);
        msg.setText(templateMessage);
        javaMailSender.send(msg);
    }
    
    @Override
    public void sendEmailTicketRequest(String email, String name,String subject, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(name+subject);
        msg.setText(message);
        javaMailSender.send(msg);
    }
    @Override
    public void sendEmailTicketRequestWithAttachment(String email, String name, String subject, String templateName, String fileName, InputStream inputStream) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        
        Context context = new Context();
        logger.info("setVariable for html");
        context.setVariable("name", name);
        logger.info("Check template");
        String html = templateEngine.process(templateName, context);
        logger.info("Making the email");
        helper.setTo(email);
        helper.setSubject(name+subject);
        helper.setText(html, true);
        helper.addAttachment(fileName, new ByteArrayResource(IOUtils.toByteArray(inputStream)));
        javaMailSender.send(msg);
    }
}