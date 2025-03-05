package com.excelr.bugtracking.dto;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BugDTO {

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
