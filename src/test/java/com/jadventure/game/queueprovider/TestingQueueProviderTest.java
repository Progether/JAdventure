package com.jadventure.game.queueprovider;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class TestingQueueProviderTest {
    private TestingQueueProvider testingQueueProvider;

    @Before
    public void initTestingQueueProvider() {
        QueueProvider.startTestingMessenger();
        testingQueueProvider = (TestingQueueProvider) QueueProvider.getInstance();
    }

    @AfterClass
    public static void tearDown() {
        QueueProvider.clearInstance();
    }

    @Test
    public void testQueuesEmptyWhenCreated() {
        assertEquals(0, testingQueueProvider.getMessageHistory().length);
        assertNull(testingQueueProvider.take());
    }

    @Test
    public void testOffer() {
        String expectedMsg = "Hello World!";
        testingQueueProvider.offer(expectedMsg);
        String actualMsg = testingQueueProvider.getLastMessage();
        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void testGetMessageHistory() {
        String[] expectedMsgs = {"hello", "world", "!"};

        for (String msg : expectedMsgs) {
            testingQueueProvider.offer(msg);
        }

        String[] actualMsgs = testingQueueProvider.getMessageHistory();
        assertArrayEquals(expectedMsgs, actualMsgs);
    }

    @Test
    public void testGetMessageHistoryAsString() {
        String[] msgs = {"hello", "world", "!"};

        for (String msg : msgs) {
            testingQueueProvider.offer(msg);
        }

        String expectedHistory = "hello\nworld\n!\n";
        String actualHistory = testingQueueProvider.getMessageHistoryAsString();
        assertEquals(expectedHistory, actualHistory);
    }

    @Test
    public void testInputQueue() {
        String expectedMsg = "Hello World!";
        testingQueueProvider.offerToInputQueue(expectedMsg);
        String actualMsg = testingQueueProvider.take();
        assertEquals(expectedMsg, actualMsg);
    }
}