package com.mediscreen.webapp.service;

import com.mediscreen.webapp.client.RiskClient;
import com.mediscreen.webapp.model.RiskLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class RiskServiceTest {

    @Mock
    private RiskClient client;

    @InjectMocks
    private RiskService service;

    @Test
    void getRisk() {
        // Arrange
        when(client.getRisk(anyLong())).thenReturn(RiskLevel.NONE);

        // Act
        service.getRisk(1l);

        // Assert
        verify(client, times(1)).getRisk(1l);
    }
}