package com.jadventure.game;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.io.DataOutputStream;
import java.io.DataInputStream;

import java.io.IOException;

class Messenger extends Thread {

    BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
    BlockingQueue<String> inputQueue = new LinkedBlockingQueue<String>();
    String mode;
    ServerSocket listener;
    Socket server;
    DataOutputStream out;
    DataInputStream in;

    public Messenger(BlockingQueue q, BlockingQueue i, String m) {
        inputQueue = i;
        queue = q;
        mode = m;
    }

    public void run() {
        if (mode.equals("server")) {
            try {
                listener = new ServerSocket(4044);
                server = listener.accept();
                out = new DataOutputStream(server.getOutputStream());
                in = new DataInputStream(server.getInputStream());
            } catch(IOException e) { e.printStackTrace(); }
        }
        while(true) {
            String message;
            while ((message = queue.poll()) != null) {
                if (mode.equals("server")) {
                    if (message == "QUERY") {
                        getInput(message);
                    } else {
                        sendToServer(message);
                    }
                } else {
                    System.out.println(message);
                }
            }
        }
    }

    public void sendToServer(String message) {
        try {
            int rawMessageLength = message.length();
            out.writeUTF(message+"END");
        } catch(IOException e) { e.printStackTrace(); }
    }

    public void getInput(String message) {
        try {
            out.writeUTF(message+"END");
            String input = in.readUTF();
            inputQueue.offer(input);
        } catch(IOException e) { e.printStackTrace(); }
    }
}
