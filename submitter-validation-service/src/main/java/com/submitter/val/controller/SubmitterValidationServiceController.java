package com.submitter.val.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.submitter.val.domain.Submitter;
import com.submitter.val.service.SubmitterValidationService;

/**
 * API to validate submitter enrollment
 * 
 * @author Devanadha Prabhu
 *
 */
@RestController
@RequestMapping("/api/submitter")
public class SubmitterValidationServiceController {

	@Autowired
	private SubmitterValidationService submitterInfoValidateService;

	/**
	 * API to perform submitter enrollment lookup
	 * 
	 * @param submitterInfo - Contains information required for lookup
	 * @return submitterInfo - Instance updated with lookup response
	 */
	@PreAuthorize("hasAnyAuthority('APIUSER','ADMIN)")
	@PostMapping("/validate")
	public ResponseEntity<Submitter> validateAndStoreSubmitterInfo(@RequestBody Submitter submitterInfo) {

		// Call service to validate submitter info
		submitterInfoValidateService.validateSubmitterEnrollment(submitterInfo);

		return ResponseEntity.ok(submitterInfo);

	}

}
