package com.mediscreen.notes.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserUtilsTest {

    @Test
    void getUserId() {
        assertEquals(1L, UserUtils.getUserId());
    }
}