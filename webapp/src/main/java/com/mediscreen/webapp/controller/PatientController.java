package com.mediscreen.webapp.controller;

import com.mediscreen.webapp.model.Patient;
import com.mediscreen.webapp.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private static final int PATIENT_PER_PAGE = 3;
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
    public String getPatientList(Model model, @RequestParam(name = "page", required = false) Optional<Integer> pageNumber) {
        int currentPage = Math.max(pageNumber.orElse(1), 1);
        Page<Patient> patientPage = service.getAllPatientsPaginated(currentPage - 1, PATIENT_PER_PAGE);

        model.addAttribute("patientPage", patientPage);

        int totalPages = patientPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "patientList";
    }

    @GetMapping("/details/{id}")
    public String getDetailsPatientForm(@PathVariable("id") long id, Model model) {
        Patient patient = service.getPatient(id);
        model.addAttribute("patient", patient);
        return "patientDetails";
    }

    @GetMapping("/update/{id}")
    public String updatePatientForm(@PathVariable("id") long id, @RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer, Model model) {
        Patient patient = service.getPatient(id);
        model.addAttribute("patient", patient);
        String cancelUrl = referrer != null && referrer.contains("/patient/details/") ? "/patient/details/" + id : "/patient/list";
        model.addAttribute("cancelUrl", cancelUrl);
        return "updatePatient";
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
        Patient patient = service.getPatient(id);
        model.addAttribute("patient", patient);
        return "deletePatient";
    }

    @PostMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") long id) {
        service.deletePatient(id);
        logger.info("Patient with id: " + id + " deleted");
        return "redirect:/patient/list";
    }
}
