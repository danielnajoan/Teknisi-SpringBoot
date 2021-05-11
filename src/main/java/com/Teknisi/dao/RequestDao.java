package com.Teknisi.dao;

import java.util.List;

import com.Teknisi.model.Request;

public interface RequestDao {
	List<Request> getAllRequest();
	public Request findRequestById(String id);
	void insertRequest(Request request);
	int deleteRequestById(String id);
	void updateRequest(Request request);
	boolean isRequestIdExists(String id);
}
