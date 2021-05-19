package com.Teknisi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Teknisi.model.Teknisi;
import com.Teknisi.services.TeknisiService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiOperation(value = "/teknisi", tags = "Teknisi Profile Controller")
@RestController
public class TeknisiController {
	
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
		if(teknisiService.isTeknisiIdExists(id) == true) {
			List<Teknisi> teknisi = teknisiService.getTeknisiById(id);
			return new ResponseEntity<>(teknisi, HttpStatus.OK);
		}else if (teknisiService.isTeknisiIdExists(id) == false ) {
			return new ResponseEntity<>("Teknisi ID did not exist", HttpStatus.BAD_REQUEST);
		}else {
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
	public ResponseEntity<Object> createTeknisi(@Valid @ModelAttribute Teknisi teknisi) {
		long id = teknisi.getId();
		if(teknisiService.isTeknisiIdExists(id) == true) {
			return new ResponseEntity<>("Teknisi ID already exist", HttpStatus.BAD_REQUEST);
		}else if (teknisi.getId() == null ) {
			return new ResponseEntity<>("Teknisi ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			teknisiService.insertTeknisi(teknisi);
			return new ResponseEntity<>("Teknisi Created Successsfully", HttpStatus.OK);
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
	public ResponseEntity<Object> updateTeknisi(@Valid @ModelAttribute Teknisi teknisi) {
		long id = teknisi.getId();
		if(teknisiService.isTeknisiIdExists(id) == true) {
			teknisiService.updateTeknisi(teknisi);
			return new ResponseEntity<>("Teknisi Updated Successsfully", HttpStatus.OK);
		}else if (teknisi.getId() == null ) {
			return new ResponseEntity<>("Teknisi ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
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
		if(teknisiService.isTeknisiIdExists(id) == true) {
			teknisiService.deleteTeknisiById(id);
			return new ResponseEntity<>("Teknisi has been deleted", HttpStatus.OK);
		}else if (teknisiService.getTeknisiById(id) == null ) {
			return new ResponseEntity<>("Teknisi ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
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
