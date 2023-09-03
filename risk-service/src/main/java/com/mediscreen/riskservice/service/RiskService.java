package com.mediscreen.riskservice.service;

import com.mediscreen.riskservice.client.NoteClient;
import com.mediscreen.riskservice.client.PatientClient;
import com.mediscreen.riskservice.model.NoteRead;
import com.mediscreen.riskservice.model.Patient;
import com.mediscreen.riskservice.model.RiskLevel;
import com.mediscreen.riskservice.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RiskService implements IRiskService {

    @Autowired
    private PatientClient patientClient;

    @Autowired
    private NoteClient noteClient;

    // TODO mettre la liste dans app properties, avec un séparateur et split string
    @Value("#{'${risk-keywords}'.split(',')}")
    List<String> keywords;

    @Override
    public RiskLevel getPatientRisk(Long id) {
        Patient patient = patientClient.findById(id);

        boolean isUnder30 = DateUtils.getAge(patient.getDateOfBirth()) < 30;
        int numberOfTerms = getNumberOfTerms(noteClient.findAll(id));

        return getRiskLevel(isUnder30, patient.getSex().equals('M'), numberOfTerms);
    }

    private int getNumberOfTerms(List<NoteRead> notes) {
        int numberOfTerms = 0;

        for(String keyword : this.keywords){
            if( notes.stream().anyMatch(note -> note.getContent().contains(keyword))){
                numberOfTerms++;
            }
        }
        return numberOfTerms;
        //return (int) Math.floor(Math.random() * 8);
    }

    private static RiskLevel getRiskLevel(boolean isUnder30, boolean isMale, int numberOfTerms) {
        RiskLevel riskLevel = RiskLevel.NONE;
        if (isUnder30) {
            if (isMale) {
                if (numberOfTerms >= 3) {
                    riskLevel = RiskLevel.IN_DANGER;
                }

                if (numberOfTerms >= 5) {
                    riskLevel = RiskLevel.EARLY_ONSET;
                }
            } else {
                // Patient is female
                if (numberOfTerms >= 4) {
                    riskLevel = RiskLevel.IN_DANGER;
                }

                if (numberOfTerms >= 7) {
                    riskLevel = RiskLevel.EARLY_ONSET;
                }
            }
        } else {
            // Patient is Over 30
            if (numberOfTerms >= 2) {
                riskLevel = RiskLevel.BORDERLINE;
            }

            if (numberOfTerms >= 6) {
                riskLevel = RiskLevel.IN_DANGER;
            }

            if (numberOfTerms >= 8) {
                riskLevel = RiskLevel.EARLY_ONSET;
            }
        }

        return riskLevel;
    }
}
