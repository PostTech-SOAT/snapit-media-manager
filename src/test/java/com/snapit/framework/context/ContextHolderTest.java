package com.snapit.framework.context;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContextHolderTest {

    @Test
    void testSetAndGetEmail() {
        String email = "test@example.com";
        ContextHolder.setEmail(email);
        assertEquals(email, ContextHolder.getEmail());
    }

    @Test
    void testRemoveEmail() {
        String email = "test@example.com";
        ContextHolder.setEmail(email);
        ContextHolder.removeEmail();
        assertEquals("empty", ContextHolder.getEmail());
    }

}
