package com.teknisi.scheduler;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.expression.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.teknisi.model.AppUser;
import com.teknisi.model.Request;
import com.teknisi.model.Teknisi;
import com.teknisi.services.AppUserService;
import com.teknisi.services.FileService;
import com.teknisi.services.MessageService;
import com.teknisi.services.RequestService;
import com.teknisi.services.TeknisiService;

import net.sf.jasperreports.engine.JRException;

@Component
public class Scheduler {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired Environment environment;
	@Autowired AppUserService appUserService;
	@Autowired RequestService requestService;
	@Autowired TeknisiService teknisiService;
	@Autowired MessageService messageService;
	@Autowired FileService fileService;

//corn position meaning: second, minute, hour, day of month, month, day(s) of week
//	@Scheduled(cron = "10 * * * * *")
	@Scheduled(cron = "0 0/10 * * * *")
	public void emailReminderStatusNew() throws IOException {
		logger.info("Check all ticket request that has status new");
		List<Request> listRequest = requestService.showAllStatusRequest("NEW");
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
	}
	
	@Scheduled(fixedRate = 300000)
	public void emailReminderStatusMail_Sent() throws ParseException, java.text.ParseException {
		logger.info("Check all ticket request that has status mail_sent");
		List<Request> listRequest = requestService.showRequestByBeforeDate("MAIL_SENT");
		for (Request request : listRequest) {
			String message = environment.getProperty("mail.system.template.message");
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
	}

	@Scheduled(cron = "0 0 12 * * 1-5")
	public void emailReminderAllPendingStatus() throws IOException, MessagingException {
		logger.info("Check all ticket request that has status mail_sent, new and processed");
		logger.info("Exporting all data to CSV");
		fileService.exportToCSV();
		logger.info("Get latest CSV that will be send to Admin");
		List<AppUser> listAppUser = appUserService.showAllAppUserBasedOnRole("ADMIN");
		for (AppUser appUser : listAppUser) {
			messageService.sendEmailTicketRequestWithAttachment( appUser.getEmail(), appUser.getUsername(), ", Here Are The List of Pending Ticket Request", "reminder", "./csv");
		}
		logger.info("Schedule information for pending ticket request has been sent to admin email");
	}
	
	@Scheduled(cron = "0 0 17 * * 1-5")
	public void emailReportAllFinishedStatus() throws IOException, MessagingException, JRException {
		logger.info("Check all ticket request that has status Finished");
		logger.info("Exporting all data to PDF");
		fileService.exportToPDF();
		logger.info("Get latest PDF that will be send to Admin");
		List<AppUser> listAppUser = appUserService.showAllAppUserBasedOnRole("ADMIN");
		for (AppUser appUser : listAppUser) {
			messageService.sendEmailTicketRequestWithAttachment( appUser.getEmail(), appUser.getUsername(), ", Here Are The List of Finished Ticket Request", "report", "./pdf");
		}
		logger.info("Schedule report for finished ticket request has been sent to admin email");
	}
	
	@Scheduled(cron = "0 0 18 * * 5")
	public void emailRecapitulationReport() throws IOException, MessagingException, JRException {
		logger.info("Check all ticket request for a recapitulation");
		logger.info("Exporting all data to XLS");
		fileService.exportToXLS();
		logger.info("Get latest XLS that will be send to Admin");
		List<AppUser> listAppUser = appUserService.showAllAppUserBasedOnRole("ADMIN");
		for (AppUser appUser : listAppUser) {
			messageService.sendEmailTicketRequestWithAttachment( appUser.getEmail(), appUser.getUsername(), ", Here Are The List of Recapitulation Ticket Request", "recapitulation", "./xls");
		}
		logger.info("Schedule recapitulation report of ticket request has been sent to admin email");
	}
	
	@Scheduled(cron = "0 0 17 * * 5")
	public void emailBarchartRecapitulationReport() throws IOException, MessagingException, JRException {
		logger.info("Check all ticket request for a recapitulation");
		logger.info("Exporting all data to BarChart PDF");
		fileService.exportToBarChart();
		logger.info("Get latest BarChart PDF that will be send to Admin");
		List<AppUser> listAppUser = appUserService.showAllAppUserBasedOnRole("ADMIN");
		for (AppUser appUser : listAppUser) {
			messageService.sendEmailTicketRequestWithAttachment( appUser.getEmail(), appUser.getUsername(), ", Here Are The List of Recapitulation Ticket Request", "recapitulation", "./pdf/barchart");
		}
		logger.info("Schedule report for finished ticket request has been sent to admin email");
	}
}
