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
import org.springframework.web.bind.annotation.RestController;

import com.excelr.bugtracking.dto.ReleaseDTO;
import com.excelr.bugtracking.dto.ReleaseRequest;
import com.excelr.bugtracking.exception.ReleaseException;
import com.excelr.bugtracking.service.IReleaseService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/releases")
public class ReleaseController {


	@Autowired
	private IReleaseService iReleaseService;

	@GetMapping()
	public ResponseEntity<List<ReleaseDTO>> getAllReleases() throws ReleaseException {
		log.info("Inside Controller Class - getALlReleases Method");
		List<ReleaseDTO> releaseDTOs = null;
		log.info("SEnding request to service class");

		try {
			releaseDTOs = iReleaseService.findAll();
			if (releaseDTOs == null) {
				return new ResponseEntity<List<ReleaseDTO>>(HttpStatus.NO_CONTENT);
			}
		} catch (ReleaseException e) {
			return new ResponseEntity<List<ReleaseDTO>>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<ReleaseDTO>>(releaseDTOs, HttpStatus.ACCEPTED);

	}

	@PostMapping
	public ResponseEntity<ReleaseDTO> save(@Valid @RequestBody ReleaseRequest releaseRequest) throws ReleaseException {

		log.info("Inside Controller - save method");
		ReleaseDTO releaseDTO = null;
		try {
			releaseDTO = iReleaseService.save(releaseRequest);
			if (releaseDTO == null) {
				return new ResponseEntity<ReleaseDTO>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<ReleaseDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ReleaseDTO>(releaseDTO, HttpStatus.CREATED);

	}

	@PutMapping()
	public ResponseEntity<ReleaseDTO> update(@Valid @RequestBody ReleaseRequest releaseRequest) throws ReleaseException {

		log.info("Inside Controller - update method");
		ReleaseDTO releaseDTO = null;
		try {
			releaseDTO = iReleaseService.save(releaseRequest);
			if (releaseDTO == null) {
				return new ResponseEntity<ReleaseDTO>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<ReleaseDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ReleaseDTO>(releaseDTO, HttpStatus.ACCEPTED);

	}

	@GetMapping("{id}")
	public ResponseEntity<ReleaseDTO> fetchById(@PathVariable("id") int id) {

		log.info("Inside controller class- fetchById Method");
		ReleaseDTO releaseDTO = null;
		log.info("fetching releases By Id");

		try {
			releaseDTO = iReleaseService.fetchById(id);
			if (releaseDTO == null) {
				return new ResponseEntity<ReleaseDTO>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<ReleaseDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ReleaseDTO>(releaseDTO, HttpStatus.OK);

	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteId(@PathVariable("id") int id) throws ReleaseException {
		log.info("Delete Release inside controller method");
		String response;
		try {
			response = iReleaseService.delete(id);

		} catch (Exception e) {
			return new ResponseEntity<String>("Internal Error", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);

	}

}
