package com.excelr.bugtracking.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "RELEASE_TBL")
public class Release implements Serializable {

	private static final long serialVersionUID = -1043326770647113305L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "release_gen")
	@SequenceGenerator(name = "release_gen", sequenceName = "RELEASE_TBL_SEQ", allocationSize = 1)
	@Column(name = "RELEASE_ID")
	private int id;

	@NotBlank(message = "Application name cannot be blank")
	@Size(min = 3, max = 255, message = "Release name must not exceed 255 characters")
	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name = "RELEASE_DATE")
	private Date releaseDate;

	private String description;
	private String status;
	private String comments;

}
