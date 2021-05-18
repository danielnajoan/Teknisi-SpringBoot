package com.Teknisi.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class TeknisiPhotoDaoImpl extends JdbcDaoSupport implements TeknisiPhotoDao{

	@Autowired 
    DataSource dataSource;
 
    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }
    
	@Override
	public List<com.Teknisi.model.TeknisiPhoto> getAllTeknisiPhoto() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<com.Teknisi.model.TeknisiPhoto> findTeknisiPhotoById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void TeknisiPhoto(com.Teknisi.model.TeknisiPhoto teknisiPhoto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int deleteTeknisiPhotoById(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateTeknisiPhoto(com.Teknisi.model.TeknisiPhoto teknisi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTeknisiPhotoIdExists(long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
