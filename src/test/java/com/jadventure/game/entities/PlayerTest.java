package com.jadventure.game.entities;

import org.junit.Test;

import com.jadventure.game.GameBeans;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.repository.LocationRepository;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
 
    @Test
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
            ;
        } else if (Player.profileExists(key)) {
            player2 = Player.load(key);
        } else {
            QueueProvider.offer("That user doesn't exist. Try again.");
        }
        assertNotNull("Failure - Can not load user 'exit' eventhough it exists.",player2);
    }
    
    @Test
    public void tryInvalidName1() {
        Player player = Player.getInstance("recruit");
        String questionMark = "?";
        System.out.println(questionMark);
        player.setName(questionMark);
        try{
        	LocationRepository temp = GameBeans.getLocationRepository(player.getName());
        }catch(Exception e) {
           assertEquals("Failure - Invalid Name", 1,0 );
        }
    }
    @Test
    public void tryInvalidName2() {
        Player player = Player.getInstance("recruit");
        String pipeName = "|";
        System.out.println(pipeName);
        player.setName(pipeName);
        try{
        	LocationRepository temp = GameBeans.getLocationRepository(player.getName());
        }catch(Exception e) {
           assertEquals("Failure - Invalid Name", 1,0 );
        }
    }
    @Test
    public void tryInvalidName3() {
        Player player = Player.getInstance("recruit");
        String doubleQuotationMark = "\"";
        System.out.println(doubleQuotationMark);
        player.setName(doubleQuotationMark);
        try{
        	LocationRepository temp = GameBeans.getLocationRepository(player.getName());
        }catch(Exception e) {
           assertEquals("Failure - Invalid Name", 1,0 );
        }
    }
    
}
