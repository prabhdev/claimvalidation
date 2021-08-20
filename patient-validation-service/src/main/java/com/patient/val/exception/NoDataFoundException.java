package com.patient.val.exception;

public class NoDataFoundException extends RuntimeException {

	/**
	 * Runtime exception definition for No Patient Data found
	 */
	private static final long serialVersionUID = 1L;

	public NoDataFoundException() {

		super("No data found");
	}

}
