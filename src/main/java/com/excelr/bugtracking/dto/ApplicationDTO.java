package com.excelr.bugtracking.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApplicationDTO {

	private int id;
	private String name;
	private Date createdOn;
	private String description;
	private String owner;

}
