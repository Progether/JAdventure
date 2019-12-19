package com.jadventure.game.queueprovider;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;

public class ServerQueueProviderTest {
    // these need to be static for use in BeforeClass/AfterClass
    private static QueueProvider queueProvider;
    private static Socket testingSocket;
    private static DataInputStream in;
    private static DataOutputStream out;

    @BeforeClass
    public static void setUp() throws IOException, InterruptedException {
        // create  a pair of client/server sockets for testing
        // use any free port
        final ServerSocket serverSocket = new ServerSocket(0);
        final Socket[] testingSockets = new Socket[1];

        Thread acceptThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    testingSockets[0] = serverSocket.accept();
                } catch (IOException e) {
                    fail("Error while creating sockets");
                }
            }
        });

        acceptThread.start();
        Socket queueSocket = new Socket("localhost", serverSocket.getLocalPort());
        // wait for the connection to be established
        acceptThread.join();
        QueueProvider.startServerMessenger(queueSocket);
        queueProvider = QueueProvider.getInstance();

        testingSocket = testingSockets[0];
        in = new DataInputStream(testingSocket.getInputStream());
        out = new DataOutputStream(testingSocket.getOutputStream());
    }

    @AfterClass
    public static void tearDown() throws IOException{
        in.close();
        out.close();
        testingSocket.close();
        QueueProvider.clearInstance();
    }

    @Test
    public void testOffer() throws IOException {
        String offeredMsg = "Hello World!";
        String expectedMsg = "Hello World!END";
        queueProvider.offer(offeredMsg);
        String actualMsg = in.readUTF();
        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void testTake() throws IOException, InterruptedException {
        final String messageSent = "Hello World!";

        // must receive message in another thread since in.readUTF() blocks execution
        Thread sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String messageReceived = queueProvider.take();
                assertEquals(messageSent, messageReceived);
            }
        });

        sendThread.start();

        String expectedQueryMsg = "QUERYEND";
        String actualQueryMsg = in.readUTF();
        assertEquals(expectedQueryMsg, actualQueryMsg);
        out.writeUTF(messageSent);
        out.flush();

        sendThread.join();
    }
}