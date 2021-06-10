package com.teknisi.dao;

import java.util.List;

import com.teknisi.model.AppUser;

public interface AppUserDao {
	List<AppUser> getAllAppUser();
	List<AppUser> getAllAppUserBasedOnRole(String role);
	public AppUser findAppUserById(Long id);
	void insertAppUser(AppUser appUser);
	int deleteAppUserById(Long id);
	void updateAppUser(AppUser appUser);
	boolean isAppUserIdExists(Long id);
	boolean isAppUserUsernameExists(String username);
	boolean isAppUserEmailExists(String email);
	public AppUser findAppUserByUsername(String username);
}
