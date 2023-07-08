package com.mediscreen.patientService.service;

import com.mediscreen.patientService.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPatientService {
    void saveNewPatient(Patient patient);

    Page<Patient> getAllPatientsPaginated(int pageNumber, int itemPerPage);

    Patient getPatient(long l);

    void updatePatient(Patient patient);

    void deletePatient(long id);
}
