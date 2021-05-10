package com.Teknisi.dao;

import java.util.List;

import com.Teknisi.model.Request;

public interface RequestDao {
	List<Request> getAllRequest();
	void insertRequest(Request request);
	int deleteRequestById(Long id);
	void updateRequest(Request request);
	public Request findRequestById(long id);
}
