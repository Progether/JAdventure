package com.jadventure.game.entities;

import org.junit.Test;

import com.jadventure.game.GameBeans;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.items.Item;
import com.jadventure.game.repository.LocationRepository;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.InvalidPathException;

public class PlayerTest {

    @Test
    public void newRecruit() {
        Player player = Player.getInstance("recruit");
        int expected = 1;
        int actual = player.getArmour();
        assertEquals("Failure - new recruit not properly created", expected, actual);
    }
    
    @Test
    public void newSewerRat() {
        Player player = Player.getInstance("sewerrat");
        int expected = 0;
        int actual = player.getArmour();
        assertEquals("Failure - new sewer rat not properly created", expected, actual);
    }
    @Test
    public void oldPlayer() {
        Player player = Player.load("test");
        String expected = "test";
        String actual = player.getName();
        assertEquals("Failure - old player not properly loaded", expected, actual);
    }
 
    @Test(expected = AssertionError.class)
    public void loadNameExit() {
        Player player = Player.getInstance("recruit");
        String playerName = "exit";
        player.setName(playerName);
        LocationRepository temp = GameBeans.getLocationRepository(player.getName());
        player.setLocation(temp.getInitialLocation());
        player.save(); 
        
//        We copied the script of loadProfileFromMenu() from MainMenu.java 
//        because we couldn't run the method since the method was private.
        String key="exit";
        Player player2 = null;
        if (key.equals("exit") || key.equals("back")) {
        	fail("Should have load user 'exit' instead of returning to menu");
        } else if (Player.profileExists(key)) {
            player2 = Player.load(key);
        } else {
            QueueProvider.offer("That user doesn't exist. Try again.");
        }
    }
    
    @Test(expected = InvalidPathException.class)
    public void tryInvalidName1() {
        Player player = Player.getInstance("recruit");
        String questionMark = "?";
        System.out.println(questionMark);
        player.setName(questionMark);
        LocationRepository temp = GameBeans.getLocationRepository(player.getName());
    }
    @Test(expected = InvalidPathException.class)
    public void tryInvalidName2() {
        Player player = Player.getInstance("recruit");
        String pipeName = "|";
        System.out.println(pipeName);
        player.setName(pipeName);
        LocationRepository temp = GameBeans.getLocationRepository(player.getName());
    }
    @Test(expected = InvalidPathException.class)
    public void tryInvalidName3() {
        Player player = Player.getInstance("recruit");
        String doubleQuotationMark = "\"";
        System.out.println(doubleQuotationMark);
        player.setName(doubleQuotationMark);
        LocationRepository temp = GameBeans.getLocationRepository(player.getName());
    }   
    @Test
    public void testItem(){
        boolean bool = false;
        
        Player player = Player.load("test");
        Item item = new Item("0101", "potion", "red", "red potion", null, 1, null);
        bool = player.hasItem(item);
        assertFalse(bool);

        bool = player.hasItem(player.searchItem("milk", player.getStorage()).get(0));
        assertTrue(bool);
        player.dropItem("milk");
        player.pickUpItem("milk");
        player.inspectItem("milk");
        player.equipItem("milk");

        assertTrue(player.searchItem("pmil1", player.getStorage()).isEmpty());
        player.dropItem("pmul1");
        player.pickUpItem("pmil1");
        player.inspectItem("pmil1");
        player.equipItem("pmil1");
    }

    @Test
    public void testLevel(){
        Player player = Player.getInstance("recruit");
        int level = player.getLevel();
        assertEquals(1, level);
        if (level == 1) {
            level++;
        }
        player.setLevel(level);
        assertEquals(2, player.getLevel());
    }

    @Test
    public void testCharacterTypeLevel() {
        Player player = Player.load("test");
        assertEquals("Recruit", player.getCurrentCharacterType());

        int recruit = player.getCharacterLevel("Recruit");
        assertEquals(3, recruit);

        player.setCharacterLevel("Recruit", recruit + 1);
        assertEquals(4, player.getCharacterLevel("Recruit"));
    }
    
}
