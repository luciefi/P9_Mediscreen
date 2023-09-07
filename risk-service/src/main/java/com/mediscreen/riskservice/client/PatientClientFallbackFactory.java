package com.mediscreen.riskservice.client;

import com.mediscreen.riskservice.exception.PatientClientException;
import com.mediscreen.riskservice.exception.PatientNotFoundException;
import com.mediscreen.riskservice.model.Patient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class PatientClientFallbackFactory implements FallbackFactory<PatientClient> {

    Logger logger = LoggerFactory.getLogger(PatientClientFallbackFactory.class);

    @Override
    public PatientClient create(Throwable cause) {
        return new PatientClient() {

            @Override
            public Patient findById(Long id) {
                String message = "Error wile retrieving patient with id: " + id;
                logger.error(message);

                if (cause instanceof FeignException.NotFound) {
                    throw new PatientNotFoundException("Patient with id: " + id + " could not be found.");
                }

                throw new PatientClientException(message);
            }
        };
    }
}
