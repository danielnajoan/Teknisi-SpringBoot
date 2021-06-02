package com.teknisi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teknisi.model.TeknisiPhoto;
import com.teknisi.services.TeknisiPhotoService;
import com.teknisi.services.TeknisiService;
import com.teknisi.utility.ImagesUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiOperation(value = "/teknisiPhoto", tags = "Teknisi Photo Profile Controller")
@RestController
public class TeknisiPhotoController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired TeknisiPhotoService teknisiPhotoService;
	@Autowired TeknisiService teknisiService;
	@Autowired ImagesUtils imagesUtils;
	
	@ApiOperation(value = "Fetch All TeknisiPhoto Data")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Iterable.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisiPhoto/showAllTeknisiPhoto", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveAllTeknisiPhoto() {
		List<TeknisiPhoto> listTeknisiPhoto = teknisiPhotoService.showAllTeknisiPhoto();
		logger.info("Retrieve all teknisi photo");
		logger.debug("All teknisi photo: {}", listTeknisiPhoto);
		return new ResponseEntity<>(listTeknisiPhoto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Fetch TeknisiPhoto by ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = TeknisiPhoto.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisiPhoto/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveTeknisiPhotoById(@Valid @PathVariable("id") Long id) {
		logger.info("Retrieving teknisi photo by id");
		logger.debug("id : {}", id);
		if(teknisiPhotoService.isTeknisiPhotoIdExists(id) == true) {
			TeknisiPhoto teknisiPhoto = teknisiPhotoService.getTeknisiPhotoById(id);
			logger.info("Retrieve teknisi photo");
			logger.debug("Retrieve teknisi photo: {}", teknisiPhoto);
			return new ResponseEntity<>(teknisiPhoto, HttpStatus.OK);
		}else if (teknisiPhotoService.isTeknisiPhotoIdExists(id) == false ) {
			logger.error("Teknisi Photo with id {} not exist", id);
			return new ResponseEntity<>("Teknisi Photo ID did not exist", HttpStatus.BAD_REQUEST);
		}else {
			logger.error("Teknisi id cannot be empty");
			return new ResponseEntity<>("Teknisi Photo ID cannot be empty", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Create TeknisiPhoto")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data has been created"),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisiPhoto/create", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> createTeknisiPhoto(@Valid @ModelAttribute TeknisiPhoto teknisiPhoto,  @RequestPart MultipartFile file) throws Exception{
		logger.info("Creating Teknisi Photo");
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			logger.debug("Input {}", objectMapper.writeValueAsString(teknisiPhoto));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String fileName = file.getOriginalFilename().split("\\.")[0];
		String fileType = file.getOriginalFilename().split("\\.")[1];
		byte[] fileByte =  file.getBytes();
		String base64 = imagesUtils.convertImagesBase64(fileByte, fileType);
		Long id = teknisiPhoto.getId();
		Long teknisi_id = teknisiPhoto.getTeknisi_id();
		if(teknisiPhotoService.isTeknisiPhotoIdOrTeknisiIdExists(id, teknisi_id) != true && teknisiService.isTeknisiIdExists(teknisi_id) == true && teknisiPhoto.getId() != null
				&& (fileType.equals("png") || fileType.equals("jpg") || fileType.equals("jpeg") && fileType.isEmpty() != false)) {
			teknisiPhotoService.insertTeknisiPhoto(teknisiPhoto, fileName, fileType, base64);
			logger.debug("Create Teknisi Photo: {}", teknisiPhoto, fileName, fileType, base64);
			logger.info("Teknisi Photo Created Successsfully");
			return new ResponseEntity<>("TeknisiPhoto Created Successsfully", HttpStatus.OK);
		}else if(teknisiPhotoService.isTeknisiPhotoIdOrTeknisiIdExists(id, teknisi_id) == true) {
			logger.error("Teknisi Photo with id {} already exist and Teknisi with id {} already exist", id, teknisi_id);
			return new ResponseEntity<>("TeknisiPhoto ID already exist and Teknisi ID already exist", HttpStatus.BAD_REQUEST);
		}else if(teknisiService.isTeknisiIdExists(teknisi_id) != true){
			logger.error("Teknisi with id {} did not exist", teknisi_id);
			return new ResponseEntity<>("Teknisi ID is not exist", HttpStatus.BAD_REQUEST);
		}else if (teknisiPhoto.getId() == null || teknisiPhoto.getTeknisi_id() == 0) {
			logger.error("TeknisiPhoto ID cannot be empty or Teknisi ID cannot be zero");
			return new ResponseEntity<>("TeknisiPhoto ID cannot be empty or Teknisi ID cannot be zero", HttpStatus.BAD_REQUEST);
		}else {
			logger.error("Check your input again");
			return new ResponseEntity<>("Check your input again", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Update TeknisiPhoto")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data has been updated"),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisiPhoto/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateTeknisiPhoto(@Valid @ModelAttribute TeknisiPhoto teknisiPhoto,  @RequestPart MultipartFile file) throws Exception{
		String fileName = file.getOriginalFilename().split("\\.")[0];
		String fileType = file.getOriginalFilename().split("\\.")[1];
		byte[] fileByte =  file.getBytes();
		String base64 = imagesUtils.convertImagesBase64(fileByte, fileType);
		Long id = teknisiPhoto.getId();
		Long teknisi_id = teknisiPhoto.getTeknisi_id();
		logger.info("Updating teknisi photo");
		logger.debug("id : {}", teknisi_id);
		if(teknisiPhotoService.isTeknisiPhotoIdAndTeknisiIdExists(id, teknisi_id) == true && teknisiService.isTeknisiIdExists(teknisi_id) == true && teknisiPhoto.getId() != null
				&& (fileType.equals("png") || fileType.equals("jpg") || fileType.equals("jpeg") && fileType.isEmpty() != false)) {
			teknisiPhotoService.updateTeknisiPhoto(teknisiPhoto, fileName, fileType, base64);
			logger.debug("Update Teknisi Photo: {}", teknisiPhoto, fileName, fileType, base64);
			logger.info("Teknisi Updated Successsfully");
			return new ResponseEntity<>("TeknisiPhoto Updated Successsfully", HttpStatus.OK);
		}else if(teknisiPhotoService.isTeknisiPhotoIdExists(id) != true) {
			
			return new ResponseEntity<>("TeknisiPhoto ID is not exist", HttpStatus.BAD_REQUEST);
		}else if(teknisiService.isTeknisiIdExists(teknisi_id) != true){
			logger.error("Teknisi with id {} did not exist", teknisi_id);
			return new ResponseEntity<>("Teknisi ID is not exist", HttpStatus.BAD_REQUEST);
		}else if (teknisiPhoto.getId() == null || teknisiPhoto.getTeknisi_id() == 0) {
			logger.error("Teknisi Photo ID cannot be empty or Teknisi ID cannot be zero");
			return new ResponseEntity<>("Teknisi Photo ID cannot be empty or Teknisi ID cannot be zero", HttpStatus.BAD_REQUEST);
		}else {
			logger.error("Check your input again");
			return new ResponseEntity<>("Check your input again", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Delete TeknisiPhoto")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data has been deleted"),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/teknisiPhoto/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteTeknisiPhoto(@Valid @PathVariable("id") Long id) {
		logger.info("Deleting teknisi by id");
		logger.debug("id : {}", id);
		if(teknisiPhotoService.isTeknisiPhotoIdExists(id) == true) {
			logger.debug("Delete Teknisi: {}", teknisiPhotoService.getTeknisiPhotoById(id));
			logger.info("Teknisi has been deleted");
			teknisiPhotoService.deleteTeknisiPhotoById(id);
			return new ResponseEntity<>("TeknisiPhoto has been deleted", HttpStatus.OK);
		}else if (teknisiPhotoService.getTeknisiPhotoById(id) == null ) {
			logger.error("Teknisi Photo id cannot be empty");
			return new ResponseEntity<>("TeknisiPhoto ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			logger.error("Teknisi Photo with id {} did not exist", id);
			return new ResponseEntity<>("TeknisiPhoto ID did not exist", HttpStatus.BAD_REQUEST);
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
