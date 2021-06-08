package com.teknisi.services;

public interface MessageService {
	void sendEmail(String email, String username);
	void sendEmailTicketRequest(String email, String name, String subject, String message);
}
