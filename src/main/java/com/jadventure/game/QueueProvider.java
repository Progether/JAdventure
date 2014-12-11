package com.jadventure.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueProvider { 

    public static BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    public static BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>();
    public static DataOutputStream out;
    public static DataInputStream in;
    public static GameModeType mode;
    public static Socket server;

    public static void startMessenger(Socket sockerInc, GameModeType modeInc) {
        mode = modeInc;
        server = sockerInc;
    }

    public static void startMessenger(GameModeType modeInc) {
        mode = modeInc;
    }

    public static BlockingQueue<String> getQueue() {
        return queue;
    }

    public static void offer(String message) {
        if (mode.equals("server")) {
            try {
                out = new DataOutputStream(server.getOutputStream());
                in = new DataInputStream(server.getInputStream());
            } catch (IOException e) { e.printStackTrace(); }
        }
        if (mode.equals("server")) {
        	// Nothing to do
        } else {
            System.out.println(message);
        }
    }

    public static boolean sendToServer(String message) {
        try {
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
        } catch(IOException e) { 
        	e.printStackTrace();
        }
        return input;
    }

    public static String take() {
        String message = null;
        if (mode.equals("server")) {
            message = getInput("QUERY");
            if (message.equals("error")) {
                message = "exit";
            }
        } else {
        	Scanner input = null;
        	try {
	            input = new Scanner(System.in);
	            message = input.nextLine();
        	}
        	catch (NoSuchElementException nsee) {
        		
        	}
        	catch (IllegalStateException ise) {
        		
        	}
        }
        return message;
    }
}
