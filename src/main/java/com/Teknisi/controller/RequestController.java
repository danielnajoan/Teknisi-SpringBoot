package com.teknisi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teknisi.model.Request;
import com.teknisi.services.MessageService;
import com.teknisi.services.RequestService;
import com.teknisi.services.TeknisiService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiOperation(value = "/request", tags = "Request Profile Controller")
@RestController
public class RequestController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired Environment environment;
	@Autowired RequestService requestService;
	@Autowired TeknisiService teknisiService;
	@Autowired MessageService messageService;
	
	@ApiOperation(value = "Fetch All Request Data")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Iterable.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/request/showAllRequest", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveAllRequest() {
		List<Request> listRequest = requestService.showAllRequest();
		logger.info("Retrieve all request");
		logger.debug("All request: {}", listRequest);
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Fetch All New Request Data")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Iterable.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/request/showAllNewRequest", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveAllNewRequest() {
		List<Request> listRequest = requestService.showAllStatusRequest("NEW",false);
		logger.info("Retrieve all new request");
		logger.debug("All new request: {}", listRequest);
		return new ResponseEntity<>(listRequest, HttpStatus.OK);
	}
	

	@ApiOperation(value = "Fetch Request by ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Request.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/request/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveRequestById(@Valid @PathVariable("id") String id) {
		logger.info("Retrieving request by id");
		logger.debug("id : {}", id);
		if(requestService.isRequestIdExists(id) == true) {
			Request request = requestService.getRequestById(id);
			logger.info("Retrieve request");
			logger.debug("Retrieve request: {}", request);
			return new ResponseEntity<>(request, HttpStatus.OK);
		}else if (requestService.isRequestIdExists(id) == false ) {
			logger.error("Request with id {} not exist", id);
			return new ResponseEntity<>("Request ID did not exist", HttpStatus.BAD_REQUEST);
		}else {
			logger.error("Request id cannot be empty");
			return new ResponseEntity<>("Request ID cannot be empty", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Create Request")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data has been created"),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/request/create", method = RequestMethod.POST)
	public ResponseEntity<Object> createRequest(@Valid @RequestBody Request request) {
		logger.info("Creating request");
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			logger.debug("Input {}", objectMapper.writeValueAsString(request));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String id = request.getRequest_id();
		long teknisi_id = request.getTeknisi_id();
		if(requestService.isRequestIdExists(id) != true && teknisiService.isTeknisiIdExists(teknisi_id) == true && request.getRequest_id() != null) {
			requestService.insertRequest(request);
			logger.debug("Create Request: {}", request);
			logger.info("Request Created Successsfully");
			return new ResponseEntity<>("Request Created Successsfully", HttpStatus.OK);
		}else if(requestService.isRequestIdExists(id) == true) {
			logger.error("Request with id {} already exist", id);
			return new ResponseEntity<>("Request ID already exist", HttpStatus.BAD_REQUEST);
		}else if(teknisiService.isTeknisiIdExists(teknisi_id) != true){
			logger.error("Teknisi with id {} did not exist", teknisi_id);
			return new ResponseEntity<>("Teknisi ID is not exist", HttpStatus.BAD_REQUEST);
		}else if (request.getRequest_id() == null || request.getTeknisi_id() == 0) {
			logger.error("Request ID cannot be empty or Teknisi ID cannot be zero");
			return new ResponseEntity<>("Request ID cannot be empty or Teknisi ID cannot be zero", HttpStatus.BAD_REQUEST);
		}else {
			logger.error("Check your input again");
			return new ResponseEntity<>("Check your input again", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Update Request")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data has been updated"),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/request/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateRequest(@Valid @RequestBody Request request) {
		String id = request.getRequest_id();
		long teknisi_id = request.getTeknisi_id();
		logger.info("Updating request");
		logger.debug("id : {}", id);
		if(requestService.isRequestIdExists(id) == true && teknisiService.isTeknisiIdExists(teknisi_id) == true && request.getRequest_id() != null) {
			requestService.updateRequest(request);
			logger.debug("Update Request: {}", request);
			logger.info("Request Updated Successsfully");
			return new ResponseEntity<>("Request Updated Successsfully", HttpStatus.OK);
		}else if(requestService.isRequestIdExists(id) != true) {
			logger.error("Request with id {} did not exist", id);
			return new ResponseEntity<>("Request ID is not exist", HttpStatus.BAD_REQUEST);
		}else if(teknisiService.isTeknisiIdExists(teknisi_id) != true){
			logger.error("Teknisi with id {} did not exist", teknisi_id);
			return new ResponseEntity<>("Teknisi ID is not exist", HttpStatus.BAD_REQUEST);
		}else if (request.getRequest_id() == null || request.getTeknisi_id() == 0) {
			logger.error("Request ID cannot be empty or Teknisi ID cannot be zero");
			return new ResponseEntity<>("Request ID cannot be empty or Teknisi ID cannot be zero", HttpStatus.BAD_REQUEST);
		}else {
			logger.error("Check your input again");
			return new ResponseEntity<>("Check your input again", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Delete Request")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data has been deleted"),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/request/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteRequest(@Valid @PathVariable("id") String id) {
		logger.info("Deleting request by id");
		logger.debug("id : {}", id);
		if(requestService.isRequestIdExists(id) == true) {
			logger.debug("Delete Request: {}", requestService.getRequestById(id));
			logger.info("Request has been deleted");
			requestService.deleteRequestById(id);
			return new ResponseEntity<>("Request has been deleted", HttpStatus.OK);
		}else if (requestService.getRequestById(id) == null ) {
			logger.error("Request id cannot be empty");
			return new ResponseEntity<>("Request ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			logger.error("Request with id {} did not exist", id);
			return new ResponseEntity<>("Request ID did not exist", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
}
