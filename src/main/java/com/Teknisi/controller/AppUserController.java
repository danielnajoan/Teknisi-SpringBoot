package com.Teknisi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Teknisi.model.AppUser;
import com.Teknisi.services.AppUserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiOperation(value = "/appUser", tags = "App User Profile Controller")
@RestController
public class AppUserController {
	
	@Autowired AppUserService appUserService;
	
	@ApiOperation(value = "Fetch All AppUser Data")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Iterable.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/appUser/showAllAppUser", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveAllAppUser() {
		List<AppUser> listAppUser = appUserService.showAllAppUser();
		return new ResponseEntity<>(listAppUser, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Fetch AppUser by ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = AppUser.class),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/appUser/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveAppUserById(@Valid @PathVariable("id") Long id) {
		if(appUserService.isAppUserIdExists(id) == true) {
			AppUser appUser = appUserService.getAppUserById(id);
			return new ResponseEntity<>(appUser, HttpStatus.OK);
		}else if (appUserService.isAppUserIdExists(id) == false ) {
			return new ResponseEntity<>("AppUser ID did not exist", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>("AppUser ID cannot be empty", HttpStatus.BAD_REQUEST);
		}
	}
	

	@ApiOperation(value = "Create AppUser")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data has been created"),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/appUser/create", method = RequestMethod.POST)
	public ResponseEntity<Object> createAppUser(@Valid @RequestBody AppUser appUser) {
		Long id = appUser.getId();
		String username = appUser.getUsername();
		String email = appUser.getEmail();
		if(appUserService.isAppUserIdExists(id) != true && appUserService.isAppUserUsernameExists(username) != true 
				&& appUserService.isAppUserEmailExists(email) != true) {
			appUserService.insertAppUser(appUser);
			return new ResponseEntity<>("AppUser Created Successsfully", HttpStatus.OK);
		}else if (appUserService.isAppUserIdExists(id) == true ) {
			return new ResponseEntity<>("AppUser ID already exist", HttpStatus.BAD_REQUEST);
		}else if (appUserService.isAppUserUsernameExists(username) == true ) {
			return new ResponseEntity<>("AppUser Username already exist", HttpStatus.BAD_REQUEST);
		}else if (appUserService.isAppUserEmailExists(email) == true ) {
			return new ResponseEntity<>("AppUser Email already exist", HttpStatus.BAD_REQUEST);
		}else if (appUser.getId() == null ) {
			return new ResponseEntity<>("AppUser ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>("Check your input again", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Update AppUser")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data has been updated"),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/appUser/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateAppUser(@Valid @RequestBody AppUser appUser) {
		Long id = appUser.getId();
		if(appUserService.isAppUserIdExists(id) == true) {
			appUserService.updateAppUser(appUser);
			return new ResponseEntity<>("AppUser Updated Successsfully", HttpStatus.OK);
		}else if(appUserService.isAppUserIdExists(id) != true) {
			return new ResponseEntity<>("AppUser ID is not exist", HttpStatus.BAD_REQUEST);
		}else if (appUser.getId() == null) {
			return new ResponseEntity<>("AppUser ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>("Check your input again", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Delete AppUser")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data has been deleted"),
			@ApiResponse(code = 401, message = "Unauthorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	@RequestMapping(value = "/appUser/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteAppUser(@Valid @PathVariable("id") Long id) {
		if(appUserService.isAppUserIdExists(id) == true) {
			appUserService.deleteAppUserById(id);
			return new ResponseEntity<>("AppUser has been deleted", HttpStatus.OK);
		}else if (appUserService.getAppUserById(id) == null ) {
			return new ResponseEntity<>("AppUser ID cannot be empty", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>("AppUser ID did not exist", HttpStatus.BAD_REQUEST);
		}
	}
}
