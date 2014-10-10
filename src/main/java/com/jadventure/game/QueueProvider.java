package com.jadventure.game;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.Scanner;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;

import java.io.IOException;
import java.net.SocketException;

public class QueueProvider { 

    public static BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    public static BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>();
    public static DataOutputStream out;
    public static DataInputStream in;
    public static String mode;
    public static Socket server;

    public static void startMessenger(Socket s,String m) {
        mode = m;
        server = s;
    }

    public static void startMessenger(String m) {
        mode = m;
    }

    public static BlockingQueue getQueue() {
        return queue;
    }

    public static void offer(String message) {
        boolean success = true;
        if (mode.equals("server")) {
            try {
                out = new DataOutputStream(server.getOutputStream());
                in = new DataInputStream(server.getInputStream());
            } catch (IOException e) { e.printStackTrace(); }
        }
        if (mode.equals("server")) {
            success = sendToServer(message);
        } else {
            System.out.println(message);
        }
    }

    public static boolean sendToServer(String message) {
        try {
            int rawMessageLength = message.length();
            out.writeUTF(message+"END");
        } catch(SocketException c) { 
            return false;
        } catch(IOException e) { e.printStackTrace(); }
        return true;
    }

    public static String getInput(String message) {
        String input = "";
        try {
            out.writeUTF(message+"END");
            input = in.readUTF();
        } catch(SocketException c) { 
            input = "error";
        } catch(IOException e) { e.printStackTrace(); }
        return input;
    }

    public static String take() {
        String message;
        if (mode.equals("server")) {
            message = getInput("QUERY");
            if (message.equals("error")) {
                message = "exit";
            }
        } else {
            Scanner input = new Scanner(System.in);
            message = input.nextLine();
        }
        return message;
    }
}
