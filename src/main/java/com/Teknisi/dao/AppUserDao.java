package com.Teknisi.dao;

import java.util.List;

import com.Teknisi.model.AppUser;

public interface AppUserDao {
	List<AppUser> getAllAppUser();
	public AppUser findAppUserById(Long id);
	void insertAppUser(AppUser appUser);
	int deleteAppUserById(Long id);
	void updateAppUser(AppUser appUser);
	boolean isAppUserIdExists(Long id);
	boolean isAppUserUsernameExists(String username);
	boolean isAppUserEmailExists(String email);
}
