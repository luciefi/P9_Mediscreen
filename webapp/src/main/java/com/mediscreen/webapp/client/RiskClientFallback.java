package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.RiskClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class RiskClientFallback implements RiskClient {

    @Override
    public String getRisk(long id) {
        throwRiskClientException("Could not get risk level for patient with id: " + id + " from risk service.");
        return null;
    }

    private void throwRiskClientException(String message) {
        throw new RiskClientException(message);
    }
}
