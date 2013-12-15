package com.jadventure.game;

import com.jadventure.game.entities.Player;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Test
    public void newPlayer() {
        Player player = Player.getInstance("recruit");
        int expected = 1;
        int actual = player.getArmour();
        assertEquals("Failure - new player not properly created", expected, actual);
    }

    @Test
    public void oldPlayer() {
        Player player = Player.load("test");
        String expected = "test";
        String actual = player.getName();
        assertEquals("Failure - old player not properly loaded", expected, actual);
    }

}
