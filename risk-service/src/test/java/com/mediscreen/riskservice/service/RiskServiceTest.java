package com.mediscreen.riskservice.service;

import com.mediscreen.riskservice.client.NoteClient;
import com.mediscreen.riskservice.client.PatientClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class RiskServiceTest {

    @Mock
    private PatientClient client;

    @Mock
    private NoteClient noteClient;

    @InjectMocks
    private RiskService service;
    @Test
    void getRisk() {
        // TODO
    }
}