package com.mediscreen.webapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "risk-service", url = "${risk-client-url}", fallback = RiskClientFallback.class)
public interface RiskClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "text/plain;charset=UTF-8")
    String getRisk(@PathVariable("id") long id);

}
