package com.teknisi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.teknisi.model.Teknisi;
import com.teknisi.services.TeknisiService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiOperation(value = "/teknisi", tags = "Teknisi Profile Controller")
@RestController
public class TeknisiController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired TeknisiService teknisiService;
	
	@ApiOperation(value = "Fetch All Teknisi Data")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Iterable.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisi/showAllTeknisi", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveAllTeknisi() {
		List<Teknisi> listTeknisi = teknisiService.showAllTeknisi();
		logger.info("Retrieve all teknisi");
		logger.debug("All teknisi: {}", listTeknisi);
		return new ResponseEntity<>(listTeknisi, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Fetch Teknisi by ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Teknisi.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisi/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveTeknisiById(@Valid @PathVariable("id") Long id) {
		logger.info("Retrieving teknisi by id");
		logger.debug("id : {}", id);
		if(teknisiService.isTeknisiIdExists(id) == true) {
			List<Teknisi> teknisi = teknisiService.getTeknisiById(id);
			logger.info("Retrieve teknisi");
			logger.debug("Retrieve teknisi: {}", teknisi);
			return new ResponseEntity<>(teknisi, HttpStatus.OK);
		}else if (teknisiService.isTeknisiIdExists(id) == false ) {
			logger.error("Teknisi with id {} not exist", id);
			return new ResponseEntity<>("Teknisi ID did not exist", HttpStatus.BAD_REQUEST);
		}else {
			logger.error("Teknisi id cannot be empty");
			return new ResponseEntity<>("Teknisi ID cannot be empty", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Create Teknisi")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data has been created"),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisi/create", method = RequestMethod.POST)
	public ResponseEntity<Object> createTeknisi(@Valid @RequestBody Teknisi teknisi) {
		logger.info("Creating teknisi");
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			logger.debug("Input {}", objectMapper.writeValueAsString(teknisi));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		Long id = teknisi.getId();
		if(teknisiService.isTeknisiIdExists(id) != true) {
			teknisiService.insertTeknisi(teknisi);
			logger.debug("Create Teknisi: {}", teknisi);
			logger.info("Teknisi Created Successsfully");
			return new ResponseEntity<>("Teknisi Created Successsfully", HttpStatus.OK);
		}else if(teknisiService.isTeknisiIdExists(id) == true) {
			logger.error("Teknisi with id {} already exist", id);
			return new ResponseEntity<>("Teknisi ID already exist", HttpStatus.BAD_REQUEST);
		}else if (teknisi.getId() == null ) {
			logger.error("Teknisi id cannot be empty");
			return new ResponseEntity<>("Teknisi ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			logger.error("Check your input again");
			return new ResponseEntity<>("Check your input again", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Update Teknisi")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data has been updated"),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisi/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateTeknisi(@Valid @RequestBody Teknisi teknisi) {
		Long id = teknisi.getId();
		logger.info("Updating teknisi");
		logger.debug("id : {}", id);
		if(teknisiService.isTeknisiIdExists(id) == true) {
			teknisiService.updateTeknisi(teknisi);
			logger.debug("Update Teknisi: {}", teknisi);
			logger.info("Teknisi Updated Successsfully");
			return new ResponseEntity<>("Teknisi Updated Successsfully", HttpStatus.OK);
		}else if (teknisi.getId() == null ) {
			logger.error("Teknisi id cannot be empty");
			return new ResponseEntity<>("Teknisi ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			logger.error("Teknisi with id {} did not exist", id);
			return new ResponseEntity<>("Teknisi ID did not exist", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Delete Teknisi")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data has been deleted"),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisi/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteTeknisi(@Valid @PathVariable("id") Long id) {
		logger.info("Deleting teknisi by id");
		logger.debug("id : {}", id);
		if(teknisiService.isTeknisiIdExists(id) == true) {
			logger.debug("Delete Teknisi: {}", teknisiService.getTeknisiById(id));
			logger.info("Teknisi has been deleted");
			teknisiService.deleteTeknisiById(id);
			return new ResponseEntity<>("Teknisi has been deleted", HttpStatus.OK);
		}else if (teknisiService.getTeknisiById(id) == null ) {
			logger.error("Teknisi id cannot be empty");
			return new ResponseEntity<>("Teknisi ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			logger.error("Teknisi with id {} did not exist", id);
			return new ResponseEntity<>("Teknisi ID did not exist", HttpStatus.BAD_REQUEST);
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
