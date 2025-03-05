package com.excelr.bugtracking.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.excelr.bugtracking.entity.Application;

@DataJpaTest
public class ApplicationRepositoryTest {

	// Test to check
	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testfindByName() {

		Application application = new Application();
		application.setName("Image Edid Pro 1");
		application.setDescription("Professional photo editing software with advance photo editing feature");
		application.setOwner("Rakshitha");
		entityManager.persist(application);

		Optional<Application> foundApplication = applicationRepository.findByName("Image Edid Pro 1");

		assertTrue(foundApplication.isPresent());
		assertEquals("Image Edid Pro 1", foundApplication.get().getName());
		assertEquals("Professional photo editing software with advance photo editing feature",
				foundApplication.get().getDescription());
		assertEquals("Rakshitha", foundApplication.get().getOwner());

	}

	@Test
	public void testfindByOwner() {

		Application application = new Application();
		application.setName("Image Edid Pro 1");
		application.setDescription("Professional photo editing software with advance photo editing feature");
		application.setOwner("Rakshitha");
		entityManager.persist(application);

		Optional<Application> foundApplication = applicationRepository.findByOwner("Rakshitha");

		assertTrue(foundApplication.isPresent());
		assertEquals("Image Edid Pro 1", foundApplication.get().getName());
		assertEquals("Professional photo editing software with advance photo editing feature",
				foundApplication.get().getDescription());
		assertEquals("Rakshitha", foundApplication.get().getOwner());

	}

	@Test
	public void testfindBydescription() {

		Application application = new Application();
		application.setName("Image Edid Pro 1");
		application.setDescription("Professional photo editing software with advance photo editing feature");
		application.setOwner("Rakshitha");
		entityManager.persist(application);

		Optional<Application> foundApplication = applicationRepository
				.findBydescription("Professional photo editing software with advance photo editing feature");

		assertTrue(foundApplication.isPresent());
		assertEquals("Image Edid Pro 1", foundApplication.get().getName());
		assertEquals("Professional photo editing software with advance photo editing feature",
				foundApplication.get().getDescription());
		assertEquals("Rakshitha", foundApplication.get().getOwner());

	}

}
