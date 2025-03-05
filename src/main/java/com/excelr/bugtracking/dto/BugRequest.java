package com.excelr.bugtracking.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BugRequest {
	private int id;
	private String type;
	private String applicationImpacted;
	private String assignedTo;
	private Date createdOn;
	private String createdBy;
	private String status;
	private String description;
	private int applicationId;
	private int releaseId;

}
