package com.excelr.bugtracking.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.relation.RelationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelr.bugtracking.dto.BugDTO;
import com.excelr.bugtracking.dto.BugRequest;
import com.excelr.bugtracking.entity.Application;
import com.excelr.bugtracking.entity.Bug;
import com.excelr.bugtracking.entity.Release;
import com.excelr.bugtracking.exception.ApplicationException;
import com.excelr.bugtracking.exception.BugException;
import com.excelr.bugtracking.mapper.ConvertToDTO;
import com.excelr.bugtracking.repository.ApplicationRepository;
import com.excelr.bugtracking.repository.BugRepository;
import com.excelr.bugtracking.repository.ReleaseRepository;

@Service
public class BugServiceImpl implements IBugService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BugServiceImpl.class);

	@Autowired
	private BugRepository bugRepository;

	@Autowired
	private ConvertToDTO convertToDTO;

	@Autowired
	private ReleaseRepository releaseRepository;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Override
	public List<BugDTO> fetchAll() throws BugException {
		// TODO Auto-generated method stub
		LOGGER.info("Inside Service class - FetchAll method");
		List<BugDTO> bugDTOs = null;

		try {
			List<Bug> bugs = bugRepository.findAll();
			bugDTOs = bugs.parallelStream().map(bug -> {
				BugDTO bugDTO = convertToDTO.mapToBugDTO(bug);
				return bugDTO;
			}).collect(Collectors.toList());

		} catch (Exception e) {
			throw new BugException("Error fetching data");
		}
		return bugDTOs;
	}

	@Override
	public BugDTO save(BugRequest bugRequest) throws BugException {
		// TODO Auto-generated method stub
		LOGGER.info("Inside Service class - save");

		if (bugRequest == null) {
			LOGGER.error("RequestBody is null", bugRequest);
			throw new BugException("RequestBody is null");
		}

		Bug bug = new Bug();

		if (bugRequest.getId() >= 0) {
			LOGGER.info("Updating the existing data");
			bug.setId(bugRequest.getId());
		}

		try {
			bug.setType(bugRequest.getType());
			bug.setApplicationImpacted(bugRequest.getApplicationImpacted());
			bug.setAssignedTo(bugRequest.getAssignedTo());
			bug.setCreatedOn(bugRequest.getCreatedOn());
			bug.setCreatedBy(bugRequest.getCreatedBy());
			bug.setStatus(bugRequest.getStatus());
			bug.setDescription(bugRequest.getDescription());

			LOGGER.info("Fetching application Data");
			Application application = applicationRepository.findById(bugRequest.getApplicationId())
					.orElseThrow(() -> new ApplicationException("Could not fetch application by ID provided"));
			LOGGER.info("Fetched Application {}", application);

			LOGGER.info("Fetching release data");
			Release release = releaseRepository.findById(bugRequest.getReleaseId())
					.orElseThrow(() -> new RelationException("Could not fetch release by ID provided"));
			LOGGER.info("Fetched release data {}", release);

			bug.setApplication(application);
			bug.setRelease(release);

			Bug createdBug = bugRepository.save(bug);
			LOGGER.info("Data inserted is {}", createdBug);

			BugDTO bugDTO = convertToDTO.mapToBugDTO(createdBug);

			return bugDTO;

		} catch (Exception e) {
			throw new BugException("Could not save the details");
		}
	}

	@Override
	public BugDTO getByID(int id) throws BugException {
		// TODO Auto-generated method stub
		LOGGER.info("Inside service class");
		if (id <= 0) {
			LOGGER.error("Enter Valid id");
			throw new BugException("Enter Valid id");
		}

		Bug bug = bugRepository.findById(id).orElse(null);
		LOGGER.info("Fetched details {}", bug);

		BugDTO bugDTO = convertToDTO.mapToBugDTO(bug);
		return bugDTO;
	}

	@Override
	public List<BugDTO> getByApplicationID(int applicationid) throws BugException {
		// TODO Auto-generated method stub
		LOGGER.info("Inside service class where we fetch by application ID is {}", applicationid);
		if (applicationid <= 0) {
			LOGGER.error("Enter Valid id");
			throw new BugException("Enter Valid id");
		}

		List<Bug> bugs = bugRepository.getByApplicationId(applicationid);
		LOGGER.info("Fetched details {}", bugs);

		if (bugs == null || bugs.isEmpty()) {
			LOGGER.info("No bugs found for application ID: {}", applicationid);
			return Collections.emptyList();
		}

		List<BugDTO> bugDTOs = bugs.parallelStream().map(convertToDTO::mapToBugDTO).collect(Collectors.toList());
		return bugDTOs;
	}

	@Override
	public List<BugDTO> getByReleaseID(int releaseId) throws BugException {
		// TODO Auto-generated method stub
		LOGGER.info("Inside service class where we fetch by releaseId is {}", releaseId);
		if (releaseId <= 0) {
			LOGGER.error("Enter Valid id");
			throw new BugException("Enter Valid id");
		}

		List<Bug> bugs = bugRepository.getByReleaseId(releaseId);
		LOGGER.info("Fetched details {}", bugs);

		if (bugs == null || bugs.isEmpty()) {
			LOGGER.info("No bugs found for releaseId {}", releaseId);
			return Collections.emptyList();
		}

		List<BugDTO> bugDTOs = bugs.parallelStream().map(convertToDTO::mapToBugDTO).collect(Collectors.toList());
		return bugDTOs;
	}

}
