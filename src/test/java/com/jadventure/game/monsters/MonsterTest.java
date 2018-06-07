package com.jadventure.game.monsters;

import static org.junit.Assert.*;

import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.spi.ObjectFactory;
import javax.naming.spi.ObjectFactoryBuilder;

import org.junit.Before;
import org.junit.Test;

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

        assertTrue(troll.equals(anotherTroll));
        assertFalse(troll.equals(notTroll));
        assertFalse(troll.equals(null));
        assertFalse(troll.equals(new Object()));
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
        assertTrue(dummyMonster.getXPGain() == 1000);
    }

    @Test
    public void monsterGenerateTest() {
        Goblin goblin = new Goblin(playerLevel);
        assertTrue(goblin.monsterType.equals("Goblin"));

        Bugbear bugbear = new Bugbear(playerLevel);
        assertTrue(bugbear.monsterType.equals("Bugbear"));

        Giant giant = new Giant(playerLevel);
        assertTrue(giant.monsterType.equals("Giant"));

        Skeleton skeleton = new Skeleton(playerLevel);
        assertTrue(skeleton.monsterType.equals("Skeleton"));

        Troll troll = new Troll(playerLevel);
        assertTrue(troll.monsterType.equals("Troll"));

        Wolf wolf = new Wolf(playerLevel);
        assertTrue(wolf.monsterType.equals("Wolf"));
    }
}
