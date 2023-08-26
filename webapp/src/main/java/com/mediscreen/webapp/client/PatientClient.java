package com.mediscreen.webapp.client;

import com.mediscreen.webapp.model.Patient;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "details-service", url = "${patient-client-url}", fallback = PatientClientFallback.class)
public interface PatientClient {

    @RequestMapping(method = RequestMethod.GET, value = "/", produces = "application/json")
    Page<Patient> findAll(
            @RequestParam(value = "pageNumber") int pageNumber,
            @RequestParam(value = "itemPerPage", required = false) int itemPerPage);

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
    Patient findById(@PathVariable("id") Long id);

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @Headers("Content-Type: application/json")
    void createPatient(Patient patient);

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    @Headers("Content-Type: application/json")
    void save(Patient patient);

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteById(@PathVariable("id") Long id);
}
