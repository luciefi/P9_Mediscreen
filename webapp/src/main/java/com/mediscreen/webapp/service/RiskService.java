package com.mediscreen.webapp.service;

import com.mediscreen.webapp.client.NoteClient;
import com.mediscreen.webapp.client.RiskClient;
import com.mediscreen.webapp.model.RiskLevel;
import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteRead;
import com.mediscreen.webapp.model.note.NoteUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class RiskService implements IRiskService {

    @Autowired
    private RiskClient client;

    @Override
    public RiskLevel getRisk(Long id) {
        // TODO
        return RiskLevel.valueOf(client.getRisk(id));
    }

}
