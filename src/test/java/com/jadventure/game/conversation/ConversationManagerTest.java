package com.jadventure.game.conversation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class ConversationManagerTest {
    ConversationManager cm;

    @Before
    public void setUp() {
        cm = ConversationManager.getInstance();
    }

    @Test 
    public void testCreation() {
        assertNotNull(cm);
        assertTrue(cm instanceof ConversationManager);
    }
}
