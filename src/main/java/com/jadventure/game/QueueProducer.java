package com.jadventure.game;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueProducer { 

    public static BlockingQueue queue = new LinkedBlockingQueue();

    public static void startMessenger() {
        Messenger messenger = new Messenger(queue);
        new Thread(messenger).start();
    }

    public static BlockingQueue getQueue() {
        return queue;
    }

    public static void offer(String message) {
        queue.offer(message);
    }
}
