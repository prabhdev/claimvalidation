package com.patient.val.controller;

import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.patient.val.domain.Patient;
import com.patient.val.exception.NoDataFoundException;
import com.patient.val.exception.PatientNotFoundException;

@ControllerAdvice
public class PatientValidationControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(PatientNotFoundException.class)
	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> handlePatientNotFoundException(PatientNotFoundException ex, WebRequest request) {

		MultiValueMap<String, String> body = (MultiValueMap<String, String>) new HashMap();
		body.add("message", "Patient not found");

		return new ResponseEntity<Object>(new Patient(), body, HttpStatus.SC_NOT_FOUND);
	}

	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity handleNodataFoundException(NoDataFoundException ex, WebRequest request) {

		@SuppressWarnings("unchecked")
		MultiValueMap<String, String> body = (MultiValueMap<String, String>) new HashMap();
		body.add("message", "No patients not found");

		return new ResponseEntity<Object>(new Patient(), body, HttpStatus.SC_NOT_FOUND);
	}

}
