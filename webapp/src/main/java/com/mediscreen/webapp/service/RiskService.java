package com.mediscreen.webapp.service;

import com.mediscreen.webapp.client.RiskClient;
import com.mediscreen.webapp.model.RiskLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiskService implements IRiskService {

    @Autowired
    private RiskClient client;

    @Override
    public RiskLevel getRisk(Long id) {
        return RiskLevel.valueOf(client.getRisk(id));
    }
}
