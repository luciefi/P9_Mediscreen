package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.Patient;
import org.springframework.data.domain.Page;

public interface IPatientService {
    void saveNewPatient(Patient patient);

    Page<Patient> getAllPatientsPaginated(int pageNumber, int itemPerPage);

    Patient getPatient(long l);

    void updatePatient(Patient patient);

    void deletePatient(long id);
}
