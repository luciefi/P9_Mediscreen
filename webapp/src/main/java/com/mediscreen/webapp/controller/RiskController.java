package com.mediscreen.webapp.controller;

import com.mediscreen.webapp.exception.PatientClientException;
import com.mediscreen.webapp.exception.RiskClientException;
import com.mediscreen.webapp.model.Patient;
import com.mediscreen.webapp.model.RiskLevel;
import com.mediscreen.webapp.service.IPatientService;
import com.mediscreen.webapp.service.IRiskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/risk")
public class RiskController {

    @Autowired
    private IRiskService service;

    @Autowired
    private IPatientService patientService;

    Logger logger = LoggerFactory.getLogger(RiskController.class);

    @GetMapping("/{id}")
    public String getRisk(@PathVariable("id") long id, Model model) {
        try {
            Patient patient = patientService.getPatient(id);
            model.addAttribute("patient", patient);
            RiskLevel riskLevel = service.getRisk(id);
            model.addAttribute("riskLevel", riskLevel);
            return "risk";
        } catch (PatientClientException e) {
            logger.info("Cannot get patient : " + e.getMessage());
            return "redirect:/patient/list"; // TODO ajout param redirection
        } catch (RiskClientException e) {
            logger.info("Cannot get patient risk : " + e.getMessage());
            return "risk"; // TODO ajout param message d'erreur pour afficher bloc
        }
    }

}
