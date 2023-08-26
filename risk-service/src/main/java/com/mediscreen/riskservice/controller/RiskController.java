package com.mediscreen.riskservice.controller;

import com.mediscreen.riskservice.service.IRiskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/risk")
@Validated
public class RiskController {

    @Autowired
    private IRiskService service;

    Logger logger = LoggerFactory.getLogger(RiskController.class);

    @GetMapping("/{id}")
    public String getPatientById(@PathVariable("id") @Positive final Long id) {
        return service.getPatientRisk(id).toString();
    }

    @ExceptionHandler({ ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintException(Exception e) {
        logger.error("Invalid parameter: {}", e.getMessage());
        return new ResponseEntity<>("Invalid parameter: {}" + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
