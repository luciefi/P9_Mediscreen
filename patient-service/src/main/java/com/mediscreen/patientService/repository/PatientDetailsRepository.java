package com.mediscreen.patientService.repository;

import com.mediscreen.patientService.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDetailsRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findAll(Pageable pageable);
}
