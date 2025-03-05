package com.excelr.bugtracking.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excelr.bugtracking.dto.ApplicationDTO;
import com.excelr.bugtracking.dto.ApplicationRequest;
import com.excelr.bugtracking.exception.ApplicationException;
import com.excelr.bugtracking.service.IApplicationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/applications")
public class ApplicationController {


	@Autowired
	private IApplicationService applicationService;

	// Fetching all applications
	@GetMapping()
	public ResponseEntity<List<ApplicationDTO>> getApplications() {
		log.info("Inside application controller and calling get application method ");

		List<ApplicationDTO> applicationDTOs = null;
		applicationDTOs = applicationService.findAll();
		log.info("Fetching Data from service layer: {}", applicationDTOs);

		if (CollectionUtils.isEmpty(applicationDTOs)) {
			log.info("Fetched 0 records from Database");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ApplicationDTO>>(applicationDTOs, HttpStatus.OK);
	}

	// Get application based on ID
	@GetMapping("/{id}")
	public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable("id") int id) throws ApplicationException {
		log.info("Inside application controller and calling get applicationById method ");
		ApplicationDTO applicationDTO = null;
		try {
			applicationDTO = applicationService.findById(id);
			log.info("Fetching data from service layer: {}", applicationDTO);

			if (applicationDTO == null) {
				log.info("Fetched 0 records from Database");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			}
		} catch (Exception e) {
			log.error("Exception while calling getApplication", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApplicationDTO>(applicationDTO, HttpStatus.OK);

	}

	@GetMapping("/findByName/{applicationName}")
	public ResponseEntity<ApplicationDTO> getApplicationByName(@PathVariable("applicationName") String name)
			throws ApplicationException {
		log.info("Inside application controller and calling get applicationByName method ");
		ApplicationDTO applicationDTO = null;

		try {
			applicationDTO = applicationService.findByName(name);
			log.info("Fetching data from service layer: {}", applicationDTO);

			if (applicationDTO == null) {
				log.info("Fetched 0 records from Database");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			}
		} catch (Exception e) {
			log.error("Exception while calling getApplication", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApplicationDTO>(applicationDTO, HttpStatus.OK);

	}
	
	@GetMapping("/findByOwner/{owner}")
	public ResponseEntity<ApplicationDTO> getApplicationByOwner(@PathVariable("owner") String owner)
			throws ApplicationException {
		log.info("Inside application controller and calling get applicationByOwner method ");
		ApplicationDTO applicationDTO = null;

		try {
			applicationDTO = applicationService.findByOwner(owner);
			log.info("Fetching data from service layer: {}", applicationDTO);

			if (applicationDTO == null) {
				log.info("Fetched 0 records from Database");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			}
		} catch (Exception e) {
			log.error("Exception while calling getApplication", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApplicationDTO>(applicationDTO, HttpStatus.OK);

	}
	
	@GetMapping("/findByDescription")
	public ResponseEntity<ApplicationDTO> getApplicationByDescription(@RequestParam("description") String description)
			throws ApplicationException {
		log.info("Inside application controller and calling get applicationBydescription method ");
		ApplicationDTO applicationDTO = null;

		try {
			applicationDTO = applicationService.findByDescription(description);
			log.info("Fetching data from service layer: {}", applicationDTO);

			if (applicationDTO == null) {
				log.info("Fetched 0 records from Database");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			}
		} catch (Exception e) {
			log.error("Exception while calling getApplication", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApplicationDTO>(applicationDTO, HttpStatus.OK);

	}

	@PostMapping()
	public ResponseEntity<ApplicationDTO> saveApplication(@RequestBody ApplicationRequest applicationRequest)
			throws ApplicationException {
		log.info("Inside application controller and calling get applicationByName method ");

		if (applicationRequest == null) {
			log.info("User has not sent data to save");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		ApplicationDTO applicationDTO = null;

		try {
			applicationDTO = applicationService.save(applicationRequest);
			log.info("Inserted data from service layer: {}", applicationDTO);

			if (applicationDTO == null) {
				log.info("Application details are not saved");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.error("Exception while saving application", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApplicationDTO>(applicationDTO, HttpStatus.CREATED);

	}

	@PutMapping()
	public ResponseEntity<ApplicationDTO> update(@RequestBody ApplicationRequest applicationRequest)
			throws ApplicationException {
		log.info("Inside application controller and calling update method ");

		if (applicationRequest == null) {
			log.info("User has not sent data to save");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		ApplicationDTO applicationDTO = null;

		try {
			applicationDTO = applicationService.save(applicationRequest);
			log.info("Updated data from service layer: {}", applicationDTO);

			if (applicationDTO == null) {
				log.info("Application details are not saved");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.error("Exception while saving application", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApplicationDTO>(applicationDTO, HttpStatus.OK);

	}

	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam("id") int id) throws ApplicationException {
		log.info("Delete Application inside controller method");
		String response;
		try {
			response = applicationService.delete(id);

		} catch (Exception e) {
			return new ResponseEntity<String>("Internal Error", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);

	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteId(@PathVariable("id") int id) throws ApplicationException {
		log.info("Delete Application inside controller method");
		String response;
		try {
			response = applicationService.delete(id);

		} catch (Exception e) {
			return new ResponseEntity<String>("Internal Error", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);

	}

}
