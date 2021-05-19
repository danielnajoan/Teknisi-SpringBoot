package com.Teknisi.services;

import java.util.List;

import com.Teknisi.model.TeknisiPhoto;

public interface TeknisiPhotoService {
	List<TeknisiPhoto> showAllTeknisiPhoto();
	TeknisiPhoto getTeknisiPhotoById(Long id);
	void insertTeknisiPhoto(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64);
	void deleteTeknisiPhotoById(Long id);
	void updateTeknisiPhoto(TeknisiPhoto teknisiPhoto);
	boolean isTeknisiPhotoIdExists(Long id);
}
