package com.jadventure.game;

import com.jadventure.game.menus.MainMenu;
import com.jadventure.game.QueueProvider;

/**
 * This is the starting point of the game.
 * This class doesn't do much more than create
 * a new MainMenu that will handle the rest of
 * the game.
 */
public class JAdventure {

    public static void main(String[] args) {
        QueueProvider.startMessenger();
        new MainMenu();
    }

}
