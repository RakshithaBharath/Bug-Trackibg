package com.excelr.bugtracking.service;

import java.util.List;

import com.excelr.bugtracking.dto.BugDTO;
import com.excelr.bugtracking.dto.BugRequest;
import com.excelr.bugtracking.exception.BugException;

public interface IBugService {

	List<BugDTO> fetchAll() throws BugException;

	BugDTO save(BugRequest bugRequest) throws BugException;

	BugDTO getByID(int id) throws BugException;

	List<BugDTO> getByApplicationID(int applicationid) throws BugException;

	List<BugDTO> getByReleaseID(int releaseId) throws BugException;

	String deleteById(int id) throws BugException;
	
	List<BugDTO> getByStatus(String status) throws BugException;
}