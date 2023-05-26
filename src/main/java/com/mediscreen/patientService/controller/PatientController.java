package com.mediscreen.patientService.controller;

import com.mediscreen.patientService.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class PatientController {

    @Autowired
    private IPatientService service;

    Logger logger = LoggerFactory.getLogger(PatientController.class);
}
