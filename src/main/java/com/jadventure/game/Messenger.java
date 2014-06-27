package com.jadventure.game;

import java.util.concurrent.BlockingQueue;

class Messenger extends Thread {
    private BlockingQueue<String> queue = null;


    public Messenger(BlockingQueue<String> queue) {
        this.queue = queue;
    }


    public void run() {
        while (true) {
            String message;
            while ((message = queue.poll()) != null) {
                System.out.println(message);
            }
        }
    }

}
