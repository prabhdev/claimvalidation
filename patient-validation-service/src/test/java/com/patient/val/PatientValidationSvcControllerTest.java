package com.patient.val;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient.val.data.repo.PatientRepository;
import com.patient.val.domain.Patient;

/**
 * Unit test cases for API which handle patient information validation
 * 
 * @author Devanadha Prabhu
 *
 */

@WebMvcTest(PatientValidationSvcControllerTest.class)
@ComponentScan(basePackages = "com.patient.val")
public class PatientValidationSvcControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	PatientRepository patientepository;

	Patient RECORD_1 = new Patient("100", "John", "Greg", "05/08/2010", "25633");
	Patient RECORD_2 = new Patient("101", "Ben", "Kris", "06/09/1990", "35633");
	Patient RECORD_3 = new Patient("102", "Jack", "Sparrow", "07/10/1980", "45633");

	/**
	 * Test to verify get patient by ID API
	 * 
	 * @throws Exception
	 */
	// @Test
	public void getPatientInfo_success() throws Exception {

		Mockito.when(patientepository.findById(RECORD_1.getPatientID())).thenReturn(Optional.of(RECORD_1));

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/patient/100").header(HttpHeaders.AUTHORIZATION,
						"Basic " + Base64Utils.encodeToString("admin:mypassword".getBytes())))
				.andExpect(status().isOk()).andReturn();

		ObjectMapper objectMapper = new ObjectMapper();
		Patient resultObject = objectMapper.readValue(result.getResponse().getContentAsString(), Patient.class);
		assertTrue(resultObject.getPatientFirstName().equals("John"), "Patient first name is not as expected");
		assertTrue(resultObject.getPatientLastName().equals("Greg"), "Patient last name is not as expected");
		assertTrue(resultObject.getPatientDOB().equals("05/08/2010"), "Patient DOB is not as expected");

	}

	/**
	 * Test to verify patient info validation returns PAT_ERR_01 for a scenario
	 * where patient ID is empty
	 * 
	 * @throws Exception
	 */
	@Test
	public void validatePatientInfo_HappyPath() throws Exception {

		Patient RECORD_PAT_HAPPY_PATH = new Patient("19851", "John", "Greg", "05/08/2010", "25633");

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/patient/validate")
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:mypassword".getBytes()))
				.accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(RECORD_PAT_HAPPY_PATH));

		MvcResult result = mockMvc.perform(mockRequest).andExpect(status().isOk()).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("000"), "Validation codes is other than 000");

	}

	/**
	 * Test to verify if 400 code is received when body is not sent
	 * 
	 * @throws Exception
	 */
	@Test
	public void validatePatientInfo_PAT_ERR_00() throws Exception {

		Patient RECORD_PAT_ERR_00 = null;

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/patient/validate")
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:mypassword".getBytes()))
				.accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(RECORD_PAT_ERR_00));

		mockMvc.perform(mockRequest).andExpect(status().is(400)).andReturn();

	}

	/**
	 * Test to verify patient info validation returns PAT_ERR_01 for a scenario
	 * where patient ID is empty
	 * 
	 * @throws Exception
	 */
	@Test
	public void validatePatientInfo_PAT_ERR_01() throws Exception {

		Patient RECORD_PAT_ERR_01 = new Patient("", "John", "Greg", "05/08/2010", "25633");

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/patient/validate")
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:mypassword".getBytes()))
				.accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(RECORD_PAT_ERR_01));

		MvcResult result = mockMvc.perform(mockRequest).andExpect(status().isOk()).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("PAT_ERR_01"),
				"Error PAT_ERR_01 was not received");

	}

	/**
	 * Test to verify patient info validation returns PAT_ERR_02 for a scenario
	 * where patient ID is not numeric
	 * 
	 * @throws Exception
	 */
	@Test
	public void validatePatientInfo_PAT_ERR_02() throws Exception {

		Patient RECORD_PAT_ERR_02 = new Patient("100A2", "John", "Greg", "05/08/2010", "25633");

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/patient/validate")
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:mypassword".getBytes()))
				.accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(RECORD_PAT_ERR_02));

		MvcResult result = mockMvc.perform(mockRequest).andExpect(status().isOk()).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("PAT_ERR_02"),
				"Error PAT_ERR_02 was not received");

	}

	/**
	 * Test to verify patient info validation returns PAT_ERR_03 for a scenario
	 * where patient ID is > 5 digits
	 * 
	 * @throws Exception
	 */
	@Test
	public void validatePatientInfo_PAT_ERR_03() throws Exception {

		Mockito.when(patientepository.findById(RECORD_1.getPatientID())).thenReturn(Optional.of(RECORD_1));

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/patient/validate")
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:mypassword".getBytes()))
				.accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(RECORD_1));

		MvcResult result = mockMvc.perform(mockRequest).andExpect(status().isOk()).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("PAT_ERR_03"),
				"Error PAT_ERR_03 was not received");

	}

}