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

	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_ON")
	private Date createdOn;

	private String description;
	private String owner;


}
