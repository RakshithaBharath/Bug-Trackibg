package com.excelr.bugtracking.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelr.bugtracking.dto.ReleaseDTO;
import com.excelr.bugtracking.dto.ReleaseRequest;
import com.excelr.bugtracking.entity.Release;
import com.excelr.bugtracking.exception.ReleaseException;
import com.excelr.bugtracking.mapper.ConvertToDTO;
import com.excelr.bugtracking.repository.ReleaseRepository;

@Service
public class ReleaseServiceImpl implements IReleaseService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ReleaseServiceImpl.class);

	@Autowired
	private ReleaseRepository releaseRepository;

	@Autowired
	private ConvertToDTO convertToDTO;

	@Override
	public List<ReleaseDTO> findAll() throws ReleaseException {
		// TODO Auto-generated method stub
		LOGGER.info("Inside SErvice class - findall method");
		List<Release> releases = releaseRepository.findAll();
		LOGGER.info("Fetch Releases from DAO layer {}", releases);

		try {
			List<ReleaseDTO> releaseDTOs = releases.parallelStream().map(release -> {
				ReleaseDTO releaseDTO = new ReleaseDTO();
				releaseDTO = convertToDTO.mapToReleaseDTO(release);

				return releaseDTO;
			}).collect(Collectors.toList());
			return releaseDTOs;

		} catch (Exception e) {
			throw new ReleaseException("Error fetching the data");
		}

	}

	@Override
	public ReleaseDTO save(ReleaseRequest releaseRequest) throws ReleaseException {
		// TODO Auto-generated method stub
		LOGGER.info("Inside SErvice class - save method");
		if (releaseRequest == null) {
			throw new ReleaseException("Request is empty");
		}
		if (releaseRequest.getName() == null) {
			throw new ReleaseException("Release name cannot be Empty");
		}
		Release release = new Release();

		if (releaseRequest.getId() >= 1) {
			release.setId(releaseRequest.getId());
		}

		try {
			release.setName(releaseRequest.getName());
			release.setReleaseDate(releaseRequest.getReleaseDate());
			release.setStatus(releaseRequest.getStatus());
			release.setComments(releaseRequest.getComments());
			release.setDescription(releaseRequest.getDescription());

			Release releaseCreated = releaseRepository.save(release);

			LOGGER.info("Saved data Release from DAO layer {}", releaseCreated);

			ReleaseDTO dto = null;

			if (releaseCreated != null) {
				dto = convertToDTO.mapToReleaseDTO(releaseCreated);
			}

			return dto;
		} catch (Exception e) {
			throw new ReleaseException("Exception thrown while saving Data");
		}
	}

	@Override
	public ReleaseDTO fetchById(int id) throws ReleaseException {
		// TODO Auto-generated method stub
		if (id <= 0) {
			LOGGER.error("InValid ID Could not fetch");
			throw new ReleaseException("Enter valid id");
		}

		ReleaseDTO releaseDTO = null;

		try {
			Optional<Release> release = releaseRepository.findById(id);
			LOGGER.info("Fetched data is {}", release);
			if (release.isPresent()) {
				releaseDTO = convertToDTO.mapToReleaseDTO(release.get());
			}

		} catch (Exception e) {
			throw new ReleaseException("Could not fetch data");
		}
		return releaseDTO;
	}

	@Override
	public String delete(int id) throws ReleaseException {
		if (id <= 0) {
			LOGGER.error("InValid ID Could not fetch");
			throw new ReleaseException("Enter valid id");
		}

		if (!releaseRepository.findById(id).isPresent()) {
			LOGGER.error("No corresponding data found");
			throw new ReleaseException("No corresponding data found");

		}

		try {
			releaseRepository.deleteById(id);

		} catch (Exception e) {
			throw new ReleaseException("Could not delete");
		}
		return "Deleted corresponding data by provided id";
	}

	@Override
	public ReleaseDTO findByName(String name) throws ReleaseException {
		// TODO Auto-generated method stub

		if (name == null || name.trim() == null) {
			throw new ReleaseException("Release Name cannot be null");
		}

		Release release = releaseRepository.findByName(name)
				.orElseThrow(() -> new ReleaseException("Exception while fetching by name"));

		ReleaseDTO releaseDTO = convertToDTO.mapToReleaseDTO(release);
		return releaseDTO;

	}

}
