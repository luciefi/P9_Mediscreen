package com.mediscreen.riskservice.service;

import com.mediscreen.riskservice.model.RiskLevel;

public interface IRiskService {
    RiskLevel getPatientRisk(Long id);
}
