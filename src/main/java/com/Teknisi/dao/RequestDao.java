package com.teknisi.dao;

import java.util.Date;
import java.util.List;

import com.teknisi.model.Request;

public interface RequestDao {
	List<Request> getAllRequest();
	List<Request> getAllRecapitulationRequest();
	List<Request> getAllRecapitulationRequest(Date startDate, Date endDate);
	List<Request> getAllPendingRequest();
	List<Request> getAllStatusRequest(String status);
	List<Request> getRequestByBeforeDate(String status);
	public Request findRequestById(String id);
	void insertRequest(Request request);
	int deleteRequestById(String id);
	void updateRequest(Request request);
	void updateStatusRequest(Request request);
	boolean isRequestIdExists(String id);
	boolean isStatusExists();
}
