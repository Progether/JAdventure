package com.jadventure.game;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueProvider { 

    public static BlockingQueue queue = new LinkedBlockingQueue();

    public static void startMessenger(String mode) {
        Messenger messenger = new Messenger(queue,mode);
        new Thread(messenger).start();
    }

    public static BlockingQueue getQueue() {
        return queue;
    }

    public static void offer(String message) {
        queue.offer(message);
    }
}
