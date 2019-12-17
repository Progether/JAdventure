package com.jadventure.game.queueprovider;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerQueueProvider extends QueueProvider{
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;

    protected ServerQueueProvider(Socket socket) {
        this.socket = socket;

        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException ioe) {
            logger.debug("Inside ServerQueueProvider( " + socket+ " )", ioe);
        }
    }

    @Override
    public void offer(String message) {
        super.offer(message);

        sendToServer(message);
    }

    @Override
    public String take() {
        String message = getInput("QUERY");

        if (message.equals("error")) {
            message = "exit";
        }

        return message;
    }

    public boolean sendToServer(String message) {
        logger.debug("sendToServer( " + message + " )");
        try {
            out.writeUTF(message + "END");
        } catch(IOException se) {
            logger.debug("Inside  sendToServer( " + message + " )", se);
            return false;
        }
        return true;
    }

    private String getInput(String message) {
        logger.debug("getInput( " + message + " )");
        String input;

        try {
            sendToServer(message);
            input = in.readUTF();
        } catch(IOException ioe) {
            logger.debug("Inside getInput( " + message + " )", ioe);
            input = "error";
        }

        return input;
    }
}
