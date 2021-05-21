package com.Teknisi.services;

import java.util.List;

import com.Teknisi.model.AppUser;

public interface AppUserService {
	List<AppUser> showAllAppUser();
	AppUser getAppUserById(Long id);
	void insertAppUser(AppUser appUser);
	void deleteAppUserById(Long id);
	void updateAppUser(AppUser appUser);
	boolean isAppUserIdExists(Long id);
	boolean isAppUserUsernameExists(String username);
	boolean isAppUserEmailExists(String email);
}
