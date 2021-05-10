package com.Teknisi.services;

import java.util.List;

import com.Teknisi.model.Teknisi;

public interface TeknisiService {
	List<Teknisi> showAllTeknisi();
	void insertTeknisi(Teknisi teknisi);
	void deleteTeknisiById(Long id);
	void updateTeknisi(Teknisi teknisi);
	Teknisi getTeknisiById(long id);
}
