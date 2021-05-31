package com.teknisi.dao;

import java.util.List;

import com.teknisi.model.Teknisi;

public interface TeknisiDao {
	List<Teknisi> getAllTeknisi();
	public List<Teknisi> findTeknisiById(Long id);
	void insertTeknisi(Teknisi teknisi);
	int deleteTeknisiById(Long id);
	void updateTeknisi(Teknisi teknisi);
	boolean isTeknisiIdExists(Long id);
}
