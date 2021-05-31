package com.teknisi.services;

import java.util.List;

import com.teknisi.model.Teknisi;

public interface TeknisiService {
	List<Teknisi> showAllTeknisi();
	List<Teknisi> getTeknisiById(Long id);
	void insertTeknisi(Teknisi teknisi);
	void deleteTeknisiById(Long id);
	void updateTeknisi(Teknisi teknisi);
	boolean isTeknisiIdExists(Long id);
}
