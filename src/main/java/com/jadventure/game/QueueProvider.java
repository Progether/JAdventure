package com.jadventure.game;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueProvider { 

    public static BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

    public static void startMessenger() {
        Messenger messenger = new Messenger(queue);
        new Thread(messenger).start();
    }

    public static BlockingQueue<String> getQueue() {
        return queue;
    }

    public static void offer(String message) {
        queue.offer(message);
    }
}
