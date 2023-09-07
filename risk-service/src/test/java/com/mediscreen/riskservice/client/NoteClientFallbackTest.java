package com.mediscreen.riskservice.client;

import com.mediscreen.riskservice.exception.NoteClientException;
import feign.FeignException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class NoteClientFallbackTest {

    NoteClientFallback noteClientFallback = new NoteClientFallback();

    @Test
    void findAll() {
        NoteClientException exception = assertThrows(NoteClientException.class, () -> noteClientFallback.findAll(1l));
        assertEquals("Could not retrieve note list for patient with id: 1", exception.getMessage());
    }
}