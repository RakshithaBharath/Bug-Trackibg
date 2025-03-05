package com.excelr.bugtracking.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReleaseRequest {
	private int id;
	private String name;
	private Date releaseDate;
	private String status;
	private String comments;
	private String description;

}
