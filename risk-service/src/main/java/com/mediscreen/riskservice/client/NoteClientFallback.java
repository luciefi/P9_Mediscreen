package com.mediscreen.riskservice.client;

import com.mediscreen.riskservice.exception.NoteClientException;
import com.mediscreen.riskservice.model.NoteRead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteClientFallback implements NoteClient {

    Logger logger = LoggerFactory.getLogger(NoteClientFallback.class);

    @Override
    public List<NoteRead> findAll(long patientId) {
        String message = "Could not retrieve note list for patient with id: " + patientId;
        logger.info(message);
        throw new NoteClientException(message);
    }
}
