package com.mediscreen.webapp.controller;

import com.mediscreen.webapp.exception.PatientNotFoundException;
import com.mediscreen.webapp.model.Patient;
import com.mediscreen.webapp.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
public class NotesController {
    Logger logger = LoggerFactory.getLogger(NotesController.class);

    @Autowired
    private IPatientService service;

    @GetMapping("/{id}")
    public String getPatientNotes(@PathVariable("id") long id, Model model) {
        try {
            Patient patient = service.getPatient(id); // TODO récupérer nom prénom depuis le note service
            model.addAttribute("patient", patient);
            return "patientNotes";
        } catch (PatientNotFoundException e) {
            logger.info("Cannot get patient details : " + e.getMessage());
            return "redirect:/patient/list";
        }
    }


}
