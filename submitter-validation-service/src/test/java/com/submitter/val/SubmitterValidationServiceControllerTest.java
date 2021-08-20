package com.submitter.val;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.submitter.val.domain.Submitter;

/**
 * Unit test cases for API which handle patient information validation
 * 
 * @author Devanadha Prabhu
 *
 */

@WebMvcTest(SubmitterValidationServiceControllerTest.class)
@ComponentScan(basePackages = "com.submitter.val")
public class SubmitterValidationServiceControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	Submitter RECORD_1 = new Submitter("147852", "34481", new ArrayList<String>());	

	/**
	 * Test to verify if submitter ID is enrolled
	 * 
	 * @throws Exception
	 */
	@Test
	public void validatePatientInfo_HappyPath() throws Exception {

		Submitter SUBMITTER_HAPPY_PATH = new Submitter("147852", "34481", new ArrayList<String>());	

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/submitter/validate")
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:mypassword".getBytes()))
				.accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(SUBMITTER_HAPPY_PATH));

		MvcResult result = mockMvc.perform(mockRequest).andExpect(status().isOk()).andReturn();		

		assertFalse(result.getResponse().getContentAsString().contains("Submitter is not enrolled for the service"), "Submitter should be enrolled");

	}	
	
	/**
	 * Test to verify if submitter ID is not enrolled
	 * 
	 * @throws Exception
	 */
	@Test
	public void validatePatientInfo_NotEnrolled() throws Exception {

		Submitter SUBMITTER_HAPPY_PATH = new Submitter("247852", "34481", new ArrayList<String>());	

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/submitter/validate")
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:mypassword".getBytes()))
				.accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(SUBMITTER_HAPPY_PATH));

		MvcResult result = mockMvc.perform(mockRequest).andExpect(status().isOk()).andReturn();		

		assertTrue(result.getResponse().getContentAsString().contains("Submitter is not enrolled for the service"), "Submitter should not be enrolled");

	}	

}