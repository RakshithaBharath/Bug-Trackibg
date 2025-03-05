package com.excelr.bugtracking.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.springframework.http.MediaType;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.excelr.bugtracking.dto.ApplicationDTO;
import com.excelr.bugtracking.dto.ApplicationRequest;
import com.excelr.bugtracking.service.IApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(ApplicationController.class)
@AutoConfigureMockMvc
public class ApplicationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IApplicationService applicationService;

	@Autowired
	private WebApplicationContext webapplicationContext;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webapplicationContext).build();

	}

	@Test
	public void testGetApplications() throws Exception {
		log.info("Inside Test - get all application");

		List<ApplicationDTO> applicationDTOs = new ArrayList<ApplicationDTO>();

		ApplicationDTO applicationDTO = new ApplicationDTO();
		applicationDTO.setId(1);
		applicationDTO.setName("MS office");

		applicationDTOs.add(applicationDTO);

		when(applicationService.findAll()).thenReturn(applicationDTOs);

		mockMvc.perform(get("/api/v1/applications")).andExpect(status().isOk());

		when(applicationService.findAll()).thenReturn(null);

		mockMvc.perform(get("/api/v1/applications")).andExpect(status().isNotFound());

	}

	@Test
	public void testUpdateApplication() throws Exception {

		ApplicationRequest applicationRequest = new ApplicationRequest();
		applicationRequest.setId(1);
		applicationRequest.setName("MS office");

		ApplicationDTO applicationDTO = new ApplicationDTO();
		applicationDTO.setId(applicationRequest.getId());
		applicationDTO.setName(applicationRequest.getName());

		when(applicationService.save(any(ApplicationRequest.class))).thenReturn(applicationDTO);

		mockMvc.perform(put("/api/v1/applications").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(applicationDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(applicationRequest.getId())) // Validate response JSON
				.andExpect(jsonPath("$.name").value(applicationRequest.getName()));

	}

	@Test
	public void testDelete() throws Exception {
		log.info("Inside Test - delete application");

		int id = 1;

		when(applicationService.delete(id)).thenReturn("Successfully Deleted Application");

		mockMvc.perform(delete("/api/v1/applications").param("id", String.valueOf(1))).andExpect(status().isOk());
	}

	@Test
	public void testGetByName() throws Exception {
		log.info("Inside Test - delete application");

		String name = "Test Application";
		ApplicationDTO applicationDTO = new ApplicationDTO();

		when(applicationService.findByName(name)).thenReturn(applicationDTO);

		mockMvc.perform(get("/api/v1/applications/findByName/Test Application")).andExpect(status().isOk());
	}

}
