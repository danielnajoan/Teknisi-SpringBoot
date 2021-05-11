package com.Teknisi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Teknisi.dao.TeknisiDao;
import com.Teknisi.model.Teknisi;

@Service
public class TeknisiServiceImpl implements TeknisiService{

	@Autowired TeknisiDao teknisiDao;
	
	@Override
	public List<Teknisi> showAllTeknisi() {
		return teknisiDao.getAllTeknisi();
	}

	@Override
	public Teknisi getTeknisiById(long id) {
		return teknisiDao.findTeknisiById(id);
	}
	
	@Override
	public void insertTeknisi(Teknisi teknisi) {
			teknisiDao.insertTeknisi(teknisi);
	}

	@Override
	public void deleteTeknisiById(Long id) {
		teknisiDao.deleteTeknisiById(id);
	}

	@Override
	public void updateTeknisi(Teknisi teknisi) {
		teknisiDao.updateTeknisi(teknisi);
	}

	@Override
	public boolean isTeknisiIdExists(long id) {
		return teknisiDao.isTeknisiIdExists(id);
	}
}
