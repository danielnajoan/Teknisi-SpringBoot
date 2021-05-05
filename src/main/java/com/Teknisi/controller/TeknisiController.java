package com.Teknisi.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Teknisi.exception.DataNotfoundException;
import com.Teknisi.model.Teknisi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TeknisiController {
	private Logger logger = LoggerFactory.getLogger("TeknisiApplication");
	
	@RequestMapping(value = "/teknisi", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveAll() {
		Map<Long, Teknisi> teknisiRepo = new HashMap<>();
		Teknisi teknisi = new Teknisi();
		teknisi.setId(1L);
		teknisi.setPhone("08194007111");
		teknisi.setName("Daniel");
		teknisi.setNik("2001551492");
		teknisi.setAddress("Jatiwarna");
		teknisi.setEmail("danielnajoan@gmail.com");
		teknisi.setCity("Bekasi");
		teknisi.setPostal_code("17415");
		teknisi.setLast_login(new Date());
		teknisi.setLongitude("200000");
		teknisi.setLatitude("10000000");
		teknisi.setCreated_date(new Date());
		teknisi.setCreated_by("Aldrian");
		teknisi.setUpdate_date(new Date());
		teknisi.setUpdate_by("Tamimmanar");
		teknisiRepo.put(teknisi.getId(), teknisi);

		return new ResponseEntity<>(teknisiRepo.values(), HttpStatus.OK);
	}
	

	@RequestMapping(value = "/teknisi/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveById(@PathVariable("id") Long id) {
		logger.debug("Get with id : " + id);
		if(id.equals(null)) throw new DataNotfoundException();
		Teknisi teknisi = new Teknisi();
		teknisi.setId(1L);
		teknisi.setPhone("08194007111");
		teknisi.setName("Daniel");
		teknisi.setNik("2001551492");
		teknisi.setAddress("Jatiwarna");
		teknisi.setEmail("danielnajoan@gmail.com");
		teknisi.setCity("Bekasi");
		teknisi.setPostal_code("17415");
		teknisi.setLast_login(new Date());
		teknisi.setLongitude("200000");
		teknisi.setLatitude("10000000");
		teknisi.setCreated_date(new Date());
		teknisi.setCreated_by("Aldrian");
		teknisi.setUpdate_date(new Date());
		teknisi.setUpdate_by("Tamimmanar");
		
		return new ResponseEntity<>(teknisi, HttpStatus.OK);
	}
	@RequestMapping(value = "/teknisi/create", method = RequestMethod.POST)
	public ResponseEntity<Object> createTeknisi(@RequestBody Teknisi teknisi) {
		try {
			logger.debug("Input : "+new ObjectMapper().writeValueAsString(teknisi));
		} catch (JsonProcessingException jsonProcessingException) {
			logger.error("Error : "+jsonProcessingException.getLocalizedMessage());
		}
		return new ResponseEntity<>("Teknisi is created successsfully", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/teknisi/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateTeknisi(@RequestBody Teknisi teknisi) {
		try {
			logger.debug("Input : "+new ObjectMapper().writeValueAsString(teknisi));
		} catch (JsonProcessingException jsonProcessingException) {
			logger.error("Error : "+jsonProcessingException.getLocalizedMessage());
		}
		return new ResponseEntity<>("Teknisi is updated successsfully", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/teknisi/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		logger.debug("Delete with id : " + id);
		return new ResponseEntity<>("Teknisi is deleted successsfully", HttpStatus.OK);
	}
}
