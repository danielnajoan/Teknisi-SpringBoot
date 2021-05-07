package com.Teknisi.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Teknisi Model")
public class Teknisi implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "ID of the Teknisi", name = "id", required = true, example = "100")
	private Long id;
	
	@ApiModelProperty(notes = "Phone of the Teknisi", name = "phone", required = true, example = "08194455001")
	private String phone;
	
	@ApiModelProperty(notes = "Name of the Teknisi", name = "name", required = true, example = "Dante")
	private String name;
	
	@ApiModelProperty(notes = "Nik of the Teknisi", name = "nik", required = true, example = "2001732555")
	private String nik;
	
	@ApiModelProperty(notes = "Address of the Teknisi", name = "address", required = true, example = "Dubai")
	private String address;
	
	@ApiModelProperty(notes = "Email of the Teknisi", name = "email", required = true, example = "dante@gmail.com")
	private String email;
	
	@ApiModelProperty(notes = "City of the Teknisi", name = "city", required = true, example = "Dubai")
	private String city;
	
	@ApiModelProperty(notes = "Postal_code of the Teknisi", name = "postal_code", required = true, example = "00119")
	private String postal_code;
	
	@ApiModelProperty(notes = "Last_login of the Teknisi", name = "last_login", required = true)
	private Date last_login;
	
	@ApiModelProperty(notes = "Longitude", name = "longitude", required = true, example = "5555555")
	private String longitude;
	
	@ApiModelProperty(notes = "Latitude", name = "latitude", required = true, example = "0000000")
	private String latitude;
	
	@ApiModelProperty(notes = "Created_date of the Teknisi", name = "created_date", required = true)
	private Date created_date;
	
	@ApiModelProperty(notes = "The Teknisi created_by", name = "created_by", required = true, example = "Merilda")
	private String created_by;
	
	@ApiModelProperty(notes = "Update_date of the Teknisi", name = "update_date", required = true)
	private Date update_date;
	
	@ApiModelProperty(notes = "The Teknisi update_by", name = "update_by", required = true, example  = "Esther")
	private String update_by;
	
	public Teknisi() {
		
	}

	public Teknisi(Long id, String phone, String name, String nik, String address, String email, String city,
			String postal_code, Date last_login, String longitude, String latitude) {
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
		builder.append("]");
		return builder.toString();
	}

	
}
