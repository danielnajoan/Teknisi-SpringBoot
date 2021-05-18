package com.Teknisi.dao;

import java.util.List;

import com.Teknisi.model.TeknisiPhoto;

public interface TeknisiPhotoDao {
	List<TeknisiPhoto> getAllTeknisiPhoto();
	public List<TeknisiPhoto> findTeknisiPhotoById(long id);
	void TeknisiPhoto(TeknisiPhoto teknisiPhoto);
	int deleteTeknisiPhotoById(Long id);
	void updateTeknisiPhoto(TeknisiPhoto teknisi);
	boolean isTeknisiPhotoIdExists(long id);
}
