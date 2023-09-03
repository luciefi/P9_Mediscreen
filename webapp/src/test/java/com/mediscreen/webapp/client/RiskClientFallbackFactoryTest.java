package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.RiskClientException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RiskClientFallbackFactoryTest {

    private RiskClientFallbackFactory riskClientFallbackFactory = new RiskClientFallbackFactory();

    @Test
    void findAll() {
        assertThrows(RiskClientException.class, () -> riskClientFallbackFactory.create(new Throwable()).getRisk(1l));
    }

}