package com.Teknisi.dao;

import java.util.List;

import com.Teknisi.model.Teknisi;

public interface TeknisiDao {
	List<Teknisi> getAllTeknisi();
	public List<Teknisi> findTeknisiById(long id);
	void insertTeknisi(Teknisi teknisi);
	int deleteTeknisiById(Long id);
	void updateTeknisi(Teknisi teknisi);
	boolean isTeknisiIdExists(long id);
}
