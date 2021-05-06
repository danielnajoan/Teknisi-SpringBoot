package com.Teknisi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Teknisi.exception.DataNotfoundException;
import com.Teknisi.model.Teknisi;
import com.Teknisi.services.TeknisiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiOperation(value = "/", tags = "Teknisi Profile Controller")
@RestController
public class TeknisiController {
	private Logger logger = LoggerFactory.getLogger("TeknisiApplication");
	
	@Autowired TeknisiService teknisiService;
	
	@ApiOperation(value = "Fetch All Teknisi Data", response = Iterable.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Teknisi.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisi", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveAll() {
		List<Teknisi> listTeknisi = teknisiService.showAllTeknisi();
		return new ResponseEntity<>(listTeknisi, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Fetch Teknisi by ID", response = Teknisi.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Teknisi.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisi/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveById(@PathVariable("id") Long id) {
		logger.debug("Get with id : " + id);
		if(id.equals(null)) throw new DataNotfoundException();
		Teknisi teknisi = teknisiService.getTeknisiById(id);
		
		return new ResponseEntity<>(teknisi, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Create Teknisi", response = Teknisi.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Teknisi.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisi/create", method = RequestMethod.POST)
	public ResponseEntity<Object> createTeknisi(@RequestBody Teknisi teknisi) {
		try {
			logger.debug("Input : "+new ObjectMapper().writeValueAsString(teknisi));
		} catch (JsonProcessingException jsonProcessingException) {
			logger.error("Error : "+jsonProcessingException.getLocalizedMessage());
		}
		return new ResponseEntity<>("Teknisi is created successsfully", HttpStatus.OK);
	}
	
	@ApiOperation(value = "Update Teknisi", response = Teknisi.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Teknisi.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisi/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateTeknisi(@RequestBody Teknisi teknisi) {
		try {
			logger.debug("Input : "+new ObjectMapper().writeValueAsString(teknisi));
		} catch (JsonProcessingException jsonProcessingException) {
			logger.error("Error : "+jsonProcessingException.getLocalizedMessage());
		}
		return new ResponseEntity<>("Teknisi is updated successsfully", HttpStatus.OK);
	}
	
	@ApiOperation(value = "Delete Teknisi", response = Teknisi.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Teknisi.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisi/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		logger.debug("Delete with id : " + id);
		return new ResponseEntity<>("Teknisi is deleted successsfully", HttpStatus.OK);
	}
}
