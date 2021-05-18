package com.Teknisi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
	

	@ApiModelProperty(notes = "ID of the Teknisi", name = "id", required = true, example = "100")
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
	@NotNull(message = "ID cannot be blank")
	@Max(value = 1000, message = "ID should not be greater than 1000")
	private Long id;
	

	@ApiModelProperty(notes = "Phone of the Teknisi", name = "phone", required = true, example = "08194455001")
	@Column(name="phone")
	@NotBlank(message = "Phone cannot be blank")
	@Pattern(regexp="[\\d]{1,13}", message = "NIK should have length between 1 and 13 numeric")
	private String phone;

	@ApiModelProperty(notes = "Name of the Teknisi", name = "name", required = true, example = "Dante")
	@Column(name="name")
	@NotBlank(message = "Name cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}+$", message = "Name should have length between 1 and 50 characters")
	private String name;

	@ApiModelProperty(notes = "Nik of the Teknisi", name = "nik", required = true, example = "2001732555")
	@Column(name="nik")
	@NotBlank(message = "NIK cannot be blank")
	@Pattern(regexp="[\\d]{1,16}", message = "NIK should have length between 1 and 16 numeric")
	private String nik;

	@ApiModelProperty(notes = "Address of the Teknisi", name = "address", required = true, example = "Dubai")
	@Column(name="address")
	@NotBlank(message = "Address cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Address should have length between 1 and 50 characters")
	private String address;

	@ApiModelProperty(notes = "Email of the Teknisi", name = "email", required = true, example = "dante@gmail.com")
	@Column(name="email")
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email should be valid")
	private String email;

	@ApiModelProperty(notes = "City of the Teknisi", name = "city", required = true, example = "Dubai")
	@Column(name="city")
	@NotBlank(message = "City cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "City should have length between 1 and 50 characters")
	private String city;

	@ApiModelProperty(notes = "Postal_code of the Teknisi", name = "postal_code", required = true, example = "00119")
	@Column(name="postal_code")
	@NotBlank(message = "Postal Code cannot be blank")
	@Pattern(regexp="[\\d]{1,5}", message = "Postal Code should have length between 1 and 5 numeric")
	private String postal_code;

	@ApiModelProperty(notes = "Last_login of the Teknisi", name = "last_login", required = true)
	@Column(name="last_login")
	@PastOrPresent
	private Date last_login;

	@ApiModelProperty(notes = "Longitude", name = "longitude", required = true, example = "5555555")
	@NotBlank(message = "Teknisi longitude need to be filled")
    @Column(name="longitude")
	private String longitude;

	@ApiModelProperty(notes = "Latitude", name = "latitude", required = true, example = "0000000")
	@NotBlank(message = "Teknisi latitude need to be filled")
    @Column(name="latitude")
	private String latitude;

	@ApiModelProperty(hidden = true)
	@Column(name="created_date")
	@PastOrPresent
	private Date created_date;

    @ApiModelProperty(hidden = true)
    @Column(name="created_by")
	private String created_by;

	@ApiModelProperty(hidden = true)
	@Column(name="update_date")
	@PastOrPresent
	private Date update_date;

    @ApiModelProperty(hidden = true)
    @Column(name="update_by")
	private String update_by;

	@OneToMany(mappedBy="teknisi_id")
    private List<Request> request = new ArrayList<Request>();
	
	@OneToOne(mappedBy = "teknisi")
    private TeknisiPhoto teknisiPhoto;
	
	public Teknisi() {
		
	}

	public Teknisi(
			@NotNull(message = "ID cannot be blank") @Max(value = 1000, message = "ID should not be greater than 1000") Long id,
			@NotBlank(message = "Phone cannot be blank") @Pattern(regexp = "[\\d]{1,13}", message = "NIK should have length between 1 and 13 numeric") String phone,
			@NotBlank(message = "Name cannot be blank") @Pattern(regexp = "^[A-Za-z0-9]{1,50}+$", message = "Name should have length between 1 and 50 characters") String name,
			@NotBlank(message = "NIK cannot be blank") @Pattern(regexp = "[\\d]{1,16}", message = "NIK should have length between 1 and 16 numeric") String nik,
			@NotBlank(message = "Address cannot be blank") @Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Address should have length between 1 and 50 characters") String address,
			@NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid") String email,
			@NotBlank(message = "City cannot be blank") @Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "City should have length between 1 and 50 characters") String city,
			@NotBlank(message = "Postal Code cannot be blank") @Pattern(regexp = "[\\d]{1,5}", message = "Postal Code should have length between 1 and 5 numeric") String postal_code,
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

	public TeknisiPhoto getTeknisiPhoto() {
		return teknisiPhoto;
	}

	public void setTeknisiPhoto(TeknisiPhoto teknisiPhoto) {
		this.teknisiPhoto = teknisiPhoto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((created_by == null) ? 0 : created_by.hashCode());
		result = prime * result + ((created_date == null) ? 0 : created_date.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((last_login == null) ? 0 : last_login.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nik == null) ? 0 : nik.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((postal_code == null) ? 0 : postal_code.hashCode());
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result + ((teknisiPhoto == null) ? 0 : teknisiPhoto.hashCode());
		result = prime * result + ((update_by == null) ? 0 : update_by.hashCode());
		result = prime * result + ((update_date == null) ? 0 : update_date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teknisi other = (Teknisi) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (created_by == null) {
			if (other.created_by != null)
				return false;
		} else if (!created_by.equals(other.created_by))
			return false;
		if (created_date == null) {
			if (other.created_date != null)
				return false;
		} else if (!created_date.equals(other.created_date))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (last_login == null) {
			if (other.last_login != null)
				return false;
		} else if (!last_login.equals(other.last_login))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nik == null) {
			if (other.nik != null)
				return false;
		} else if (!nik.equals(other.nik))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (postal_code == null) {
			if (other.postal_code != null)
				return false;
		} else if (!postal_code.equals(other.postal_code))
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		if (teknisiPhoto == null) {
			if (other.teknisiPhoto != null)
				return false;
		} else if (!teknisiPhoto.equals(other.teknisiPhoto))
			return false;
		if (update_by == null) {
			if (other.update_by != null)
				return false;
		} else if (!update_by.equals(other.update_by))
			return false;
		if (update_date == null) {
			if (other.update_date != null)
				return false;
		} else if (!update_date.equals(other.update_date))
			return false;
		return true;
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
		builder.append(", teknisiPhoto=");
		builder.append(teknisiPhoto);
		builder.append("]");
		return builder.toString();
	}
}
