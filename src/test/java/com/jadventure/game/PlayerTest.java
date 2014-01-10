package com.jadventure.game;

import com.jadventure.game.entities.Player;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PlayerTest {

    @Test
    public void newPlayer() {
        BlockingQueue queue = new LinkedBlockingQueue();
        Player player = Player.getInstance(queue, "recruit");
        int expected = 1;
        int actual = player.getArmour();
        assertEquals("Failure - new player not properly created", expected, actual);
    }

    @Test
    public void oldPlayer() {
        BlockingQueue queue = new LinkedBlockingQueue();
        Player player = Player.load(queue, "test");
        String expected = "test";
        String actual = player.getName();
        assertEquals("Failure - old player not properly loaded", expected, actual);
    }

}
