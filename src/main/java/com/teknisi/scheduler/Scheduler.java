package com.teknisi.scheduler;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.teknisi.model.Request;
import com.teknisi.model.Teknisi;
import com.teknisi.services.MessageService;
import com.teknisi.services.RequestService;
import com.teknisi.services.TeknisiService;

@Component
public class Scheduler {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired Environment environment;
	@Autowired RequestService requestService;
	@Autowired TeknisiService teknisiService;
	@Autowired MessageService messageService;

	@Scheduled(cron = "0 0/10 * * * *")
	public ResponseEntity<Object> sendEmailTicketRequest() throws IOException {
		logger.info("Check all ticket request that has status new");
		List<Request> listRequest = requestService.showAllStatusRequest("NEW", false);
		for (Request request : listRequest) {
			String message = environment.getProperty("mail.template.message");
			String formattedMessage = MessageFormat.format(message, request.getRequest_id(), 
					request.getMerchant_name(), request.getAddress(), request.getCity(), 
					request.getPerson_in_charge());
			logger.debug("Formatted Message {}" + formattedMessage);
			List<Teknisi> listTeknisi = teknisiService.getTeknisiById(request.getTeknisi_id());
			for(Teknisi teknisi : listTeknisi) {
				messageService.sendEmailTicketRequest(teknisi.getEmail(),teknisi.getName(), ", You have new ticket request!", formattedMessage);
				logger.debug("Send reminder ticket request to each Teknisi {} " + teknisi.getEmail() + teknisi.getName());
				requestService.updateStatusRequest(request);
				logger.info("The request status has been updated to status mail_sent");
				logger.debug("Request {}" + request);
			}
		}
		logger.info("Schedule reminder for ticket request status = new has been sent to email");
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}
	
	@Scheduled(fixedRate = 300000)
	public ResponseEntity<Object> sendEmailMailRequestV2() throws ParseException, java.text.ParseException {
		logger.info("Check all ticket request that has status mail_sent");
		List<Request> listRequest = requestService.showAllStatusRequest("MAIL_SENT", true);
		for (Request request : listRequest) {
			String message = environment.getProperty("mail.template.message");
			String formattedMessage = MessageFormat.format(message, request.getRequest_id(), 
					request.getMerchant_name(), request.getAddress(), request.getCity(), 
					request.getPerson_in_charge());
			logger.debug("Formatted Message {}" + formattedMessage);
			List<Teknisi> listTeknisi = teknisiService.getTeknisiById(request.getTeknisi_id());
			for(Teknisi teknisi : listTeknisi) {
				messageService.sendEmailTicketRequest(teknisi.getEmail(),teknisi.getName(), ", Please processnew request", formattedMessage);
				logger.debug("Send reminder ticket request to each Teknisi {} " + teknisi.getEmail() + teknisi.getName());
			}
		}
		logger.info("Schedule reminder for ticket request status = mail_sent has been sent to email");
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}
}
