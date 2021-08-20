package com.patient.val.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.patient.val.domain.Patient;
import com.patient.val.domain.Response;
import com.patient.val.service.PatientValidationService;
import com.patient.val.service.StorePatientService;

/**
 * APIs to handle patient information
 * 
 * @author Devanadha Prabhu
 *
 */

@RequestMapping("/api/patient")
@RestController
public class PatientValidationSvcController {

	@Autowired
	private StorePatientService storeService;

	@Autowired
	private PatientValidationService patInfoValidateService;

	/**
	 * Validates patient information and returns a response containing error codes
	 * 
	 * @param patient - Contains patient info to be validated
	 * @return response - Validation codes to be interpreted by the caller
	 */
	@PreAuthorize("hasAnyAuthority('ROLE_APIUSER','ROLE_ADMIN)")
	@PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> validatePatientInfo(@RequestBody @NotNull Patient patient) {

		Response response = patInfoValidateService.validatePatientInfo(patient);

		return ResponseEntity.ok(response);

	}

	/**
	 * API to store patient information in a database
	 * 
	 * @param patient - Patient information holder
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('ROLE_APIUSER','ROLE_ADMIN)")
	@PostMapping(value = "/store", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> storePatientInfo(@RequestBody Patient patient) {

		// Call store service to store the request
		storeService.addPatient(patient);

		return ResponseEntity.accepted().build();

	}

	/**
	 * API to return patient details matching a specific patient ID
	 * 
	 * @param id - Patient ID for which the details are requested
	 * @return - Patient - Patient instance matching the ID
	 */
	@PreAuthorize("hasAnyAuthority('ROLE_APIUSER','ROLE_ADMIN)")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Patient> getPatientInfo(@PathVariable("id") String id) {

		// Call service to obtain patient information
		Patient patient = storeService.getPatient(id);

		return ResponseEntity.ok(patient);

	}

}