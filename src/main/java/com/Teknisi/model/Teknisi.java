package com.Teknisi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import javax.validation.constraints.Email;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "teknisi")
@ApiModel(description = "Teknisi Model")
public class Teknisi implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
	@ApiModelProperty(notes = "ID of the Teknisi", name = "id", required = true, example = "100")
	@Max(value = 1000, message = "ID should not be greater than 1000")
	private Long id;
	
	@Column(name="phone")
	@ApiModelProperty(notes = "Phone of the Teknisi", name = "phone", required = true, example = "08194455001")
	@Pattern(regexp="[\\d]{1,13}", message = "NIK should have length between 1 and 13 numeric")
	private String phone;
	
	@Column(name="name")
	@ApiModelProperty(notes = "Name of the Teknisi", name = "name", required = true, example = "Dante")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Name should have length between 1 and 50 characters")
	private String name;
	
	@Column(name="nik")
	@ApiModelProperty(notes = "Nik of the Teknisi", name = "nik", required = true, example = "2001732555")
	@Pattern(regexp="[\\d]{1,16}", message = "NIK should have length between 1 and 16 numeric")
	private String nik;
	
	@Column(name="address")
	@ApiModelProperty(notes = "Address of the Teknisi", name = "address", required = true, example = "Dubai")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Address should have length between 1 and 50 characters")
	private String address;
	
	@Column(name="email")
	@ApiModelProperty(notes = "Email of the Teknisi", name = "email", required = true, example = "dante@gmail.com")
	@Email(message = "Email should be valid")
	private String email;
	
	@Column(name="city")
	@ApiModelProperty(notes = "City of the Teknisi", name = "city", required = true, example = "Dubai")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "City should have length between 1 and 50 characters")
	private String city;
	
	@Column(name="postal_code")
	@ApiModelProperty(notes = "Postal_code of the Teknisi", name = "postal_code", required = true, example = "00119")
	@Pattern(regexp="[\\d]{1,5}", message = "Postal Code should have length between 1 and 5 numeric")
	private String postal_code;
	
	@Column(name="last_login")
	@ApiModelProperty(notes = "Last_login of the Teknisi", name = "last_login", required = true)
	@PastOrPresent
	private Date last_login;
	
	@NotBlank(message = "Teknisi longitude need to be filled")
    @Column(name="longitude")
	@ApiModelProperty(notes = "Longitude", name = "longitude", required = true, example = "5555555")
	private String longitude;
	
	@NotBlank(message = "Teknisi latitude need to be filled")
    @Column(name="latitude")
	@ApiModelProperty(notes = "Latitude", name = "latitude", required = true, example = "0000000")
	private String latitude;
	
	@Column(name="created_date")
	@PastOrPresent
	@ApiModelProperty(hidden = true)
	private Date created_date;
	
    @Column(name="created_by")
    @ApiModelProperty(hidden = true)
	private String created_by;
	
	@Column(name="update_date")
	@PastOrPresent
	@ApiModelProperty(hidden = true)
	private Date update_date;
	
    @Column(name="update_by")
    @ApiModelProperty(hidden = true)
	private String update_by;
	
	@OneToMany(mappedBy="teknisi_id")
	@ApiModelProperty(hidden = true)
    private List<Request> request = new ArrayList<Request>();
	
	
	public Teknisi() {
		
	}


	public Teknisi(@Max(value = 1000, message = "ID should not be greater than 1000") Long id,
			@Pattern(regexp = "[\\d]{1,13}", message = "NIK should have length between 1 and 13 numeric") String phone,
			@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Name should have length between 1 and 50 characters") String name,
			@Pattern(regexp = "[\\d]{1,16}", message = "NIK should have length between 1 and 16 numeric") String nik,
			@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Address should have length between 1 and 50 characters") String address,
			@Email(message = "Email should be valid") String email,
			@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "City should have length between 1 and 50 characters") String city,
			@Pattern(regexp = "[\\d]{1,5}", message = "Postal Code should have length between 1 and 5 numeric") String postal_code,
			@PastOrPresent Date last_login, @NotBlank(message = "Teknisi longitude need to be filled") String longitude,
			@NotBlank(message = "Teknisi latitude need to be filled") String latitude) {
		super();
		this.id = id;
		this.phone = phone;
		this.name = name;
		this.nik = nik;
		this.address = address;
		this.email = email;
		this.city = city;
		this.postal_code = postal_code;
		this.last_login = last_login;
		this.longitude = longitude;
		this.latitude = latitude;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getNik() {
		return nik;
	}


	public void setNik(String nik) {
		this.nik = nik;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getPostal_code() {
		return postal_code;
	}


	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}


	public Date getLast_login() {
		return last_login;
	}


	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
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


	public List<Request> getRequest() {
		return request;
	}


	public void setRequest(List<Request> request) {
		this.request = request;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Teknisi [id=");
		builder.append(id);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", name=");
		builder.append(name);
		builder.append(", nik=");
		builder.append(nik);
		builder.append(", address=");
		builder.append(address);
		builder.append(", email=");
		builder.append(email);
		builder.append(", city=");
		builder.append(city);
		builder.append(", postal_code=");
		builder.append(postal_code);
		builder.append(", last_login=");
		builder.append(last_login);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", created_date=");
		builder.append(created_date);
		builder.append(", created_by=");
		builder.append(created_by);
		builder.append(", update_date=");
		builder.append(update_date);
		builder.append(", update_by=");
		builder.append(update_by);
		builder.append(", request=");
		builder.append(request);
		builder.append("]");
		return builder.toString();
	}
	
}
