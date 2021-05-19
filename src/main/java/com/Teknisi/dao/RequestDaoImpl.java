package com.Teknisi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.Teknisi.model.Request;

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
			"select * from request order by request_id asc";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Request> requestList = new ArrayList<Request>();

		List<Map<String,Object>> rows = jdbcTemplate.queryForList(query);

		for(Map<String,Object> column : rows){
			Request request = new Request();
			request.setRequest_id(String.valueOf(column.get("request_id")));
			request.setMerchant_name(String.valueOf(column.get("merchant_name")));
			request.setAddress(String.valueOf(column.get("address")));
			request.setCity(String.valueOf(column.get("city")));
			request.setPostal_code(String.valueOf(column.get("postal_code")));
			request.setPhone(String.valueOf(column.get("phone")));
			request.setPerson_in_charge(String.valueOf(column.get("pic")));
			request.setTeknisi_id(Integer.parseInt(column.get("teknisi_id").toString()));
			request.setCreated_date((Date)(column.get("created_date")));
			request.setCreated_by(String.valueOf(column.get("created_by")));
			request.setUpdate_date((Date)(column.get("update_date")));
			request.setUpdate_by(String.valueOf(column.get("update_by")));
			requestList.add(request);
		}
		return requestList;
	}
	@Override
	public Request findRequestById(String id) {
		String query = "select * from request where request_id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		//using RowMapper anonymous class, we can create a separate RowMapper for reuse
		@SuppressWarnings("deprecation")
		Request request = jdbcTemplate.queryForObject(query, new Object[]{id}, new RowMapper<Request>(){
			@Override
			public Request mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Request request = new Request();
				request.setRequest_id(rs.getString("request_id"));
				request.setMerchant_name(rs.getString("merchant_name"));
				request.setAddress(rs.getString("address"));
				request.setCity(rs.getString("city"));
				request.setPostal_code(rs.getString("postal_code"));
				request.setPhone(rs.getString("phone"));
				request.setPerson_in_charge(rs.getString("pic"));
				request.setTeknisi_id(rs.getInt("teknisi_id"));
				request.setCreated_date(rs.getDate("created_date"));
				request.setCreated_by(rs.getString("created_by"));
				request.setUpdate_date(rs.getDate("update_date"));
				request.setUpdate_by(rs.getString("update_by"));
				return request;
			}});
		return request;
	}
	@Override
	public void insertRequest(Request request) {
		String query = 
	    		 "INSERT INTO request("
	    		 + "request_id, merchant_name, address, city, postal_code, phone, pic, "
	    		 + "teknisi_id, created_date, created_by, update_date, update_by) "
	    		 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
	    Date created_date = new Date();
		String created_by = "Merchant PIC";
		
	     getJdbcTemplate()
	     	.update(query, new Object[]{
	     			request.getRequest_id(), request.getMerchant_name(), request.getAddress(), request.getCity(), request.getPostal_code(),
	     			request.getPhone(), request.getPerson_in_charge(), request.getTeknisi_id(), created_date, created_by, 
	     			request.getUpdate_date(), request.getUpdate_by()
	     		});
		
	}

	@Override
	public int deleteRequestById(String id) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.update("delete from request where request_id = ?",id);
	}

	@Override
	public void updateRequest(Request request) {
		String query = "update request set merchant_name=? , address=? , city=?, postal_code=?, phone=?, pic=?, "
				+ "teknisi_id=?, update_date=?, update_by=? where request_id=?";
		Date update_date = new Date();
		String update_by = "Database Admin";
		getJdbcTemplate()
     	.update(query, new Object[]{
     			request.getMerchant_name(), request.getAddress(), request.getCity(), request.getPostal_code(), request.getPhone(),
     			request.getPerson_in_charge(), request.getTeknisi_id(),
     			update_date, update_by, request.getRequest_id()
     		});
	}
	@Override
	public boolean isRequestIdExists(String id) {
		String sql = "select count(*) from request where request_id= ? limit 1";
	    @SuppressWarnings("deprecation")
		long count = getJdbcTemplate().queryForObject(sql, new Object[] { id }, Long.class);
		return count > 0;
	}

}
