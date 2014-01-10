package com.jadventure.game;

import com.jadventure.game.menus.MainMenu;
import com.jadventure.game.Messenger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.lang.InterruptedException;

/**
 * This is the starting point of the game.
 * This class doesn't do much more than create
 * a new MainMenu that will handle the rest of
 * the game.
 */
public class JAdventure {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
        Messenger messenger = new Messenger(queue);
        new Thread(messenger).start();
        new MainMenu(queue);
    }

}
