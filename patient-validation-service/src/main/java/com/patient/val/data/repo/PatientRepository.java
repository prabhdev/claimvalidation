package com.patient.val.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.patient.val.domain.Patient;

public interface PatientRepository extends CrudRepository<Patient, String> {

	
}
