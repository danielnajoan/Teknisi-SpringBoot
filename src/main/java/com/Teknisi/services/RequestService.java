package com.teknisi.services;

import java.util.Date;
import java.util.List;

import com.teknisi.model.Request;

public interface RequestService {
	List<Request> showAllRequest();
	List<Request> showAllRecapitulationRequest();
	List<Request> showAllRecapitulationRequest(Date startDate, Date endDate);
	List<Request> showAllPendingRequest();
	List<Request> showAllStatusRequest(String status);
	List<Request> showRequestByBeforeDate (String status);
	Request getRequestById(String id);
	void insertRequest(Request request);
	void deleteRequestById(String id);
	void updateRequest(Request request);
	void updateStatusRequest(Request request);
	boolean isRequestIdExists(String id);
	boolean isStatusExists();
}
