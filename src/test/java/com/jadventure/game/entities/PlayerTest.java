package com.jadventure.game.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jadventure.game.navigation.Coordinate;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.repository.PlayerRepository;

public class PlayerTest {

    @Test
    public void createPlayer() {
        Player player = new PlayerRepository().create("recruit");
        int expected = 1;
        int actual = player.getArmour();
        assertEquals("Failure - new player not properly created", expected, actual);

        Coordinate coordinate = player.getLocation().getCoordinate();
        assertEquals(0, coordinate.x); 
        assertEquals(0, coordinate.y); 
        assertEquals(-1, coordinate.z); 
    }

    @Test
    public void loadExistingPlayerProfile() {
        PlayerRepository playerRepo = new PlayerRepository();
        Player player = playerRepo.getPlayer("test");
        String expected = "test";
        String actual = player.getName();
        assertEquals("Failure - old player not properly loaded", expected, actual);

        ILocation location = player.getLocation();
        if (location != null) {
            Coordinate coordinate = player.getLocation().getCoordinate();
            assertEquals(0, coordinate.x); 
            assertEquals(0, coordinate.y); 
            assertEquals(-1, coordinate.z);
        }
    }

}
