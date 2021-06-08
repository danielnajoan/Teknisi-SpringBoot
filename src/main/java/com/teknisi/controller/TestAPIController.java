package com.teknisi.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.teknisi.model.Request;
import com.teknisi.model.Teknisi;
import com.teknisi.services.AppUserService;
import com.teknisi.services.MessageService;
import com.teknisi.services.RequestService;
import com.teknisi.services.TeknisiService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiOperation(value = "/test", tags = "Test Service Profile Controller")
@RestController
@Configuration
@PropertySource("classpath:test.properties")
public class TestAPIController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired MessageService messageService;
	
	@Autowired AppUserService appUserService;
	
	@Autowired RequestService requestService;
	
	@Autowired TeknisiService teknisiService;
	
	@Autowired Environment environment;
	
	@ApiOperation(value = "Fetch All New Request Data")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Iterable.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/test/showAllNewRequest", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveAllNewRequest() {
		List<Request> listRequest = requestService.showAllNewRequest();
		logger.info("Retrieve all new request");
		logger.debug("All new request: {}", listRequest);
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Send All New Request Data To Teknisi")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Iterable.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/test/sendEmailTicketRequest", method = RequestMethod.GET)
	public ResponseEntity<Object> sendEmailTicketRequest() throws IOException {
		List<Request> listRequest = requestService.showAllNewRequest();
		for (Request request : listRequest) {
			Long teknisi_id = request.getTeknisi_id();
			List<Teknisi> listTeknisi = teknisiService.getTeknisiById(teknisi_id);
			for(Teknisi teknisi : listTeknisi) {
				String request_id = request.getRequest_id();
				FileInputStream in = new FileInputStream("../../../test.properties");
				Properties props = new Properties();
				props.load(in);

				FileOutputStream out = new FileOutputStream("test.properties");
				props.setProperty("request.request_id", request_id);
				props.store(out, request_id);
				messageService.sendEmailTicketRequest(teknisi.getEmail(), teknisi.getName(), 
						request.getRequest_id(), request.getMerchant_name(), request.getAddress(),
						request.getCity(), request.getPerson_in_charge());
			}
		}
		logger.info("Send all new request to each Teknisi");
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}
}
