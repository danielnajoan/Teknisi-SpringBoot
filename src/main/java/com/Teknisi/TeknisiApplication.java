package com.Teknisi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.Teknisi.model.Teknisi;
import com.Teknisi.services.TeknisiService;

@SpringBootApplication
public class TeknisiApplication implements CommandLineRunner{

	@Autowired
	TeknisiService teknisiService;
	
	public static void main(String[] args) {
		SpringApplication.run(TeknisiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Date teknisi_1_last_login = new Date();
		Date teknisi_1_created_date = new Date();
		Date teknisi_1_update_date = new Date();
		//Teknisi 1
		Teknisi teknisi_1 = new Teknisi();
		teknisi_1.setId(1L);
		teknisi_1.setPhone("08194007111");
		teknisi_1.setName("Daniel");
		teknisi_1.setNik("2001551492");
		teknisi_1.setAddress("Jatiwarna");
		teknisi_1.setEmail("danielnajoan@gmail.com");
		teknisi_1.setCity("Bekasi");
		teknisi_1.setPostal_code("17415");
		teknisi_1.setLast_login(teknisi_1_last_login);
		teknisi_1.setLongitude("200000");
		teknisi_1.setLatitude("10000000");
		teknisi_1.setCreated_date(teknisi_1_created_date);
		teknisi_1.setCreated_by("Aldrian");
		teknisi_1.setUpdate_date(teknisi_1_update_date);
		teknisi_1.setUpdate_by("Tamimmanar");
		teknisiService.insert(teknisi_1);
		 
		//Teknisi 2
		Teknisi teknisi_2 = new Teknisi();
		teknisi_2.setId(2L);
		teknisi_2.setPhone("08194007111");
		teknisi_2.setName("Asta");
		teknisi_2.setNik("200010005");
		teknisi_2.setAddress("Clover");
		teknisi_2.setEmail("black@gmail.com");
		teknisi_2.setCity("Clover");
		teknisi_2.setPostal_code("91919");
		teknisi_2.setLast_login(teknisi_1_last_login);
		teknisi_2.setLongitude("200000");
		teknisi_2.setLatitude("10000000");
		teknisi_2.setCreated_date(teknisi_1_created_date);
		teknisi_2.setCreated_by("Yuno");
		teknisi_2.setUpdate_date(teknisi_1_update_date);
		teknisi_2.setUpdate_by("Noel");
		teknisiService.insert(teknisi_2);
		System.out.println("");
		System.out.println("Show All Data in Table");
		teknisiService.showAllTeknisi();
		System.out.println("");
		System.out.println("Search By ID");
		teknisiService.getTeknisiById(Long.valueOf(1L));
		//System.out.println("Delete By ID");
		//teknisiService.deleteById(1L);
		System.out.println("");
		//Update Teknisi 1
		Teknisi updateTeknisi = new Teknisi();
		updateTeknisi.setId(1L);
		updateTeknisi.setPhone("081380555111");
		updateTeknisi.setName("David");
		updateTeknisi.setNik("20015599");
		updateTeknisi.setAddress("PIK");
		updateTeknisi.setEmail("david@gmail.com");
		updateTeknisi.setCity("Jakarta");
		updateTeknisi.setPostal_code("18099");
		updateTeknisi.setLast_login(teknisi_1_last_login);
		updateTeknisi.setLongitude("200000");
		updateTeknisi.setLatitude("10000000");
		updateTeknisi.setCreated_date(teknisi_1_created_date);
		updateTeknisi.setCreated_by("Dante");
		updateTeknisi.setUpdate_date(teknisi_1_update_date);
		updateTeknisi.setUpdate_by("Laurance");
		System.out.println("Update By ID");
		teknisiService.updateTeknisi(updateTeknisi);
		System.out.println("");
		System.out.println("Show All Data in Table");
		teknisiService.showAllTeknisi();
	}

}
