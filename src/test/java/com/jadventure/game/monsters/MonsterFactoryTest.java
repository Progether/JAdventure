package com.jadventure.game.monsters;

import org.junit.Before;
import org.junit.Test;

import com.jadventure.game.entities.Player;
import com.jadventure.game.navigation.Coordinate;
import com.jadventure.game.navigation.Location;
import com.jadventure.game.navigation.LocationType;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MonsterFactoryTest {
    private Player player;
    private Location location;
    private MonsterFactory factory;

    @Before
    public void setUp(){
        player = Player.getInstance("recruit");

        Coordinate coordinate = new Coordinate(1, 1, 0);
        String title = "At the edge of a forest";
        String description = "The are many big trees and some tick busses, " +
            "looks difficult to go through.";
        LocationType locationType = LocationType.FOREST;
        location = new Location(coordinate, title, description, locationType);
        location.setDangerRating(5);

        factory = new MonsterFactory();
    }

    @Test
    public void localizedMonsterGenerationTest_Forest() {
        location.setLocationType(LocationType.FOREST);
        player.setLocation(location);
        Monster forestMonster = factory.generateMonster(player);
        assertTrue(forestMonster instanceof Troll ||
                forestMonster instanceof Bugbear ||
                forestMonster instanceof Goblin);
    }

    @Test
    public void localizedMonsterGenerationTest_Swamp() {
        location.setLocationType(LocationType.SWAMP);
        player.setLocation(location);
        Monster swampMonster = factory.generateMonster(player);
        assertTrue(swampMonster instanceof Goblin ||
                swampMonster instanceof Troll);
    }

    @Test
    public void localizedMonsterGenerationTest_Mountains() {
        location.setLocationType(LocationType.MOUNTAIN);
        player.setLocation(location);
        Monster mountainMonster = factory.generateMonster(player);
        assertTrue(mountainMonster instanceof Giant ||
                mountainMonster instanceof Wolf ||
                mountainMonster instanceof Troll ||
                mountainMonster instanceof Skeleton);
    }

    @Test
    public void localizedMonsterGenerationTest_Cave() {
        location.setLocationType(LocationType.CAVE);
        player.setLocation(location);
        Monster caveMonster = factory.generateMonster(player);
        assertTrue(caveMonster instanceof Troll ||
                caveMonster instanceof Skeleton ||
                caveMonster instanceof Goblin);
    }

    @Test
    public void localizedMonsterGenerationTest_Plains() {
        location.setLocationType(LocationType.PLAINS);
        player.setLocation(location);
        Monster plansMonster = factory.generateMonster(player);
        assertTrue(plansMonster instanceof Bugbear ||
                plansMonster instanceof Goblin);
    }

    @Test
    public void monsterGenerationOnNoDangerLocation(){
        location.setDangerRating(0);
        player.setLocation(location);
        Monster monster = factory.generateMonster(player);
        assertNull(monster);
    }
}
