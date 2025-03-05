package com.excelr.bugtracking.entity;

import java.io.Serializable;
import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = {"release", "application"})
@Table(name = "BUG_TBL")
public class Bug implements Serializable {

	private static final long serialVersionUID = -6900774692774651793L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bug_seq")
	@SequenceGenerator(name = "bug_seq", sequenceName = "BUG_TBL_SEQ", allocationSize = 1)
	@Column(name = "BUG_ID")
	private int id;

	@Column(name = "BUG_TYPE")
	private String type;

	@Column(name = "APPLICATION_IMPACTED")
	private String applicationImpacted;

	@Column(name = "ASSIGNED_TO")
	private String assignedTo;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	private String status;
	private String description;

	@ManyToOne
	@JoinColumn(name = "APPLICATION_ID")
	private Application application;

	@ManyToOne
	@JoinColumn(name = "RELEASE_ID")
	private Release release;

}
