package com.excelr.bugtracking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excelr.bugtracking.dto.BugDTO;
import com.excelr.bugtracking.dto.BugRequest;
import com.excelr.bugtracking.exception.BugException;
import com.excelr.bugtracking.service.IBugService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/api/bugs")
public class BugController {

	@Autowired
	private IBugService iBugService;

	@GetMapping
	public ResponseEntity<List<BugDTO>> findAll() throws BugException {
		log.info("Inside Controller Class - findAll method");
		List<BugDTO> bugDTOs = null;
		try {
			bugDTOs = iBugService.fetchAll();
			if (bugDTOs == null) {
				log.info("No Content to fetch");
				return new ResponseEntity<List<BugDTO>>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			log.error("Error while fetching Data");
			return new ResponseEntity<List<BugDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<BugDTO>>(bugDTOs, HttpStatus.ACCEPTED);
	}

	@PostMapping
	public ResponseEntity<BugDTO> insert(@RequestBody BugRequest bugRequest) throws BugException {
		log.info("Inside Controller Class - save method");
		BugDTO bugDTOs = null;
		try {
			bugDTOs = iBugService.save(bugRequest);
			if (bugDTOs == null) {
				log.info("Could not save data");
				return new ResponseEntity<BugDTO>(HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			log.error("Error while fetching Data");
			return new ResponseEntity<BugDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<BugDTO>(bugDTOs, HttpStatus.ACCEPTED);

	}

	@PutMapping
	public ResponseEntity<BugDTO> update(@RequestBody BugRequest bugRequest) throws BugException {
		log.info("Inside Controller Class - update method");
		BugDTO bugDTOs = null;
		try {
			bugDTOs = iBugService.save(bugRequest);
			if (bugDTOs == null) {
				log.info("Could not update data");
				return new ResponseEntity<BugDTO>(HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			log.error("Error while fetching Data");
			return new ResponseEntity<BugDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<BugDTO>(bugDTOs, HttpStatus.ACCEPTED);

	}

	@GetMapping("/{id}")
	public ResponseEntity<BugDTO> findById(@PathVariable("id") int id) throws BugException {
		log.info("Inside Controller Class - findById method");
		BugDTO bugDTOs = null;
		try {
			bugDTOs = iBugService.getByID(id);
			if (bugDTOs == null) {
				log.info("Could not fetch data by provided id");
				return new ResponseEntity<BugDTO>(HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			log.error("Error while fetching Data");
			return new ResponseEntity<BugDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<BugDTO>(bugDTOs, HttpStatus.ACCEPTED);

	}

	@GetMapping("/GetByApplicationId")
	public ResponseEntity<List<BugDTO>> findByApplicationId(@RequestParam("id") int id) throws BugException {
		log.info("Inside Controller Class - findByApplicationId method");
		List<BugDTO> bugDTOs = null;
		try {
			bugDTOs = iBugService.getByApplicationID(id);
			if (bugDTOs == null) {
				log.info("Could not fetch data by provided id");
				return new ResponseEntity<List<BugDTO>>(HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			log.error("Error while fetching Data: {}", e.getMessage(), e); // Logs full stack trace
			return new ResponseEntity<List<BugDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<BugDTO>>(bugDTOs, HttpStatus.ACCEPTED);

	}

	@GetMapping("/GetByReleaseId")
	public ResponseEntity<List<BugDTO>> findByReleaseId(@RequestParam("id") int id) throws BugException {
		log.info("Inside Controller Class - findByReleaseId method");
		List<BugDTO> bugDTOs = null;
		try {
			bugDTOs = iBugService.getByReleaseID(id);
			if (bugDTOs == null) {
				log.info("Could not fetch data by provided id");
				return new ResponseEntity<List<BugDTO>>(HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			log.error("Error while fetching Data: {}", e.getMessage(), e); // Logs full stack trace
			return new ResponseEntity<List<BugDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<BugDTO>>(bugDTOs, HttpStatus.ACCEPTED);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") int id) throws BugException {
		try {
			String response = iBugService.deleteById(id);
			return new ResponseEntity<String>(response, HttpStatus.OK);

		} catch (Exception e) {

			log.error("Error while Deleting Data: {}", e.getMessage(), e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

}
