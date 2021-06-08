package com.teknisi.dao;

import java.util.List;

import com.teknisi.model.Request;

public interface RequestDao {
	List<Request> getAllRequest();
	List<Request> getAllNewRequest();
	public Request findRequestById(String id);
	void insertRequest(Request request);
	int deleteRequestById(String id);
	void updateRequest(Request request);
	boolean isRequestIdExists(String id);
	boolean isStatusExists();
}
