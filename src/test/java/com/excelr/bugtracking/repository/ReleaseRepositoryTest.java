package com.excelr.bugtracking.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.excelr.bugtracking.entity.Release;

@DataJpaTest
public class ReleaseRepositoryTest {

	@Autowired
	private ReleaseRepository releaseRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void testfindByName() {

		Release release = new Release();
		release.setName("Release 1.0");
		release.setDescription("DB upgrade");
		release.setStatus("IN PROGRESS");
		release.setComments("Upgrade all DB which falls are being deprecated");
		testEntityManager.persist(release);

		Optional<Release> fetchedRelease = releaseRepository.findByName("Release 1.0");
		assertTrue(fetchedRelease.isPresent());
		assertEquals("Release 1.0", fetchedRelease.get().getName());
		assertEquals("DB upgrade", fetchedRelease.get().getDescription());
		assertEquals("IN PROGRESS", fetchedRelease.get().getStatus());
		assertEquals("Upgrade all DB which falls are being deprecated", fetchedRelease.get().getComments());

	}

}
