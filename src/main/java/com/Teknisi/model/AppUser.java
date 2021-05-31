package com.teknisi.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Teknisi Model")
public class AppUser implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "ID of the User", name = "id", required = true, example = "100")
	@NotNull(message = "ID cannot be blank")
	@Max(value = 1000, message = "ID should not be greater than 1000")
	private Long id;
	
	@ApiModelProperty(notes = "Username of the User", name = "username", required = true, example = "Dante")
	@NotBlank(message = "Username cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}+$", message = "Username should have length between 1 and 50 characters")
	private String username;
	
	@ApiModelProperty(notes = "Password of the User", name = "password", required = true, example = "Dante")
	@NotBlank(message = "Password cannot be blank")
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,250}$", 
	message = "Password should have Minimum eight characters and maximum 250 characters, at least one uppercase letter, one lowercase letter, one number and one special character")
	private String password;
	
	@ApiModelProperty(notes = "Email of the User", name = "email", required = true, example = "dante@gmail.com")
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email should be valid")
	private String email;
	
	@ApiModelProperty(hidden = true)
	private String roles;
	
	@ApiModelProperty(hidden = true)
	@PastOrPresent
	private Date created_date;

    @ApiModelProperty(hidden = true)
	private String created_by;

	@ApiModelProperty(hidden = true)
	@PastOrPresent
	private Date update_date;

    @ApiModelProperty(hidden = true)
	private String update_by;
    
    public AppUser() {
    	
    }

	public AppUser(
			@NotNull(message = "ID cannot be blank") @Max(value = 1000, message = "ID should not be greater than 1000") Long id,
			@NotBlank(message = "Username cannot be blank") @Pattern(regexp = "^[A-Za-z0-9]{1,50}+$", message = "Username should have length between 1 and 50 characters") String username,
			@NotBlank(message = "Password cannot be blank") @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,250}$", message = "Password should have Minimum eight characters and maximum 250 characters, at least one uppercase letter, one lowercase letter, one number and one special character") String password,
			@NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid") String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppUser [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", email=");
		builder.append(email);
		builder.append(", roles=");
		builder.append(roles);
		builder.append(", created_date=");
		builder.append(created_date);
		builder.append(", created_by=");
		builder.append(created_by);
		builder.append(", update_date=");
		builder.append(update_date);
		builder.append(", update_by=");
		builder.append(update_by);
		builder.append("]");
		return builder.toString();
	}

}
