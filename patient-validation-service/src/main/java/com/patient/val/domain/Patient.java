package com.patient.val.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO to hold patient information
 * 
 * @author Devanadha Prabhu
 *
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    
	@Id
	private String patientID;
	private String patientFirstName;
	private String patientLastName;
	private String patientDOB;
	private String correlationID;	

}