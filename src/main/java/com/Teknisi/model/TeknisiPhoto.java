package com.Teknisi.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "teknisi_photo")
@ApiModel(description = "Teknisi Photo Model")
public class TeknisiPhoto implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID of the Teknisi Photo", name = "id", required = true, example = "100")
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
	@NotNull(message = "Teknisi Photo ID cannot be blank")
	private Long id;
	
	
	@Column(name = "teknisi_id")
    @ApiModelProperty(hidden = true)
	private int teknisi_id;
	
	@Column(name="file_type")
	@NotBlank(message = "Person in charge cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "PIC should have length between 1 and 50 characters")
	@ApiModelProperty(notes = "File type of the ", name = "file_type", required = true, example = "")
	private String file_type;
	
	@ApiModelProperty(notes = "Name of the Teknisi", name = "name", required = true, example = "Dante")
	@Column(name="name")
	@NotBlank(message = "Name cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}+$", message = "Name should have length between 1 and 50 characters")
	private String name;
	
	@ApiModelProperty(notes = "Images of the Teknisi", name = "images", required = true, example = "Dante.jpg")
	@Column(name="images")
	@NotBlank(message = "Images cannot be blank")
	private String images;
	
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
    
    public TeknisiPhoto () {
    	
    }

	public TeknisiPhoto(@NotNull(message = "Teknisi Photo ID cannot be blank") Long id, int teknisi_id,
			@NotBlank(message = "Person in charge cannot be blank") @Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "PIC should have length between 1 and 50 characters") String file_type,
			@NotBlank(message = "Name cannot be blank") @Pattern(regexp = "^[A-Za-z0-9]{1,50}+$", message = "Name should have length between 1 and 50 characters") String name,
			@NotBlank(message = "Images cannot be blank") String images) {
		super();
		this.id = id;
		this.teknisi_id = teknisi_id;
		this.file_type = file_type;
		this.name = name;
		this.images = images;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTeknisi_id() {
		return teknisi_id;
	}

	public void setTeknisi_id(int teknisi_id) {
		this.teknisi_id = teknisi_id;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
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
		builder.append("TeknisiPhoto [id=");
		builder.append(id);
		builder.append(", teknisi_id=");
		builder.append(teknisi_id);
		builder.append(", file_type=");
		builder.append(file_type);
		builder.append(", name=");
		builder.append(name);
		builder.append(", images=");
		builder.append(images);
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
