package com.jadventure.game;

import com.jadventure.game.menus.MainMenu;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.Client;

/**
 * This is the starting point of the game.
 * This class doesn't do much more than create
 * a new MainMenu that will handle the rest of
 * the game.
 */
public class JAdventure {

    public static void main(String[] args) {
        String mode = "complete";
        if (args.length == 1) {
            if (args[0].equals("server") || args[0].equals("client")) {
                mode = args[0];
            }
        }
        if (mode.equals("client")) {
            new Client();
        } else {
            new QueueProvider().startMessenger(mode);
            new MainMenu();
        }
    }

}
