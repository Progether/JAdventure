package com.jadventure.game;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueProvider { 

    public static BlockingQueue<String> queue = new LinkedBlockingQueue();
    public static BlockingQueue<String> inputQueue = new LinkedBlockingQueue();

    public static void startMessenger(String mode) {
        Messenger messenger = new Messenger(queue,inputQueue,mode);
        new Thread(messenger).start();
    }

    public static BlockingQueue getQueue() {
        return queue;
    }

    public static void offer(String message) {
        queue.offer(message);
    }

    public static String take() {
        queue.offer("QUERY");
        String message;
        while ((message = inputQueue.poll()) == null) {
        }
        return message;
    }
}
