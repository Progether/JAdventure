package com.jadventure.game.queueprovider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

public abstract class QueueProvider {
    protected static Logger logger = LoggerFactory.getLogger(QueueProvider.class);

    private static QueueProvider instance;

    public static QueueProvider getInstance() {
        return instance;
    }

    public static void startStandaloneMessenger() {
        logger.debug("Starting messenger in STANDALONE mode");

        instance = new StandAloneQueueProvider();
    }

    public static void startServerMessenger(Socket sockerInc) {
        logger.debug("Starting messenger in SERVER mode");

        instance = new ServerQueueProvider(sockerInc);
    }

    public static void startTestingMessenger() {
        logger.debug("Starting messenger for automated testing purposes");

        instance = new TestingQueueProvider();
    }

    public void offer(String message) {
        logger.debug("offer( " + message + " )");
    }

    public abstract String take();
}
