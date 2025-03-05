package com.excelr.bugtracking.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelr.bugtracking.dto.ApplicationDTO;
import com.excelr.bugtracking.dto.ApplicationRequest;
import com.excelr.bugtracking.entity.Application;
import com.excelr.bugtracking.exception.ApplicationException;
import com.excelr.bugtracking.mapper.ConvertToDTO;
import com.excelr.bugtracking.repository.ApplicationRepository;

@Service
public class ApplicationServiceImpl implements IApplicationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceImpl.class);

	@Autowired
	ApplicationRepository applicationRepository;

	@Autowired
	private ConvertToDTO convertToDTO;

	@Override
	public List<ApplicationDTO> findAll() {
		LOGGER.info("Inside AplicationServiceImpl findAll method...");
		List<Application> applications = applicationRepository.findAll();
		LOGGER.info("Fetching application from DAO layer: {}", applications);

		// map entity to DTO
		List<ApplicationDTO> applicationDTOs = applications.parallelStream().map(application -> {
			ApplicationDTO applicationDTO = convertToDTO.mapToApplicationDTO(application);
			return applicationDTO;

		}).collect(Collectors.toList());

		return applicationDTOs;
	}
	//

	@Override
	public ApplicationDTO findById(int id) throws ApplicationException {
		// TODO Auto-generated method stub
		LOGGER.info("Inside AplicationServiceImpl findById method...");

		Optional<Application> applicationOptional = applicationRepository.findById(id);
		LOGGER.info("Fetching application by ID from DAO layer: {}", applicationOptional);

		if (applicationOptional.isPresent()) {
			Application application = applicationOptional.get();
			ApplicationDTO applicationDTO = convertToDTO.mapToApplicationDTO(application);
			return applicationDTO;
		} else {
			LOGGER.error("No Such Application Present");
			throw new ApplicationException("No Such Application found");

		}
	}

	@Override
	public ApplicationDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		LOGGER.info("Inside AplicationServiceImpl findById method...");

		if (name == null || name.trim().isEmpty()) {
			LOGGER.error("Application name cannot be null or empty");
			throw new ApplicationException("Application name is required");
		}

		Application application = applicationRepository.findByName(name.trim())
				.orElseThrow(() -> new ApplicationException("No Such Application found"));

		ApplicationDTO applicationDTO = convertToDTO.mapToApplicationDTO(application);
		return applicationDTO;

	}

	@Override
	public ApplicationDTO save(ApplicationRequest applicationRequest) throws ApplicationException {
		// TODO Auto-generated method stub
		LOGGER.info("Inside ApplicationServiceImpl save method {}", applicationRequest);

		if (applicationRequest == null) {
			LOGGER.error("Invalid Application request ");
			throw new ApplicationException("applicationrequest is missing");
		}

		if (applicationRequest.getName() == null) {
			LOGGER.error("Invalid Application request no name field ");
			throw new ApplicationException("Name field is missing");
		}

		Application application;

		if (applicationRequest.getId() <= 0) {
			// **CREATE Operation**
			if (applicationRepository.findByName(applicationRequest.getName()).isPresent()) {
				throw new ApplicationException("Application name already exists");
			}
			application = new Application();
		} else {
			// **UPDATE Operation**
			Optional<Application> existingApplication = applicationRepository.findById(applicationRequest.getId());
			if (existingApplication.isEmpty()) {
				throw new ApplicationException("Application with ID " + applicationRequest.getId() + " not found");
			}
			application = existingApplication.get();

			// **Check if new name conflicts with another application**
			if (!application.getName().equals(applicationRequest.getName())) { // Name is being changed
				Optional<Application> nameCheck = applicationRepository.findByName(applicationRequest.getName());
				if (nameCheck.isPresent() && (!(nameCheck.get().getId() == applicationRequest.getId()))) {
					throw new ApplicationException("Application name already exists");
				}
			}
		}

		application.setName(applicationRequest.getName());
		application.setOwner(applicationRequest.getOwner());
		application.setDescription(applicationRequest.getDescription());
		application.setCreatedOn(applicationRequest.getCreatedOn());

		Application applicationCreated = applicationRepository.save(application);

		ApplicationDTO applicationCreatedDTO = null;

		if (applicationCreated != null) {
			applicationCreatedDTO = convertToDTO.mapToApplicationDTO(application);

		}
		LOGGER.info("Data is being inserted");

		return applicationCreatedDTO;
	}

	@Override
	public String delete(int id) throws ApplicationException {
		// TODO Auto-generated method stub
		LOGGER.info("Inside ");
		if (id <= 0) {
			throw new ApplicationException("Enter a valid Application Id");
		}
		try {
			applicationRepository.deleteById(id);
			LOGGER.info("Deleted application successfully");
		} catch (Exception e) {
			throw new ApplicationException("Could not delete the application");
		}

		return "Successfully Deleted Application";
	}

	@Override
	public ApplicationDTO findByOwner(String owner) throws ApplicationException {
		LOGGER.info("Inside AplicationServiceImpl findByName method...");

		if (owner == null || owner.trim().isEmpty()) {
			LOGGER.error("Owner cannot be null or empty");
			throw new ApplicationException("owner is required");
		}

		Application application = applicationRepository.findByOwner(owner.trim())
				.orElseThrow(() -> new ApplicationException("No Application found"));

		ApplicationDTO applicationDTO = convertToDTO.mapToApplicationDTO(application);
		return applicationDTO;

	}

	@Override
	public ApplicationDTO findByDescription(String description) throws ApplicationException {
		LOGGER.info("Inside AplicationServiceImpl findBydescription method...");

		if (description == null || description.trim().isEmpty()) {
			LOGGER.error("Owner cannot be null or empty");
			throw new ApplicationException("owner is required");
		}

		Application application = applicationRepository.findBydescription(description.trim())
				.orElseThrow(() -> new ApplicationException("No Application found"));

		ApplicationDTO applicationDTO = convertToDTO.mapToApplicationDTO(application);
		return applicationDTO;

	}

}
