package com.jadventure.game;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Messenger extends Thread {

    BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

    public Messenger(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        while(true) {
            String message;
            while ((message = queue.poll()) != null) {
                System.out.println(":" + message);
            }
        }
    }

}
