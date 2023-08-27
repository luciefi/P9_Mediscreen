package com.mediscreen.webapp.controller;

import com.mediscreen.webapp.exception.PatientClientException;
import com.mediscreen.webapp.exception.UnavailablePatientClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class PatientClientExceptionHandler {
    @ExceptionHandler({
            PatientClientException.class, UnavailablePatientClientException.class})
    public final String handleException(Exception e, RedirectAttributes redirectAttributes) {
        Logger logger = LoggerFactory.getLogger(PatientClientExceptionHandler.class);

        logger.info("Cannot get patient : " + e.getMessage());

        if (e instanceof PatientClientException) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/patient/list";
        }
        return "redirect:/patient/list/unavailable";
    }
}
