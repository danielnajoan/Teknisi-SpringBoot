package com.Teknisi.dao;

import java.util.List;

import com.Teknisi.model.TeknisiPhoto;

public interface TeknisiPhotoDao {
	List<TeknisiPhoto> getAllTeknisiPhoto();
	public TeknisiPhoto findTeknisiPhotoById(Long id);
	void insertTeknisiPhoto(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64);
	int deleteTeknisiPhotoById(Long id);
	void updateTeknisiPhoto(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64);
	boolean isTeknisiPhotoIdExists(Long id);
	boolean isTeknisiPhotoIdAndTeknisiIdExists(Long id, long teknisi_id);
	boolean isTeknisiPhotoIdOrTeknisiIdExists(Long id, long teknisi_id);
}
