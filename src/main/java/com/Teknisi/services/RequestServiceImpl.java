package com.teknisi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teknisi.dao.RequestDao;
import com.teknisi.model.Request;

@Service
public class RequestServiceImpl implements RequestService{

	@Autowired RequestDao requestDao;
	
	@Override
	public List<Request> showAllRequest() {
		return requestDao.getAllRequest();
	}
	
	@Override
	public List<Request> showAllStatusRequest(String status) {
		return requestDao.getAllStatusRequest(status);
	}

	@Override
	public Request getRequestById(String id) {
		return requestDao.findRequestById(id);
	}

	@Override
	public void insertRequest(Request request) {
		requestDao.insertRequest(request);
	}

	@Override
	public void deleteRequestById(String id) {
		requestDao.deleteRequestById(id);
	}

	@Override
	public void updateRequest(Request request) {
		requestDao.updateRequest(request);
	}
	
	@Override
	public void updateStatusRequest(Request request) {
		requestDao.updateStatusRequest(request);
	}

	@Override
	public boolean isRequestIdExists(String id) {
		return requestDao.isRequestIdExists(id);
	}

	@Override
	public boolean isStatusExists() {
		return requestDao.isStatusExists();
	}

}
