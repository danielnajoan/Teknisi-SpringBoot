package com.teknisi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.teknisi.services.AppUserService;
import com.teknisi.services.TeknisiService;

@SpringBootApplication
public class TeknisiApplication implements CommandLineRunner{

	@Autowired TeknisiService teknisiService;
	@Autowired AppUserService appUserService;
	
	public static void main(String[] args) {
		SpringApplication.run(TeknisiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
	}

}
