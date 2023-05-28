package com.mediscreen.patientService.controller;

import com.mediscreen.patientService.exception.PatientNotFoundException;
import com.mediscreen.patientService.model.Patient;
import com.mediscreen.patientService.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private IPatientService service;

    Logger logger = LoggerFactory.getLogger(PatientController.class);

    @GetMapping("/add")
    public String createPatient(Model model) {
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        return "addPatient";
    }

    @PostMapping("/add")
    public String saveNewPatient(@Valid Patient patient, BindingResult result) {
        if (result.hasErrors()) {
            logger.info("Cannot add patient : invalid form");
            return "addPatient";
        }
        service.saveNewPatient(patient);
        logger.info("New patient added");
        return "redirect:/patient/list";
    }

    @GetMapping("/list")
    public String getPatientList(Model model) {
        List<Patient> patients = service.getAllPatients();
        model.addAttribute("patients", patients);
        return "patientList";
    }

    @GetMapping("/update/{id}")
    public String updatePatientForm(@PathVariable("id") long id, Model model) {
        try {
            Patient patient = service.getPatient(id);
            model.addAttribute("patient", patient);
            return "updatePatient";
        } catch (PatientNotFoundException e) {
            logger.info("Cannot update patient : " + e.getMessage());
            return "redirect:/patient/list";
        }
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") long id, @Valid Patient patient, BindingResult result) {
        if (result.hasErrors()) {
            logger.info("Cannot update patient : invalid form");
            return "updatePatient";
        }
        service.updatePatient(patient);
        logger.info("Patient with id: " + id + " updated");
        return "redirect:/patient/list";
    }

    @GetMapping("/delete/{id}")
    public String deletePatientForm(@PathVariable("id") long id, Model model) {
        try {
            Patient patient = service.getPatient(id);
            model.addAttribute("patient", patient);
            return "deletePatient";
        } catch (PatientNotFoundException e) {
            logger.info("Cannot delete patient : " + e.getMessage());
            return "redirect:/patient/list";
        }
    }

    @PostMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") long id) {
        service.deletePatient(id);
        logger.info("Patient with id: " + id + " deleted");
        return "redirect:/patient/list";
    }



}
