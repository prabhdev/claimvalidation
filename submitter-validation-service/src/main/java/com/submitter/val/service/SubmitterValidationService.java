package com.submitter.val.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.submitter.val.domain.Submitter;

@Service
public class SubmitterValidationService {

	private static final Logger logger = LogManager.getLogger(SubmitterValidationService.class);

	@Autowired
	private RestTemplate restTemplate; // TODO To be used for external HIPAA validation

	@Autowired
	private CacheManager cacheManager;

	/**
	 * Submitter ID is validated against a cache instance. Request is accepted if
	 * the lookup is successful
	 * 
	 * @param submitter - Object required for enrollment lookup
	 */
	public void validateSubmitterEnrollment(Submitter submitter) {

		logger.info("Submitter validation start: " + submitter.getCorrelationID());

		if (!(cacheManager.getCache("submitterID").get(submitter.getSubmitterID()) != null)) {
			submitter.getErrorCode().add("Submitter is not enrolled for the service");
		}

		logger.info("Submitter validation end: " + submitter.getCorrelationID());

	}

}
