package com.mediscreen.riskservice.client;

import com.mediscreen.riskservice.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "details-service", url = "${patient-client-url}", fallbackFactory = PatientClientFallbackFactory.class)
public interface PatientClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
    Patient findById(@PathVariable("id") Long id);

}
