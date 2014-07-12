package com.jadventure.game.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import com.jadventure.game.items.Item;
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
    public void createSewerRat() {
    	String id = "Sewer Rat";
    	String name = "Super Sewer Rat";
    	int healthMax = 1;
    	int health = 2;
    	double damage = 3;
		int armour = 4;
    	int level = 6;
    	int strength = 7;
    	int intelligence = 8;
		int dexterity = 9;
    	int stealth = 10;
    	Item weapon = createKnife();
    	String introduction = "Welcome Sewer Rat!";
		int luck = 3;
    	
        Player sewerRat = new Player(id, name, healthMax, health, damage, armour, level, 
        		strength, intelligence, dexterity, stealth, weapon, introduction, luck);

        assertEquals(id, sewerRat.getId());
        assertEquals(name, sewerRat.getClassName());
        assertEquals("h Max", healthMax, sewerRat.getHealthMax());
        assertEquals("h", health, sewerRat.getHealth());
        assertEquals("d", Double.valueOf(damage), Double.valueOf(sewerRat.getDamage()));
        assertEquals("a", armour, sewerRat.getArmour());
        assertEquals("level", level, sewerRat.getLevel());
        assertEquals("stength", strength, sewerRat.getStrength());
        assertEquals("intelligence", intelligence, sewerRat.getIntelligence());
        assertEquals("dexterity", dexterity, sewerRat.getDexterity());
        assertEquals("stealth", stealth, sewerRat.getStealth());
        assertEquals("weapon", weapon, sewerRat.getWeapon());
        assertEquals(introduction, sewerRat.getIntro());
        System.out.println((1 <= sewerRat.getLuck()) + "  " + ((luck + 1) >= sewerRat.getLuck()) + " " + sewerRat.getLuck());
        assertTrue("luck", 1 <= sewerRat.getLuck() && (luck + 1 >= sewerRat.getLuck()));
    }
    
    private Item createKnife() {
    	String id = "knife1";
    	String type = "weapon";
    	String name = "knife";
    	String description = "nise sharp knife";
    	Map<String, Integer> properties = null;
		return new Item(id, type, name, description, properties);
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
