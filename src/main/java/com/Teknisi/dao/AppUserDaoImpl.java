package com.teknisi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.teknisi.model.AppUser;

@Repository
public class AppUserDaoImpl extends JdbcDaoSupport implements AppUserDao{

	@Autowired 
	 DataSource dataSource;
	 
	 @PostConstruct
	 private void initialize(){
	    setDataSource(dataSource);
	 }

	@Override
	public List<AppUser> getAllAppUser() {
		String query = 
				"select * from app_user order by id asc";
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			List<AppUser> appUserList = new ArrayList<AppUser>();

			List<Map<String,Object>> rows = jdbcTemplate.queryForList(query);

			for(Map<String,Object> column : rows){
				AppUser appUser = new AppUser();
				appUser.setId(Long.parseLong(column.get("id").toString()));
				appUser.setUsername(String.valueOf(column.get("username")));
				appUser.setPassword(String.valueOf(column.get("password")));
				appUser.setEmail(String.valueOf(column.get("email")));
				appUser.setCreated_date((Date)(column.get("created_date")));
				appUser.setCreated_by(String.valueOf(column.get("created_by")));
				appUser.setUpdate_date((Date)(column.get("update_date")));
				appUser.setUpdate_by(String.valueOf(column.get("update_by")));
				appUser.setRoles(String.valueOf(column.get("roles")));
				appUserList.add(appUser);
			}
			return appUserList;
	}

	@Override
	public AppUser findAppUserById(Long id) {
		String query = "select * from app_user where id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		//using RowMapper anonymous class, we can create a separate RowMapper for reuse
		@SuppressWarnings("deprecation")
		AppUser appUser = jdbcTemplate.queryForObject(query, new Object[]{id}, new RowMapper<AppUser>(){
			@Override
			public AppUser mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AppUser appUser = new AppUser();
				appUser.setId(rs.getLong("id"));
				appUser.setUsername(rs.getString("username"));
				appUser.setPassword(rs.getString("password"));
				appUser.setEmail(rs.getString("email"));
				appUser.setCreated_date(rs.getDate("created_date"));
				appUser.setCreated_by(rs.getString("created_by"));
				appUser.setUpdate_date(rs.getDate("update_date"));
				appUser.setUpdate_by(rs.getString("update_by"));
				appUser.setRoles(rs.getString("roles"));
				return appUser;
			}});
		return appUser;
	}

	@Override
	public void insertAppUser(AppUser appUser) {
		String query = 
	    		 "INSERT INTO app_user("
	    		 + "id, username, password, email, "
	    		 + "created_date, created_by, update_date, update_by, roles) "
	    		 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
	    Date created_date = new Date();
		String created_by = "User App Admin";
		String roles = "USER";
	     getJdbcTemplate()
	     	.update(query, new Object[]{
	     			appUser.getId(), appUser.getUsername(), appUser.getPassword(), appUser.getEmail(), 
	     			created_date, created_by, appUser.getUpdate_date(), appUser.getUpdate_by(), roles
	     		});
	}

	@Override
	public int deleteAppUserById(Long id) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.update("delete from app_user where id = ?",id);
	}

	@Override
	public void updateAppUser(AppUser appUser) {
		String query = "update app_user set username=?, password=?, email=?, "
				+ "update_date=?, update_by=? where id = ?";
		Date update_date = new Date();
		String update_by = "Database Admin";
		getJdbcTemplate()
     	.update(query, new Object[]{
     			appUser.getUsername(), appUser.getPassword(), appUser.getEmail(),
     			update_date, update_by, appUser.getId()
     		});
	}

	@Override
	public boolean isAppUserIdExists(Long id) {
		String sql = "select count(*) from app_user where id = ? limit 1";
	    @SuppressWarnings("deprecation")
		Long count = getJdbcTemplate().queryForObject(sql, new Object[] { id}, Long.class);
		return count > 0;
	}

	@Override
	public boolean isAppUserUsernameExists(String username) {
		String sql = "select count(*) from app_user where username = ? limit 1";
	    @SuppressWarnings("deprecation")
		Long count = getJdbcTemplate().queryForObject(sql, new Object[] {username}, Long.class);
		return count > 0;
	}

	@Override
	public boolean isAppUserEmailExists(String email) {
		String sql = "select count(*) from app_user where email = ? limit 1";
	    @SuppressWarnings("deprecation")
		Long count = getJdbcTemplate().queryForObject(sql, new Object[] {email}, Long.class);
		return count > 0;
	}

	@Override
	public AppUser findAppUserByUsername(String username) {
		String query = "select * from app_user where username = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		//using RowMapper anonymous class, we can create a separate RowMapper for reuse
		@SuppressWarnings("deprecation")
		AppUser appUser = jdbcTemplate.queryForObject(query, new Object[]{username}, new RowMapper<AppUser>(){
			@Override
			public AppUser mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AppUser appUser = new AppUser();
				appUser.setId(rs.getLong("id"));
				appUser.setUsername(rs.getString("username"));
				appUser.setPassword(rs.getString("password"));
				appUser.setEmail(rs.getString("email"));
				appUser.setCreated_date(rs.getDate("created_date"));
				appUser.setCreated_by(rs.getString("created_by"));
				appUser.setUpdate_date(rs.getDate("update_date"));
				appUser.setUpdate_by(rs.getString("update_by"));
				appUser.setRoles(rs.getString("roles"));
				return appUser;
			}});
		return appUser;
	}
}
