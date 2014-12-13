package com.jadventure.game;

import com.jadventure.game.menus.MainMenu;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.Client;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.net.SocketException;

/**
 * This is the starting point of the game.
 * This class doesn't do much more than create
 * a new MainMenu that will handle the rest of
 * the game.
 */
public class JAdventure {

    public static void main(String[] args) {
        GameModeType mode = GameModeType.STAND_ALONE;
        if (args.length == 1) {
            mode = GameModeType.valueOf(args[0].toUpperCase());
        }
        if (GameModeType.CLIENT == mode) {
            new Client();
        } else {
            if (GameModeType.SERVER == mode) {
                while (true) {
                	ServerSocket listener = null;
                    try {
                        listener = new ServerSocket(4044);
                        while (true) {
                            Socket server = listener.accept();
                            Runnable r = new MainMenu(server,mode);
                            new Thread(r).start();
                        }
                    } catch (SocketException e) { 
                       e.printStackTrace();
                    } catch (IOException c) {
                        c.printStackTrace();
                    } finally {
                    	try {
							listener.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
                    }
                }
            } else {
                QueueProvider.startMessenger(GameModeType.STAND_ALONE);
                new MainMenu();
            }
        }
    }
}
