package com.mediscreen.webapp.client;

import com.mediscreen.webapp.configuration.CustomFeignConfiguration;
import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteRead;
import com.mediscreen.webapp.model.note.NoteUpdate;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notes-service", url = "http://localhost:8082/notes", configuration = CustomFeignConfiguration.class)
public interface NoteClient {

    @RequestMapping(method = RequestMethod.GET, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    CustomPageImpl<NoteRead> findAll(
            @RequestParam(value = "pageNumber", required = false) int pageNumber,
            @RequestParam(value = "itemPerPage", required = false) int itemPerPage);


    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
    NoteRead findById(@PathVariable("id") Long id);

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @Headers("Content-Type: application/json")
    void createNote(NoteCreate note);

    @RequestMapping(method = RequestMethod.PUT)
    @Headers("Content-Type: application/json")
    void save(NoteUpdate note);

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteById(@PathVariable("id") Long id);
}