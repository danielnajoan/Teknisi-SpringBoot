package com.teknisi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teknisi.dao.TeknisiPhotoDao;
import com.teknisi.model.TeknisiPhoto;

@Service
public class TeknisiPhotoServiceImpl implements TeknisiPhotoService{

	@Autowired TeknisiPhotoDao teknisiPhotoDao;
	
	@Override
	public List<TeknisiPhoto> showAllTeknisiPhoto() {
		return teknisiPhotoDao.getAllTeknisiPhoto();
	}

	@Override
	public TeknisiPhoto getTeknisiPhotoById(Long id) {
		return teknisiPhotoDao.findTeknisiPhotoById(id);
	}

	@Override
	public void insertTeknisiPhoto(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64) {
		teknisiPhotoDao.insertTeknisiPhoto(teknisiPhoto, fileName, fileType, base64);
	}

	@Override
	public void deleteTeknisiPhotoById(Long id) {
		teknisiPhotoDao.deleteTeknisiPhotoById(id);
	}

	@Override
	public void updateTeknisiPhoto(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64) {
		teknisiPhotoDao.updateTeknisiPhoto(teknisiPhoto, fileName, fileType, base64);
	}

	@Override
	public boolean isTeknisiPhotoIdExists(Long id) {
		return teknisiPhotoDao.isTeknisiPhotoIdExists(id);
	}
	
	@Override
	public boolean isTeknisiPhotoIdAndTeknisiIdExists(Long id, Long teknisi_id) {
		return teknisiPhotoDao.isTeknisiPhotoIdAndTeknisiIdExists(id, teknisi_id);
	}
	
	@Override
	public boolean isTeknisiPhotoIdOrTeknisiIdExists(Long id, Long teknisi_id) {
		return teknisiPhotoDao.isTeknisiPhotoIdOrTeknisiIdExists(id, teknisi_id);
	}
}
