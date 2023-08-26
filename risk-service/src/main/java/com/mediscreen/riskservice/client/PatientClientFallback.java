package com.mediscreen.riskservice.client;

import com.mediscreen.riskservice.model.Patient;
import com.mediscreen.riskservice.exception.PatientNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PatientClientFallback implements PatientClient {

    Logger logger = LoggerFactory.getLogger(PatientClientFallback.class);

    @Override
    public Patient findById(Long id) {
       throwPatientNotFound(id);
        return null;
    }

    private void throwPatientNotFound(long id) {
        logger.info("Patient with id: " + id + " could not be found.");
        throw new PatientNotFoundException();
    }
}
