package com.excelr.bugtracking.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.event.InvocationEvent;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito.Then;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.excelr.bugtracking.dto.ApplicationDTO;
import com.excelr.bugtracking.dto.ApplicationRequest;
import com.excelr.bugtracking.entity.Application;
import com.excelr.bugtracking.exception.ApplicationException;
import com.excelr.bugtracking.mapper.ConvertToDTO;
import com.excelr.bugtracking.repository.ApplicationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceImplTest {

	@Mock
	private ApplicationRepository applicationRepository;

	@Mock
	private ConvertToDTO convertToDTO;

	@InjectMocks
	private ApplicationServiceImpl applicationServiceImpl;

	@Test
	public void testfindAll() {

		log.info("Testing Application Service class -find All method");

		Application application1 = new Application();
		application1.setId(1);
		application1.setName("BARD");
		application1.setOwner("John Doe");
		application1.setDescription("Bard Description");

		Application application2 = new Application();
		application2.setId(2);
		application2.setName("ChatGPT");
		application2.setOwner("Jane Smith");
		application2.setDescription("ChatGPT Application");

		ApplicationDTO appDTO1 = new ApplicationDTO();
		appDTO1.setId(1);
		appDTO1.setName("BARD");
		appDTO1.setOwner("John Doe");
		appDTO1.setDescription("Bard Description");

		ApplicationDTO appDTO2 = new ApplicationDTO();
		appDTO2.setId(2);
		appDTO2.setName("ChatGPT");
		appDTO2.setOwner("Jane Smith");
		appDTO2.setDescription("ChatGPT Application");

		when(applicationRepository.findAll()).thenReturn(List.of(application1, application2));
		when(convertToDTO.mapToApplicationDTO(application1)).thenReturn(appDTO1);
		when(convertToDTO.mapToApplicationDTO(application2)).thenReturn(appDTO2);

		List<ApplicationDTO> applicListDtos = applicationServiceImpl.findAll();
		assertNotNull(applicListDtos);
		assertEquals(2, applicListDtos.size());

		log.info("Testing individual objects in findall");
		ApplicationDTO app1 = applicListDtos.get(0);
		assertEquals(1, app1.getId());
		assertEquals("BARD", app1.getName());
		assertEquals("John Doe", app1.getOwner());
		assertEquals("Bard Description", app1.getDescription());

		ApplicationDTO app2 = applicListDtos.get(1);
		assertEquals(2, app2.getId());
		assertEquals("ChatGPT", app2.getName());
		assertEquals("Jane Smith", app2.getOwner());
		assertEquals("ChatGPT Application", app2.getDescription());

	}

	@Test
	public void testfindById() throws ApplicationException {

		log.info("Testing Application Service class -find By Id method");

		Application application1 = new Application();
		application1.setId(1);
		application1.setName("BARD");
		application1.setOwner("John Doe");
		application1.setDescription("Bard Description");

		ApplicationDTO appDTO1 = new ApplicationDTO();
		appDTO1.setId(1);
		appDTO1.setName("BARD");
		appDTO1.setOwner("John Doe");
		appDTO1.setDescription("Bard Description");

		when(applicationRepository.findById(1)).thenReturn(Optional.of(application1));
		when(applicationRepository.findById(3)).thenReturn(Optional.empty());
		when(convertToDTO.mapToApplicationDTO(application1)).thenReturn(appDTO1);

		ApplicationDTO app1 = applicationServiceImpl.findById(1);

		assertNotNull(app1);
		assertEquals(1, app1.getId());
		assertEquals("BARD", app1.getName());
		assertEquals("John Doe", app1.getOwner());
		assertEquals("Bard Description", app1.getDescription());

		assertThrows(ApplicationException.class, () -> {
			applicationServiceImpl.findById(3);
		});

	}

	@Test(expected = ApplicationException.class)
	public void testfindById_Notfound() throws ApplicationException {
		log.info("Testing Application Service class -findById_Not found method");
		when(applicationRepository.findById(999)).thenReturn(Optional.empty());

		applicationServiceImpl.findById(999);
	}

	@Test
	public void testfindByName() throws ApplicationException {

		log.info("Testing Application Service class -find By Name method");

		Application application1 = new Application();
		application1.setId(1);
		application1.setName("BARD");
		application1.setOwner("John Doe");
		application1.setDescription("Bard Description");

		ApplicationDTO appDTO1 = new ApplicationDTO();
		appDTO1.setId(1);
		appDTO1.setName("BARD");
		appDTO1.setOwner("John Doe");
		appDTO1.setDescription("Bard Description");

		when(applicationRepository.findByName("BARD")).thenReturn(Optional.of(application1));
		when(applicationRepository.findByName("Not Found")).thenReturn(Optional.empty());
		when(convertToDTO.mapToApplicationDTO(application1)).thenReturn(appDTO1);

		ApplicationDTO app1 = applicationServiceImpl.findByName("BARD");

		assertNotNull(app1);
		assertEquals(1, app1.getId());
		assertEquals("BARD", app1.getName());
		assertEquals("John Doe", app1.getOwner());
		assertEquals("Bard Description", app1.getDescription());

		assertThrows(ApplicationException.class, () -> {
			applicationServiceImpl.findByName("Not Found");
		});

		assertThrows(ApplicationException.class, () -> {
			applicationServiceImpl.findByName("");
		});
	}

	@Test
	public void testfindByDescription() throws ApplicationException {

		log.info("Testing Application Service class -find By Description method");

		Application application1 = new Application();
		application1.setId(1);
		application1.setName("BARD");
		application1.setOwner("John Doe");
		application1.setDescription("Bard Description");

		ApplicationDTO appDTO1 = new ApplicationDTO();
		appDTO1.setId(1);
		appDTO1.setName("BARD");
		appDTO1.setOwner("John Doe");
		appDTO1.setDescription("Bard Description");

		when(applicationRepository.findBydescription("Bard Description")).thenReturn(Optional.of(application1));
		when(applicationRepository.findBydescription("Not Found Description")).thenReturn(Optional.empty());
		when(convertToDTO.mapToApplicationDTO(application1)).thenReturn(appDTO1);

		ApplicationDTO app1 = applicationServiceImpl.findByDescription("Bard Description");

		assertNotNull(app1);
		assertEquals(1, app1.getId());
		assertEquals("BARD", app1.getName());
		assertEquals("John Doe", app1.getOwner());
		assertEquals("Bard Description", app1.getDescription());

		assertThrows(ApplicationException.class, () -> {
			applicationServiceImpl.findByDescription("Not Found Description");
		});

		assertThrows(ApplicationException.class, () -> {
			applicationServiceImpl.findByDescription("");
		});
	}

	@Test
	public void testfindByOwner() throws ApplicationException {

		log.info("Testing Application Service class -find By Owner method");

		Application application1 = new Application();
		application1.setId(1);
		application1.setName("BARD");
		application1.setOwner("John Doe");
		application1.setDescription("Bard Description");

		ApplicationDTO appDTO1 = new ApplicationDTO();
		appDTO1.setId(1);
		appDTO1.setName("BARD");
		appDTO1.setOwner("John Doe");
		appDTO1.setDescription("Bard Description");

		when(applicationRepository.findByOwner("John Doe")).thenReturn(Optional.of(application1));
		when(applicationRepository.findByOwner("No Owner")).thenReturn(Optional.empty());
		when(convertToDTO.mapToApplicationDTO(application1)).thenReturn(appDTO1);

		ApplicationDTO app1 = applicationServiceImpl.findByOwner("John Doe");

		assertNotNull(app1);
		assertEquals(1, app1.getId());
		assertEquals("BARD", app1.getName());
		assertEquals("John Doe", app1.getOwner());
		assertEquals("Bard Description", app1.getDescription());

		assertThrows(ApplicationException.class, () -> {
			applicationServiceImpl.findByOwner("No Owner");
		});

		assertThrows(ApplicationException.class, () -> {
			applicationServiceImpl.findByOwner("");
		});
	}

	@Test
	public void testsave() throws ApplicationException {

		log.info("Testing Application Service class -Save method");

		ApplicationRequest appRequest = new ApplicationRequest();
		appRequest.setName("BARD");
		appRequest.setOwner("John Doe");
		appRequest.setDescription("Bard Description");

		Application application1 = new Application();
		application1.setId(1);
		application1.setName("BARD");
		application1.setOwner("John Doe");
		application1.setDescription("Bard Description");

		ApplicationDTO appDTO1 = new ApplicationDTO();
		appDTO1.setId(1);
		appDTO1.setName("BARD");
		appDTO1.setOwner("John Doe");
		appDTO1.setDescription("Bard Description");

		when(applicationRepository.save(any(Application.class))).thenAnswer(invocation -> {
			Application savedApp = invocation.getArgument(0);
			savedApp.setId(1);
			return savedApp;
		});

		when(convertToDTO.mapToApplicationDTO(application1)).thenReturn(appDTO1);

		ApplicationDTO app1 = applicationServiceImpl.save(appRequest);

		assertNotNull(app1);
		assertEquals(1, 1);
		assertEquals("BARD", app1.getName());
		assertEquals("John Doe", app1.getOwner());
		assertEquals("Bard Description", app1.getDescription());

		ApplicationRequest appBadRequest = new ApplicationRequest();
		appRequest.setName("");
		appRequest.setOwner("John Doe");
		appRequest.setDescription("Bard Description");

		ApplicationRequest application = new ApplicationRequest();

		assertThrows(ApplicationException.class, () -> {
			applicationServiceImpl.save(appBadRequest);
		});

		assertThrows(ApplicationException.class, () -> {
			applicationServiceImpl.save(application);
		});

	}

	@Test
	public void testdelete() throws ApplicationException {

		log.info("Testing Application Service class -Delete method");

		String result = applicationServiceImpl.delete(1);
		assertEquals("Successfully Deleted Application", result);

		verify(applicationRepository).deleteById(1);

		assertThrows(ApplicationException.class, () -> {
			applicationServiceImpl.delete(-1);
		});

	}

}
