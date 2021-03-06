package com.teknisi.services;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teknisi.dao.TeknisiDao;
import com.teknisi.model.Request;
import com.teknisi.model.Teknisi;

@Service
public class TeknisiServiceImpl implements TeknisiService{

	@Autowired TeknisiDao teknisiDao;
	
	@Override
	public List<Teknisi> showAllTeknisi() {
		List<Teknisi> teknisiList = teknisiDao.getAllTeknisi();
		List<Teknisi> clearList = new ArrayList<Teknisi>();
		Hashtable<Long, Teknisi> filterHash = new Hashtable<Long, Teknisi>();
		for(Teknisi teknisiObject:teknisiList){
			Teknisi TeknisiOnHash = filterHash.get(teknisiObject.getId());
		  
		  if(TeknisiOnHash==null){
		    filterHash.put(teknisiObject.getId(),teknisiObject);
		  }
		  else{
		    List<Request> requestList = TeknisiOnHash.getRequest();
		    requestList.addAll(teknisiObject.getRequest());
		    TeknisiOnHash.setRequest(requestList);
		    filterHash.put(TeknisiOnHash.getId(),TeknisiOnHash);
		  }
		}
		for(Long key:filterHash.keySet()){
		  clearList.add(filterHash.get(key));
		}

		return clearList;
	}

	@Override
	public List<Teknisi> getTeknisiById(Long id) {
		List<Teknisi> teknisiList = teknisiDao.findTeknisiById(id);
		List<Teknisi> clearList = new ArrayList<Teknisi>();
		Hashtable<Long, Teknisi> filterHash = new Hashtable<Long, Teknisi>();
		for(Teknisi teknisiObject:teknisiList){
			Teknisi TeknisiOnHash = filterHash.get(teknisiObject.getId());
		  
		  if(TeknisiOnHash==null){
		    filterHash.put(teknisiObject.getId(),teknisiObject);
		  }
		  else{
		    List<Request> requestList = TeknisiOnHash.getRequest();
		    requestList.addAll(teknisiObject.getRequest());
		    TeknisiOnHash.setRequest(requestList);
		    filterHash.put(TeknisiOnHash.getId(),TeknisiOnHash);
		  }
		}
		for(Long key:filterHash.keySet()){
		  clearList.add(filterHash.get(key));
		}

		return clearList;
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
	public boolean isTeknisiIdExists(Long id) {
		return teknisiDao.isTeknisiIdExists(id);
	}
}
