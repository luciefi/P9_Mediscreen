package com.mediscreen.patientService.controller;

import com.mediscreen.patientService.exception.NotFoundException;
import com.mediscreen.patientService.model.Patient;
import com.mediscreen.patientService.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/patientDetails")
@Validated
public class PatientDetailsController {

    @Autowired
    private IPatientService service;

    Logger logger = LoggerFactory.getLogger(PatientDetailsController.class);

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable("id") final Long id) {
        return service.getPatient(id);
    }


    @GetMapping
    public Page<Patient> getPaginatedPatients(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) @Min(value = 0, message = "Page index must not be less than zero") Integer pageNumber,
            @RequestParam(value = "itemPerPage", defaultValue = "10", required = false) @Min(value = 1, message = "Page size must not be less than one") Integer itemPerPage) {
        return service.getAllPatientsPaginated(pageNumber, itemPerPage);
    }

    @PostMapping
    public void addPatient(@Valid @RequestBody Patient patient) {
        service.saveNewPatient(patient);
        logger.info("New patient added");
    }

    @PutMapping
    public void updateMedicalRecord(@Valid @RequestBody Patient patient) {
        service.updatePatient(patient);
        logger.info("Patient with id: " + patient.getId() + " updated");
    }

    @DeleteMapping("/{id}")
    public void deleteMedicalRecord(@PathVariable("id") final Long id) {
        service.deletePatient(id);
        logger.info("Patient with id: " + id + " deleted");
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        logger.error("Not found exception: {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintException(Exception e) {
        logger.error("Invalid parameter: {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
