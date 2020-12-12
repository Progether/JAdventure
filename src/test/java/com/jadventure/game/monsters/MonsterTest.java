package com.jadventure.game.monsters;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class MonsterTest {

    private Monster dummyMonster;
    private int playerLevel;

    @Before
    public void setUp() {
        dummyMonster = new Monster() { };
        playerLevel = 5;
    }

    @Test
    public void monsterTypeCompareTest() {
        Troll troll = new Troll(playerLevel);
        Troll anotherTroll = new Troll(playerLevel);
        Goblin notTroll = new Goblin(playerLevel);

        assertEquals(troll, anotherTroll);
        assertNotEquals(troll, notTroll);
        assertNotEquals(null, troll);
        assertNotEquals(troll, new Object());
    }

    @Test
    public void addRandomItemTest() {
        assertTrue(dummyMonster.getStorage().isEmpty());
        dummyMonster.addRandomItems(playerLevel, "arhl1");
        assertFalse(dummyMonster.getStorage().isEmpty());
    }

    @Test
    public void XPTest() {
        dummyMonster.setXPGain(1000);
        assertEquals(1000, dummyMonster.getXPGain());
    }

    @Test
    public void monsterGenerateTest() {
        Goblin goblin = new Goblin(playerLevel);
        assertEquals("Goblin", goblin.monsterType);

        Bugbear bugbear = new Bugbear(playerLevel);
        assertEquals("Bugbear", bugbear.monsterType);

        Giant giant = new Giant(playerLevel);
        assertEquals("Giant", giant.monsterType);

        Skeleton skeleton = new Skeleton(playerLevel);
        assertEquals("Skeleton", skeleton.monsterType);

        Troll troll = new Troll(playerLevel);
        assertEquals("Troll", troll.monsterType);

        Wolf wolf = new Wolf(playerLevel);
        assertEquals("Wolf", wolf.monsterType);
    }
}
