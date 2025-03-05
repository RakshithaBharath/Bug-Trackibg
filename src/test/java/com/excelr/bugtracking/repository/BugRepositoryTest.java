package com.excelr.bugtracking.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.excelr.bugtracking.entity.Application;
import com.excelr.bugtracking.entity.Bug;
import com.excelr.bugtracking.entity.Release;

@DataJpaTest
public class BugRepositoryTest {

	@Autowired
	private BugRepository bugRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testgetByApplicationId() {

		Application app = new Application();
		app.setName("Bug Tracker");
		app.setDescription("Application for tracking bugs");
		app.setOwner("John Doe");
		app.setCreatedOn(new Date());
		entityManager.persist(app);

		// Given: Create and persist a Release entity
		Release release = new Release();
		release.setName("Version 1.0");
		release.setDescription("First release");
		release.setReleaseDate(new Date());
		release.setStatus("Active");
		entityManager.persist(release);

		// Given: Create and persist a Bug entity
		Bug bug = new Bug();
		bug.setType("Critical");
		bug.setApplicationImpacted("Frontend");
		bug.setAssignedTo("Alice");
		bug.setCreatedOn(new Date());
		bug.setCreatedBy("Admin");
		bug.setStatus("Open");
		bug.setDescription("Login page crashes on submit");
		bug.setApplication(app);
		bug.setRelease(release);
		entityManager.persist(bug);

		List<Bug> bugs = bugRepository.getByApplicationId(app.getId());
		assertFalse(bugs.isEmpty());
		assertEquals(1, bugs.size());

		Bug retrievedBug = bugs.get(0);
		assertEquals("Critical", retrievedBug.getType(), "Bug type should match");
		assertEquals("Frontend", retrievedBug.getApplicationImpacted(), "Impacted area should match");
		assertEquals("Alice", retrievedBug.getAssignedTo(), "Assigned person should match");
		assertEquals("Admin", retrievedBug.getCreatedBy(), "Creator should match");
		assertEquals("Open", retrievedBug.getStatus(), "Bug status should be Open");
		assertEquals("Login page crashes on submit", retrievedBug.getDescription(), "Bug description should match");
		assertEquals("Bug Tracker", retrievedBug.getApplication().getName());
		assertEquals("Application for tracking bugs", retrievedBug.getApplication().getDescription());
		assertEquals("John Doe", retrievedBug.getApplication().getOwner());
		assertEquals("Version 1.0", retrievedBug.getRelease().getName());
		assertEquals("First release", retrievedBug.getRelease().getDescription());
		assertEquals("Active", retrievedBug.getRelease().getStatus());

	}
	
	@Test
	public void testgetByReleaseId() {

		Application app = new Application();
		app.setName("Bug Tracker");
		app.setDescription("Application for tracking bugs");
		app.setOwner("John Doe");
		app.setCreatedOn(new Date());
		entityManager.persist(app);

		// Given: Create and persist a Release entity
		Release release = new Release();
		release.setName("Version 1.0");
		release.setDescription("First release");
		release.setReleaseDate(new Date());
		release.setStatus("Active");
		entityManager.persist(release);

		// Given: Create and persist a Bug entity
		Bug bug = new Bug();
		bug.setType("Critical");
		bug.setApplicationImpacted("Frontend");
		bug.setAssignedTo("Alice");
		bug.setCreatedOn(new Date());
		bug.setCreatedBy("Admin");
		bug.setStatus("Open");
		bug.setDescription("Login page crashes on submit");
		bug.setApplication(app);
		bug.setRelease(release);
		entityManager.persist(bug);

		List<Bug> bugs = bugRepository.getByReleaseId(release.getId());
		assertFalse(bugs.isEmpty());
		assertEquals(1, bugs.size());

		Bug retrievedBug = bugs.get(0);
		assertEquals("Critical", retrievedBug.getType(), "Bug type should match");
		assertEquals("Frontend", retrievedBug.getApplicationImpacted(), "Impacted area should match");
		assertEquals("Alice", retrievedBug.getAssignedTo(), "Assigned person should match");
		assertEquals("Admin", retrievedBug.getCreatedBy(), "Creator should match");
		assertEquals("Open", retrievedBug.getStatus(), "Bug status should be Open");
		assertEquals("Login page crashes on submit", retrievedBug.getDescription(), "Bug description should match");
		assertEquals("Bug Tracker", retrievedBug.getApplication().getName());
		assertEquals("Application for tracking bugs", retrievedBug.getApplication().getDescription());
		assertEquals("John Doe", retrievedBug.getApplication().getOwner());
		assertEquals("Version 1.0", retrievedBug.getRelease().getName());
		assertEquals("First release", retrievedBug.getRelease().getDescription());
		assertEquals("Active", retrievedBug.getRelease().getStatus());

	}
}
