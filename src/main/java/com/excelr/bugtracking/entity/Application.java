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
@Table(name = "APPLICATION_TBL")
public class Application implements Serializable {

	private static final long serialVersionUID = 250761684892939162L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_gen")
	@SequenceGenerator(name = "application_gen", sequenceName = "APPLICATION_TBL_SEQ", allocationSize = 1)
	@Column(name = "APPLICATION_ID")
	private int id;

	@NotBlank(message = "Application name cannot be blank")
	@Size(min= 3, max = 255, message = "Application name must not exceed 255 characters")
	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_ON")
	private Date createdOn;

	private String description;
	
	@NotBlank(message = "Application owner cannot be blank")
	@Size(min= 3, max = 255, message = "Application owner must not exceed 255 characters")
	private String owner;

}
