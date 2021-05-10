package com.Teknisi.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.Teknisi.model.Request;
import com.Teknisi.model.Teknisi;

@Repository
public class RequestDaoImpl extends JdbcDaoSupport implements RequestDao{

	 @Autowired 
	 DataSource dataSource;
	 
	 @PostConstruct
	 private void initialize(){
	    setDataSource(dataSource);
	 }
	
	@Override
	public List<Request> getAllRequest() {
		String query = 
			"select req.request_id, req.merchant_name, req.address, "
			+ "req.city, req.postal_code, req.phone, req.pic, tek.id, "
			+ "req.created_date, req.created_by, req.update_date, req.update_by "
			+ "from request req right join teknisi tek on req.teknisi_id = tek.id";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Request> requestList = new ArrayList<Request>();

		List<Map<String,Object>> requestRows = jdbcTemplate.queryForList(query);

		for(Map<String,Object> requestColumn : requestRows){
			Request request = new Request();
			Teknisi teknisi = new Teknisi();
			request.setRequest_id(String.valueOf(requestColumn.get("request_id")));
			request.setMerchant_name(String.valueOf(requestColumn.get("name")));
			request.setAddress(String.valueOf(requestColumn.get("address")));
			request.setCity(String.valueOf(requestColumn.get("city")));
			request.setPostal_code(String.valueOf(requestColumn.get("postal_code")));
			request.setPhone(String.valueOf(requestColumn.get("phone")));
			request.setPerson_in_charge(String.valueOf(requestColumn.get("pic")));
			teknisi.setId(Long.parseLong(requestColumn.get("id").toString()));
			request.setTeknisi_id(teknisi);
			request.setCreated_date((Date)(requestColumn.get("created_date")));
			request.setCreated_by(String.valueOf(requestColumn.get("created_by")));
			request.setUpdate_date((Date)(requestColumn.get("update_date")));
			request.setUpdate_by(String.valueOf(requestColumn.get("update_by")));
			requestList.add(request);
		}
		return requestList;
	}

	@Override
	public void insertRequest(Request request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int deleteRequestById(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateRequest(Request request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Request findRequestById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
