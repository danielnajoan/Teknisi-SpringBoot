package com.Teknisi.services;

import java.util.List;

import com.Teknisi.model.Teknisi;

public interface TeknisiService {
	List<Teknisi> showAllTeknisi();
	Teknisi getTeknisiById(long id);
	void insertTeknisi(Teknisi teknisi);
	void deleteTeknisiById(Long id);
	void updateTeknisi(Teknisi teknisi);
	boolean isTeknisiIdExists(long id);
}
