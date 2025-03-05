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
public class ReleaseDTO {

	private int id;
	private String name;
	private Date releaseDate;
	private String status;
	private String comments;
	private String description;

}
