package com.teknisi.services;

public interface MessageService {
	void sendEmail(String email, String username);
	void sendEmailTicketRequest(String email, String name, String request_id, String merchant_name, String address, String city, String pic);
}
