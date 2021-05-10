package com.Teknisi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "request")
@ApiModel(description = "Request Model")
public class Request {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="request_id")
	@ApiModelProperty(notes = "Request ID of the Merchant", name = "id", required = true, example = "Req100")
	@Pattern(regexp = "^[A-Za-z0-9]{1,10}$", message = "Request ID should have length between 1 and 10 Alphanumeric characters")
	private String request_id;
	
	@Column(name="merchant_name ")
	@ApiModelProperty(notes = "Name of the Merchant", name = "merchant_name", required = true, example = "Apolos")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Name should have length between 1 and 50 characters")
	private String merchant_name;
	
	@Column(name="address")
	@ApiModelProperty(notes = "address of the Merchant", name = "address", required = true, example = "Jalan bunga matahari IX, Bekasi")
	@Pattern(regexp = "^[-a-zA-Z0-9,]{5,140}$", message = "Address should have length between 5 and 140 characters")
	private String address;
	
	@Column(name="city")
	@ApiModelProperty(notes = "City of the Merchant", name = "city", required = true, example = "Markus")
	@Pattern(regexp = "^[-a-zA-Z0-9,]{1,25}$", message = "City should have length between 1 and 25 characters")
	private String city;
	
	@Column(name="postal_code")
	@ApiModelProperty(notes = "Postal_code of the Merchant", name = "postal_code", required = true, example = "00119")
	@Pattern(regexp="[\\d]{1,5}", message = "Postal Code should have length between 1 and 5 numeric")
	private String postal_code;
	
	@Column(name="phone")
	@ApiModelProperty(notes = "Phone of the Teknisi", name = "phone", required = true, example = "08194455001")
	@Pattern(regexp="[\\d]{1,13}", message = "NIK should have length between 1 and 13 numeric")
	private String phone;
	
	@Column(name="pic")
	@ApiModelProperty(notes = "PIC of the Merchant", name = "pic", required = true, example = "Devin")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "PIC should have length between 1 and 50 characters")
	private String person_in_charge;
	
	@ManyToOne
    @JoinColumn(name="teknisi_id", nullable=false)
    private Teknisi teknisi_id;
	
	@Column(name="created_date")
	@ApiModelProperty(notes = "Created_date of the Request", name = "created_date", required = true)
	@PastOrPresent
	private Date created_date;
	
	@NotBlank(message = "Request created_by need to be filled")
    @Column(name="created_by")
	@ApiModelProperty(notes = "The Teknisi created_by", name = "created_by", required = true, example = "Merilda")
	private String created_by;
	
	@Column(name="update_date")
	@ApiModelProperty(notes = "Update_date of the Request", name = "update_date", required = true)
	@PastOrPresent
	private Date update_date;
	
	@NotBlank(message = "Request update_by need to be filled")
    @Column(name="update_by")
	@ApiModelProperty(notes = "The Teknisi update_by", name = "update_by", required = true, example  = "Esther")
	private String update_by;
	
	public Request() {
		
	}

	public Request(
			@Pattern(regexp = "^[A-Za-z0-9]{1,10}$", message = "Request ID should have length between 1 and 10 Alphanumeric characters") String request_id,
			@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Name should have length between 1 and 50 characters") String merchant_name,
			@Pattern(regexp = "^[-a-zA-Z0-9,]{5,140}$", message = "Address should have length between 5 and 140 characters") String address,
			@Pattern(regexp = "^[-a-zA-Z0-9,]{1,25}$", message = "City should have length between 1 and 25 characters") String city,
			@Pattern(regexp = "[\\d]{1,5}", message = "Postal Code should have length between 1 and 5 numeric") String postal_code,
			@Pattern(regexp = "[\\d]{1,13}", message = "NIK should have length between 1 and 13 numeric") String phone,
			@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "PIC should have length between 1 and 50 characters") String person_in_charge,
			Teknisi teknisi_id) {
		super();
		this.request_id = request_id;
		this.merchant_name = merchant_name;
		this.address = address;
		this.city = city;
		this.postal_code = postal_code;
		this.phone = phone;
		this.person_in_charge = person_in_charge;
		this.teknisi_id = teknisi_id;
	}

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public String getMerchant_name() {
		return merchant_name;
	}

	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPerson_in_charge() {
		return person_in_charge;
	}

	public void setPerson_in_charge(String person_in_charge) {
		this.person_in_charge = person_in_charge;
	}

	public Teknisi getTeknisi_id() {
		return teknisi_id;
	}

	public void setTeknisi_id(Teknisi teknisi_id) {
		this.teknisi_id = teknisi_id;
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
		builder.append("Request [request_id=");
		builder.append(request_id);
		builder.append(", merchant_name=");
		builder.append(merchant_name);
		builder.append(", address=");
		builder.append(address);
		builder.append(", city=");
		builder.append(city);
		builder.append(", postal_code=");
		builder.append(postal_code);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", person_in_charge=");
		builder.append(person_in_charge);
		builder.append(", teknisi_id=");
		builder.append(teknisi_id);
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
