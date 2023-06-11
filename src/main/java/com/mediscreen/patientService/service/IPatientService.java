package com.mediscreen.patientService.service;

import com.mediscreen.patientService.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPatientService {
    void saveNewPatient(Patient patient);

    Page<Patient> getAllPatientsPaginated(Pageable pageable);

    Patient getPatient(long l);

    void updatePatient(Patient patient);

    void deletePatient(long id);
}
