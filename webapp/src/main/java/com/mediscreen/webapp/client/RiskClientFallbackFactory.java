package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.RiskClientException;
import com.mediscreen.webapp.exception.UnavailableRiskClientException;
import feign.FeignException;
import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
public class RiskClientFallbackFactory implements FallbackFactory<RiskClient> {
    Logger logger = LoggerFactory.getLogger(RiskClientFallbackFactory.class);

    @Override
    public RiskClient create(Throwable cause) {
        logger.error("An exception occurred when calling the RiskClient", cause);

        return new RiskClient() {
            @Override
            public String getRisk(long id) {
                throwRiskClientException("Could not get risk level for patient with id: " + id + " from risk service.");
                return null;
            }

            private void throwRiskClientException(String message) {

                if (cause instanceof FeignException.BadRequest) {
                    throw new RiskClientException("Bad request: " + message);
                }
                if (cause instanceof FeignException.NotFound) {
                    throw new RiskClientException("Not Found: " + message);
                }
                if (cause instanceof RetryableException) {
                    throw new UnavailableRiskClientException("Service unavailable: " + message);
                }

                throw new RiskClientException(message);
            }
        };
    }
}
