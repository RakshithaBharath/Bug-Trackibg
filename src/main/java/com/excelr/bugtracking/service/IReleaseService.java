package com.excelr.bugtracking.service;

import java.util.List;

import com.excelr.bugtracking.dto.ReleaseDTO;
import com.excelr.bugtracking.dto.ReleaseRequest;
import com.excelr.bugtracking.exception.ReleaseException;

public interface IReleaseService {
	
	List<ReleaseDTO> findAll() throws ReleaseException;
		
	ReleaseDTO save(ReleaseRequest releaseRequest) throws ReleaseException;

	ReleaseDTO fetchById(int id)  throws ReleaseException;
	
	String delete(int id) throws ReleaseException;
	
	ReleaseDTO findByName(String name)  throws ReleaseException;
}
