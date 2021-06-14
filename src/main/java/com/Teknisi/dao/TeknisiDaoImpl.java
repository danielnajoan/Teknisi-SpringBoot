package com.teknisi.dao;

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

import com.teknisi.model.Request;
import com.teknisi.model.Teknisi;
import com.teknisi.model.TeknisiPhoto;

@Repository
public class TeknisiDaoImpl extends JdbcDaoSupport implements TeknisiDao{

    @Autowired 
    DataSource dataSource;
 
    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }
    @Override
	public List<Teknisi> getAllTeknisi() {
		String query = "select tek.id as teknisiID, tek.phone as teknisiPhone, tek.name as teknisiName, tek.nik as teknisiNIK, tek.address as teknisiAddress, tek.email as teknisiEmail, tek.city as teknisiCity, "
				+ "tek.postal_code as teknisiPostalCode, tek.last_login as teknisiLastLogin, tek.longitude as teknisiLongitude, tek.latitude as teknisiLatitude, "
				+ "tek.created_date as teknisiCreatedDate, tek.created_by as teknisiCreatedBy, tek.update_date as teknisiUpdateDate, tek.update_by as teknisiUpdateBy, "
				+ "req.request_id as requestID, req.teknisi_id as reqTeknisiId, req.merchant_name as merchantName, req.address as requestAddress, req.city as requestCity, req.postal_code as requestPostalCode, req.phone as requestPhone, req.pic as requestPIC, "
				+ "req.created_date as requestCreatedDate, req.created_by as requestCreatedBy, req.update_date as requestUpdateDate, req.update_by as requestUpdateBy, req.status as requestStatus, "
				+ "tekPhoto.id as tekPhotoID, tekPhoto.teknisi_id as tekPhotoTeknisiId, tekPhoto.file_type as tekPhotoFile, tekPhoto.name as tekPhotoName, "
				+ "tekPhoto.images as tekPhotoImages, tekPhoto.created_date as tekPhotoCreatedDate, tekPhoto.created_by as tekPhotoCreatedBy, tekPhoto.update_date as tekPhotoUpdateDate, tekPhoto.update_by as tekPhotoUpdateBy "
				+ "from teknisi tek "
				+ "left join request req on req.teknisi_id = tek.id "
				+ "left join teknisi_photo tekPhoto on tekPhoto.teknisi_id = tek.id "
				+ "order by tek.id asc";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Teknisi> teknisiList = new ArrayList<Teknisi>();

		List<Map<String,Object>> rows = jdbcTemplate.queryForList(query);

		for(Map<String,Object> column : rows){
			Teknisi teknisi = new Teknisi();
			List<Request> listRequest = new ArrayList<Request>();
			Request request = new Request();
			TeknisiPhoto teknisiPhoto = new TeknisiPhoto();
			teknisi.setId(Long.parseLong(column.get("teknisiID").toString()));
			teknisi.setPhone(String.valueOf(column.get("teknisiPhone")));
			teknisi.setName(String.valueOf(column.get("teknisiName")));
			teknisi.setNik(String.valueOf(column.get("teknisiNIK")));
			teknisi.setAddress(String.valueOf(column.get("teknisiAddress")));
			teknisi.setEmail(String.valueOf(column.get("teknisiEmail")));
			teknisi.setCity(String.valueOf(column.get("teknisiCity")));
			teknisi.setPostal_code(String.valueOf(column.get("teknisiPostalCode")));
			teknisi.setLast_login((Date)(column.get("teknisiLastLogin")));
			teknisi.setLongitude(String.valueOf(column.get("teknisiLongitude")));
			teknisi.setLatitude(String.valueOf(column.get("teknisiLatitude")));
			teknisi.setCreated_date((Date)(column.get("teknisiCreatedDate")));
			teknisi.setCreated_by(String.valueOf(column.get("teknisiCreatedBy")));
			teknisi.setUpdate_date((Date)(column.get("teknisiUpdateDate")));
			teknisi.setUpdate_by(String.valueOf(column.get("teknisiUpdateBy")));
			if(column.get("requestID")!=null) {
				request.setRequest_id(String.valueOf(column.get("requestID")));
				request.setMerchant_name(String.valueOf(column.get("merchantName")));
				request.setAddress(String.valueOf(column.get("requestAddress")));
				request.setCity(String.valueOf(column.get("requestCity")));
				request.setPostal_code(String.valueOf(column.get("requestPostalCode")));
				request.setPhone(String.valueOf(column.get("requestPhone")));
				request.setPerson_in_charge(String.valueOf(column.get("requestPIC")));
				request.setTeknisi_id(column.get("reqTeknisiId")==null?null:Long.parseLong(column.get("reqTeknisiId").toString()));
				request.setCreated_date((Date)(column.get("requestCreatedDate")));
				request.setCreated_by(String.valueOf(column.get("requestCreatedBy")));
				request.setUpdate_date((Date)(column.get("requestUpdateDate")));
				request.setUpdate_by(String.valueOf(column.get("requestUpdateBy")));
				request.setStatus(String.valueOf(column.get("requestStatus")));
				listRequest.add(request);
				teknisi.setRequest(listRequest);
			}
			if(column.get("tekPhotoID")!=null) {
				teknisiPhoto.setId(column.get("tekPhotoID")==null?null:Long.parseLong(column.get("tekPhotoID").toString()));
				teknisiPhoto.setTeknisi_id(column.get("tekPhotoTeknisiId")==null?null:Long.parseLong(column.get("tekPhotoTeknisiId").toString()));
				teknisiPhoto.setFile_type(String.valueOf(column.get("tekPhotoFile")));
				teknisiPhoto.setName(String.valueOf(column.get("tekPhotoName")));
				teknisiPhoto.setImages(String.valueOf(column.get("tekPhotoImages")));
				teknisiPhoto.setCreated_date((Date)(column.get("tekPhotoCreatedDate")));
				teknisiPhoto.setCreated_by(String.valueOf(column.get("tekPhotoCreatedBy")));
				teknisiPhoto.setUpdate_date((Date)(column.get("tekPhotoUpdateDate")));
				teknisiPhoto.setUpdate_by(String.valueOf(column.get("tekPhotoUpdateBy")));
				teknisi.setTeknisiPhoto(teknisiPhoto);
			}
			teknisiList.add(teknisi);
		}
		return teknisiList;
	}

	@Override
	public List<Teknisi> findTeknisiById(Long id) {
		String query = "select tek.id as teknisiID, tek.phone as teknisiPhone, tek.name as teknisiName, tek.nik as teknisiNIK, tek.address as teknisiAddress, tek.email as teknisiEmail, tek.city as teknisiCity, "
				+ "tek.postal_code as teknisiPostalCode, tek.last_login as teknisiLastLogin, tek.longitude as teknisiLongitude, tek.latitude as teknisiLatitude, "
				+ "tek.created_date as teknisiCreatedDate, tek.created_by as teknisiCreatedBy, tek.update_date as teknisiUpdateDate, tek.update_by as teknisiUpdateBy, "
				+ "req.request_id as requestID, req.teknisi_id as reqTeknisiId, req.merchant_name as merchantName, req.address as requestAddress, req.city as requestCity, req.postal_code as requestPostalCode, req.phone as requestPhone, req.pic as requestPIC, "
				+ "req.created_date as requestCreatedDate, req.created_by as requestCreatedBy, req.update_date as requestUpdateDate, req.update_by as requestUpdateBy, req.status as requestStatus, "
				+ "tekPhoto.id as tekPhotoID, tekPhoto.teknisi_id as tekPhotoTeknisiId, tekPhoto.file_type as tekPhotoFile, tekPhoto.name as tekPhotoName, "
				+ "tekPhoto.images as tekPhotoImages, tekPhoto.created_date as tekPhotoCreatedDate, tekPhoto.created_by as tekPhotoCreatedBy, tekPhoto.update_date as tekPhotoUpdateDate, tekPhoto.update_by as tekPhotoUpdateBy "
				+ "from teknisi tek "
				+ "left join request req on req.teknisi_id = tek.id "
				+ "left join teknisi_photo tekPhoto on tekPhoto.teknisi_id = tek.id "
				+ "where tek.id = ?"
				+ "order by request_id asc ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Teknisi> teknisiList = new ArrayList<Teknisi>();
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(query, new Object[]{id});

			for(Map<String,Object> column : rows){
				Teknisi teknisi = new Teknisi();
				List<Request> listRequest = new ArrayList<Request>();
				Request request = new Request();
				TeknisiPhoto teknisiPhoto = new TeknisiPhoto();
				teknisi.setId(Long.parseLong(column.get("teknisiID").toString()));
				teknisi.setPhone(String.valueOf(column.get("teknisiPhone")));
				teknisi.setName(String.valueOf(column.get("teknisiName")));
				teknisi.setNik(String.valueOf(column.get("teknisiNIK")));
				teknisi.setAddress(String.valueOf(column.get("teknisiAddress")));
				teknisi.setEmail(String.valueOf(column.get("teknisiEmail")));
				teknisi.setCity(String.valueOf(column.get("teknisiCity")));
				teknisi.setPostal_code(String.valueOf(column.get("teknisiPostalCode")));
				teknisi.setLast_login((Date)(column.get("teknisiLastLogin")));
				teknisi.setLongitude(String.valueOf(column.get("teknisiLongitude")));
				teknisi.setLatitude(String.valueOf(column.get("teknisiLatitude")));
				teknisi.setCreated_date((Date)(column.get("teknisiCreatedDate")));
				teknisi.setCreated_by(String.valueOf(column.get("teknisiCreatedBy")));
				teknisi.setUpdate_date((Date)(column.get("teknisiUpdateDate")));
				teknisi.setUpdate_by(String.valueOf(column.get("teknisiUpdateBy")));
				if(column.get("requestID")!=null) {
					request.setRequest_id(String.valueOf(column.get("requestID")));
					request.setMerchant_name(String.valueOf(column.get("merchantName")));
					request.setAddress(String.valueOf(column.get("requestAddress")));
					request.setCity(String.valueOf(column.get("requestCity")));
					request.setPostal_code(String.valueOf(column.get("requestPostalCode")));
					request.setPhone(String.valueOf(column.get("requestPhone")));
					request.setPerson_in_charge(String.valueOf(column.get("requestPIC")));
					request.setTeknisi_id(column.get("reqTeknisiId")==null?null:Long.parseLong(column.get("reqTeknisiId").toString()));
					request.setCreated_date((Date)(column.get("requestCreatedDate")));
					request.setCreated_by(String.valueOf(column.get("requestCreatedBy")));
					request.setUpdate_date((Date)(column.get("requestUpdateDate")));
					request.setUpdate_by(String.valueOf(column.get("requestUpdateBy")));
					request.setStatus(String.valueOf(column.get("requestStatus")));
					listRequest.add(request);
					teknisi.setRequest(listRequest);
				}
				if(column.get("tekPhotoID")!=null) {
					teknisiPhoto.setId(column.get("tekPhotoID")==null?null:Long.parseLong(column.get("tekPhotoID").toString()));
					teknisiPhoto.setTeknisi_id(column.get("tekPhotoTeknisiId")==null?null:Long.parseLong(column.get("tekPhotoTeknisiId").toString()));
					teknisiPhoto.setFile_type(String.valueOf(column.get("tekPhotoFile")));
					teknisiPhoto.setName(String.valueOf(column.get("tekPhotoName")));
					teknisiPhoto.setImages(String.valueOf(column.get("tekPhotoImages")));
					teknisiPhoto.setCreated_date((Date)(column.get("tekPhotoCreatedDate")));
					teknisiPhoto.setCreated_by(String.valueOf(column.get("tekPhotoCreatedBy")));
					teknisiPhoto.setUpdate_date((Date)(column.get("tekPhotoUpdateDate")));
					teknisiPhoto.setUpdate_by(String.valueOf(column.get("tekPhotoUpdateBy")));
					teknisi.setTeknisiPhoto(teknisiPhoto);
				}
				teknisiList.add(teknisi);
			}
			return teknisiList;
	}

	
	@Override
	public void insertTeknisi(Teknisi teknisi) {
	     String query = 
	    		 "INSERT INTO teknisi("
	    		 + "id, phone, name, nik, address, email, city, "
	    		 + "postal_code, last_login, longitude, latitude, "
	    		 + "created_date, created_by, update_date, update_by) "
	    		 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
	    Date created_date = new Date();
		String created_by = "Manager Teknisi";
		
	     getJdbcTemplate()
	     	.update(query, new Object[]{
	     		teknisi.getId(), teknisi.getPhone(), teknisi.getName(), teknisi.getNik(), teknisi.getAddress(), teknisi.getEmail(), teknisi.getCity(),
	     		teknisi.getPostal_code(), teknisi.getLast_login(), teknisi.getLongitude(), teknisi.getLatitude(),
	     		created_date, created_by, teknisi.getUpdate_date(), teknisi.getUpdate_by()
	     		});
		
	}
	@Override
    public int deleteTeknisiById(Long id) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.update("delete from teknisi where id = ?",id);
    }
	
	@Override
	public void updateTeknisi(Teknisi teknisi) {
		String query = "update teknisi set phone=? , name=? , nik=?, address=?, email=?, city=?, "
				+ "postal_code=?, last_login=?, longitude=?, latitude=?, "
				+ "update_date=?, update_by=? where id=?";
		Date update_date = new Date();
		String update_by = "Database Admin";
		getJdbcTemplate()
     	.update(query, new Object[]{
     		teknisi.getPhone(), teknisi.getName(), teknisi.getNik(), teknisi.getAddress(), teknisi.getEmail(), teknisi.getCity(),
     		teknisi.getPostal_code(), teknisi.getLast_login(), teknisi.getLongitude(), teknisi.getLatitude(),
     		update_date, update_by, teknisi.getId()
     		});
		
	}

	@Override
	public boolean isTeknisiIdExists(Long id) {
		String sql = "select count(*) from teknisi where id = ? limit 1";
	    @SuppressWarnings("deprecation")
	    Long count = getJdbcTemplate().queryForObject(sql, new Object[] { id }, Long.class);
		return count > 0;
	}

}
