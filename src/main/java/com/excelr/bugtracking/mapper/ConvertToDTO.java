package com.excelr.bugtracking.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.excelr.bugtracking.dto.ApplicationDTO;
import com.excelr.bugtracking.dto.BugDTO;
import com.excelr.bugtracking.dto.ReleaseDTO;
import com.excelr.bugtracking.entity.Application;
import com.excelr.bugtracking.entity.Bug;
import com.excelr.bugtracking.entity.Release;

@Component
public class ConvertToDTO {

	public ReleaseDTO mapToReleaseDTO(Release release) {
		return new ReleaseDTO(release.getId(), release.getName(), release.getReleaseDate(), release.getStatus(),
				release.getComments(), release.getDescription());
	}

	public ApplicationDTO mapToApplicationDTO(Application application) {
		return new ApplicationDTO(application.getId(), application.getName(), application.getCreatedOn(),
				application.getDescription(), application.getOwner());
	}

	public BugDTO mapToBugDTO(Bug bug) {
		return new BugDTO(bug.getId(), bug.getType(), bug.getApplicationImpacted(), bug.getAssignedTo(),
				bug.getCreatedOn(), bug.getCreatedBy(), bug.getStatus(), bug.getDescription(),
				bug.getApplication().getId(), bug.getRelease().getId());

	}

}
