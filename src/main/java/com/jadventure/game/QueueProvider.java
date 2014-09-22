package com.jadventure.game;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.Scanner;

public class QueueProvider { 

    public static BlockingQueue<String> queue = new LinkedBlockingQueue();
    public static BlockingQueue<String> inputQueue = new LinkedBlockingQueue();
    public static String mode;

    public static void startMessenger(String m) {
        mode = m;
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
        String message;
        if (mode.equals("server")) {
            queue.offer("QUERY");
            while ((message = inputQueue.poll()) == null) {
            }
        } else {
            Scanner input = new Scanner(System.in);
            message = input.next();
        }
        return message;
    }
}
