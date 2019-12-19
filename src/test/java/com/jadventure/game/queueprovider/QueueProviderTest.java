package com.jadventure.game.queueprovider;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueueProviderTest {
    @AfterClass
    public static void tearDown() {
        QueueProvider.clearInstance();
    }

    @Test
    public void testStartStandaloneMessenger() {
        QueueProvider.startStandaloneMessenger();
        assertTrue(QueueProvider.getInstance() instanceof StandAloneQueueProvider);
    }

    @Test
    public void testStartServerMessenger() {
        QueueProvider.startServerMessenger(null);
        assertTrue(QueueProvider.getInstance() instanceof ServerQueueProvider);
    }

    @Test
    public void testStartTestingMessenger() {
        QueueProvider.startTestingMessenger();
        assertTrue(QueueProvider.getInstance() instanceof TestingQueueProvider);
    }
}