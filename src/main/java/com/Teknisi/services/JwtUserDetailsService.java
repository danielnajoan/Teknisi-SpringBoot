package com.teknisi.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.teknisi.dao.AppUserDao;
import com.teknisi.model.AppUser;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired AppUserDao appUserDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = appUserDao.findAppUserByUsername(username);
		GrantedAuthority authority = new SimpleGrantedAuthority(appUser.getRoles());
		if (appUserDao.isAppUserUsernameExists(username) == true) {
			return new User(appUser.getUsername(), appUser.getPassword(), Arrays.asList(authority));
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}