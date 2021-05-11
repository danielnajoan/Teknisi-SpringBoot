package com.Teknisi.services;

import java.util.List;

import com.Teknisi.model.Request;

public interface RequestService {
	List<Request> showAllRequest();
	Request getRequestById(String id);
	void insertRequest(Request request);
	void deleteRequestById(String id);
	void updateRequest(Request request);
	boolean isRequestIdExists(String id);
}
