package com.mediscreen.patientService.service;

import com.mediscreen.patientService.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientRepository repository;
}
