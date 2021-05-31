package com.teknisi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.teknisi.dao.AppUserDao;
import com.teknisi.model.AppUser;

@Service
public class AppUserServiceImpl implements AppUserService{

	@Autowired AppUserDao appUserDao;

	@Autowired private PasswordEncoder bcryptEncoder;
	
	@Override
	public List<AppUser> showAllAppUser() {
		return appUserDao.getAllAppUser();
	}

	@Override
	public AppUser getAppUserById(Long id) {
		return appUserDao.findAppUserById(id);
	}

	@Override
	public void insertAppUser(AppUser appUser) {
		appUser.setUsername(appUser.getUsername());
		appUser.setPassword(bcryptEncoder.encode(appUser.getPassword()));
		appUserDao.insertAppUser(appUser);
	}

	@Override
	public void deleteAppUserById(Long id) {
		appUserDao.deleteAppUserById(id);
	}

	@Override
	public void updateAppUser(AppUser appUser) {
		appUserDao.updateAppUser(appUser);
	}

	@Override
	public boolean isAppUserIdExists(Long id) {
		return appUserDao.isAppUserIdExists(id);
	}

	@Override
	public boolean isAppUserUsernameExists(String username) {
		return appUserDao.isAppUserUsernameExists(username);
	}

	@Override
	public boolean isAppUserEmailExists(String email) {
		return appUserDao.isAppUserEmailExists(email);
	}
	
}
