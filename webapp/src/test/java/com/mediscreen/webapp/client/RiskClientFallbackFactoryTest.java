package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.RiskClientException;
import com.mediscreen.webapp.exception.UnavailableRiskClientException;
import feign.FeignException;
import feign.RetryableException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class RiskClientFallbackFactoryTest {

    private RiskClientFallbackFactory riskClientFallbackFactory = new RiskClientFallbackFactory();

    @Test
    void findAll() {
        RiskClientException exception = assertThrows(RiskClientException.class, () -> riskClientFallbackFactory.create(new Throwable()).getRisk(1l));
    }

    @Test
    void findAll_BadRequest() {
        RiskClientException exception = assertThrows(RiskClientException.class, () -> riskClientFallbackFactory.create(mock(FeignException.BadRequest.class)).getRisk(1l));
    }

    @Test
    void findAll_NotFound() {
        RiskClientException exception = assertThrows(RiskClientException.class, () -> riskClientFallbackFactory.create(mock(FeignException.NotFound.class)).getRisk(1l));
    }

    @Test
    void findAll_ServiceUnavailable() {
        UnavailableRiskClientException exception = assertThrows(UnavailableRiskClientException.class, () -> riskClientFallbackFactory.create(mock(RetryableException.class)).getRisk(1l));
    }

}