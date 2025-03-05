package com.excelr.bugtracking.service;

import java.util.List;

import com.excelr.bugtracking.dto.ApplicationDTO;
import com.excelr.bugtracking.dto.ApplicationRequest;
import com.excelr.bugtracking.exception.ApplicationException;

public interface IApplicationService {

	// fetch All Applications.
	List<ApplicationDTO> findAll();

	ApplicationDTO findById(int id) throws ApplicationException;

	ApplicationDTO findByName(String name) throws ApplicationException;

	ApplicationDTO findByOwner(String owner) throws ApplicationException;

	ApplicationDTO findByDescription(String description) throws ApplicationException;

	ApplicationDTO save(ApplicationRequest applicationRequest) throws ApplicationException;
	
	String delete(int id) throws ApplicationException;

}
