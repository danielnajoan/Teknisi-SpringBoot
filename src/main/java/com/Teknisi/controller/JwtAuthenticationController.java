package com.teknisi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teknisi.configuration.JwtTokenUtil;
import com.teknisi.model.AppUser;
import com.teknisi.model.JwtRequest;
import com.teknisi.model.JwtResponse;
import com.teknisi.services.AppUserService;
import com.teknisi.services.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired AppUserService appUserService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(HttpServletRequest request, 
			@RequestBody JwtRequest authenticationRequest) throws Exception {
		logger.info("Authenticating AppUser");
		logger.debug("Authenticate AppUser: {}", authenticationRequest.getUsername(), authenticationRequest.getPassword());
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		String sessionId = request.getSession().getId();
		request.getSession().setAttribute("sessionId", sessionId);
		final String token = jwtTokenUtil.generateToken(sessionId, userDetails);
		logger.info("Generate AppUser Token");
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Object> saveAppUser(@RequestBody AppUser appUser) throws Exception {
		logger.info("Creating AppUser");
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			logger.debug("Input {}", objectMapper.writeValueAsString(appUser));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		appUserService.insertAppUser(appUser);
		logger.debug("Create AppUser: {}", appUser);
		logger.info("AppUser Created Successsfully");
		return new ResponseEntity<>("AppUser Created Successsfully", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<?> logoutAppUser(final HttpServletRequest request, final HttpServletResponse response) {
	final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("AppUser Logging Out");
		if (auth != null) {
			logger.info("AppUser Log Out Successsfully");
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}