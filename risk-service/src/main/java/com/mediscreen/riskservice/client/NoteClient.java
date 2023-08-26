package com.mediscreen.riskservice.client;

import com.mediscreen.riskservice.model.NoteRead;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "notes-service", url = "${note-client-url}", fallback = NoteClientFallback.class)
public interface NoteClient {

    @RequestMapping(method = RequestMethod.GET, value = "/list/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<NoteRead> findAll(
            @PathVariable("id") long patientId);

}
