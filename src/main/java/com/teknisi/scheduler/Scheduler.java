package com.teknisi.scheduler;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
		List<Request> listRequest = requestService.showAllStatusRequest("NEW");
		for (Request request : listRequest) {
			Long teknisi_id = request.getTeknisi_id();
			String request_id = request.getRequest_id();
			String merchant_name = request.getMerchant_name();
			String address = request.getAddress();
			String city = request.getCity();
			String pic = request.getPerson_in_charge();
			String message = environment.getProperty("mail.template.message");
			String formattedMessage = MessageFormat.format(message, request_id, merchant_name, address, city, pic);
			List<Teknisi> listTeknisi = teknisiService.getTeknisiById(teknisi_id);
			for(Teknisi teknisi : listTeknisi) {
				messageService.sendEmailTicketRequest(teknisi.getEmail(),teknisi.getName(), ", You have new ticket request!", formattedMessage);
				requestService.updateStatusRequest(request);
			}
		}
		logger.info("Send all new request to each Teknisi");
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}
	
	@Scheduled(fixedRate = 300000)
	public ResponseEntity<Object> sendEmailMailRequestV2() throws ParseException, java.text.ParseException {
		List<Request> listRequest = requestService.showAllStatusRequest("MAIL_SENT");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		logger.info("check {} " + now);
        Calendar calender = Calendar.getInstance();
        calender.setTime(now);
        calender.add(Calendar.HOUR, -6);
        Date currentDate = calender.getTime();
		for (Request request : listRequest) {
			Date createdDate = sdf.parse(request.getCreated_date().toString());
			logger.info("check {} " + createdDate);
			if(createdDate.before(currentDate)) {
				Long teknisi_id = request.getTeknisi_id();
				String request_id = request.getRequest_id();
				String merchant_name = request.getMerchant_name();
				String address = request.getAddress();
				String city = request.getCity();
				String pic = request.getPerson_in_charge();
				String message = environment.getProperty("mail.template.message");
				String formattedMessage = MessageFormat.format(message, request_id, merchant_name, address, city, pic);
				List<Teknisi> listTeknisi = teknisiService.getTeknisiById(teknisi_id);
				for(Teknisi teknisi : listTeknisi) {
					messageService.sendEmailTicketRequest(teknisi.getEmail(),teknisi.getName(), ", Please processnew request", formattedMessage);
				}
			}
		}
		logger.info("Send all new request to each Teknisi => " + now);
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}
}
