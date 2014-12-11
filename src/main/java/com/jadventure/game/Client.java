package com.jadventure.game;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

    DataInputStream in;
    DataOutputStream out;

    public Client() {
        String serverName = "localhost";
        
        int port = 4044;
        Socket client = null;
        try {
            client = new Socket(serverName, port);
            
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());

            while(true) {
                String serverMessage = in.readUTF();
                if (serverMessage.endsWith("END")) {
                    serverMessage = serverMessage.substring(0, serverMessage.length() - 3);
                    if (serverMessage.equals("QUERY")) {
                        getInput();
                    } else if (serverMessage.equals("EXIT")) {
                        break;
                    } else {
                        System.out.println(serverMessage);
                    }
                } else {
                    String message = "";
                    while(!serverMessage.endsWith("END")) {
                        message += serverMessage;
                        serverMessage = in.readUTF();
                    }
                    message = serverMessage.substring(0, serverMessage.length() - 3);
                    if (message.equals("QUERY")) {
                        getInput();
                    } else if (serverMessage.equals("EXIT")) {
                        break;
                    } else {
                        System.out.println(serverMessage);
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

    public void getInput() {
        Scanner input = null;
        try {
        	input = new Scanner(System.in);
        	String userInput = input.next();
            out.writeUTF(userInput);
        } catch (IOException e) { 
        	e.printStackTrace();
    	} finally {
    		input.close();
    	}
    }
}

