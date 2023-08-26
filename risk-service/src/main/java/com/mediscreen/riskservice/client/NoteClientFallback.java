package com.mediscreen.riskservice.client;

import com.mediscreen.riskservice.model.NoteRead;
import com.mediscreen.riskservice.exception.NoteNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteClientFallback implements NoteClient {

    Logger logger = LoggerFactory.getLogger(NoteClientFallback.class);

    @Override
    public List<NoteRead> findAll(long patientId) {
        return null;
    }

}
