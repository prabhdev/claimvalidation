package com.patient.val;

/**
 * Microservice to validate and store patient information
 * 
 * @author Devanadha Prabhu
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PatientValidationSvcApp {		

	public static void main(String[] args) {
		SpringApplication.run(PatientValidationSvcApp.class, args);
	}

}
