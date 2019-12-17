package com.jadventure.game.prompts;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collection;

import com.jadventure.game.queueprovider.QueueProvider;
import com.jadventure.game.queueprovider.TestingQueueProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.jadventure.game.DeathException;
import com.jadventure.game.GameBeans;
import com.jadventure.game.entities.Player;
import com.jadventure.game.monsters.Troll;
import com.jadventure.game.navigation.Coordinate;
import com.jadventure.game.navigation.Direction;
import com.jadventure.game.navigation.Location;
import com.jadventure.game.navigation.LocationType;
import com.jadventure.game.repository.LocationRepository;

public class CommandCollectionTest {
    private Player player;
    private Location location;
    private CommandCollection collection;
    private TestingQueueProvider testingQueueProvider;

    @Before
    public void setUp() {
        Coordinate coordinate = new Coordinate(1, 1, 0);
        String title = "At the edge of a forest";
        String description = "The are many big trees and some tick busses, " +
            "looks difficult to go through.";
        LocationType locationType = LocationType.FOREST;
        location = new Location(coordinate, title, description, locationType);
        location.setDangerRating(5);

        player = Player.getInstance("recruit");
        player.setLevel(1);
        player.setLocation(location);

        collection = CommandCollection.getInstance();
        collection.initPlayer(player);

        QueueProvider.startTestingMessenger();
        // get a reference to the created TestingQueueProvider object for testing
        testingQueueProvider = (TestingQueueProvider) QueueProvider.getInstance();
    }

    @Test
    public void commandHelpTest() {
        collection.command_help();
        int n = testingQueueProvider.getMessageHistory().length;

        //13 help commands + 1 extra line
        assertTrue(n == 14);
    }

    @Test
    public void commandSaveTest() {
        collection.command_save();
        assertTrue(testingQueueProvider.getMessageHistoryAsString().contains("data was saved"));
    }

    @Test
    public void commandMonsterTest() {
        collection.command_m();
        assertTrue(testingQueueProvider.getMessageHistoryAsString().contains("no monsters"));

        Troll troll = new Troll(player.getLevel());
        player.getLocation().addMonster(troll);
        collection.command_m();
        assertTrue(testingQueueProvider.getMessageHistoryAsString().contains(troll.monsterType));
    }

    @Test
    public void commandGoTest() throws DeathException{
        player.setName("player1");
        LocationRepository locationRepo =
            GameBeans.getLocationRepository(player.getName());
        player.setLocation(locationRepo.getInitialLocation());

        collection.command_g("s");

        assertTrue(testingQueueProvider.getMessageHistoryAsString().contains("Stairs:"));
    }

    @Test
    public void commandInspectTest() {
        collection.command_i("");
        assertTrue(testingQueueProvider.getMessageHistoryAsString().contains("Item doesn't exist"));
    }

    @Test
    public void commandViewTest() {
        collection.command_v("b");
        assertTrue(testingQueueProvider.getMessageHistoryAsString().contains("Backpack"));

        collection.command_v("s");
        assertTrue(testingQueueProvider.getMessageHistoryAsString().contains("Player name:"));

        collection.command_v("e");
        assertTrue(testingQueueProvider.getMessageHistoryAsString().contains("Equipped Items:"));
    }
}
