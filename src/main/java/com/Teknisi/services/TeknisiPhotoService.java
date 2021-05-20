package com.Teknisi.services;

import java.util.List;

import com.Teknisi.model.TeknisiPhoto;

public interface TeknisiPhotoService {
	List<TeknisiPhoto> showAllTeknisiPhoto();
	TeknisiPhoto getTeknisiPhotoById(Long id);
	void insertTeknisiPhoto(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64);
	void deleteTeknisiPhotoById(Long id);
	void updateTeknisiPhoto(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64);
	boolean isTeknisiPhotoIdExists(Long id);
	boolean isTeknisiPhotoIdAndTeknisiIdExists(Long id, Long teknisi_id);
	boolean isTeknisiPhotoIdOrTeknisiIdExists(Long id, Long teknisi_id);
}
