package com.patient.val.service;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.patient.val.domain.Patient;
import com.patient.val.domain.Response;

/**
 * Service class to validate patient information
 * 
 * @author Devanadha Prabhu
 *
 */

@Service
public class PatientValidationService {

	private static final Logger logger = LogManager.getLogger(PatientValidationService.class);

	/**
	 * Validates patient ID to check if it is numeric and 5 digits long
	 * 
	 * @param patient - Patient info to be validated
	 * @return response - Contains validation error codes. 000 for no errors.
	 */
	public Response validatePatientInfo(Patient patient) {

		logger.info("Patient validation start: " + patient.getPatientID());
		Response response = new Response();

		if (StringUtils.isEmpty(patient.getPatientID())) {
			response.getValidationCodes().add("PAT_ERR_01");
		} else if (!StringUtils.isNumeric(patient.getPatientID())) {
			response.getValidationCodes().add("PAT_ERR_02");
		} else if (patient.getPatientID().length() != 5) {
			response.getValidationCodes().add("PAT_ERR_03");
		} else {
			response.getValidationCodes().add("000");
		}

		logger.info("Patient validation end: " + patient.getPatientID());

		return response;

	}

}
