package com.patient.val.exception;

import lombok.Getter;

@Getter
public class PatientNotFoundException extends RuntimeException {

	/**
	 * Runtime exception definition for PatientID not found
	 */
	private static final long serialVersionUID = 1L;

	private final String id;

	public PatientNotFoundException(String id) {

		super(String.format("Patient with ID: %s not found", id));
		this.id = id;
	}

}
