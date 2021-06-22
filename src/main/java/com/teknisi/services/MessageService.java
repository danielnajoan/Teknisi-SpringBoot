package com.teknisi.services;

import java.io.IOException;

import javax.mail.MessagingException;

public interface MessageService {
	void sendEmail(String email, String username);
	void sendEmailTicketRequest(String email, String name, String subject, String message);
	void sendEmailTicketRequestWithAttachment(String email, String name, String subject, String templateName, String fileName, byte[] attachment) throws MessagingException, IOException;
}
