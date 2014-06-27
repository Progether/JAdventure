package com.jadventure.game.monsters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class MonsterTest {
    private Monster monster;

//
//    MonsterCreator monsterCreator;
//    ArrayList<Monster> monsterArrayList;

    @Before
    public void setUp() {
        monster = new Giant(2);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGenerateName() {
        assertThat(monster.getName(), instanceOf(String.class));
    }

    @Test
    public void testGenerateDamage() {
        assertThat(monster.getDamage(), instanceOf(double.class));
    }

    @Test
    public void testGenerateHealth() {
        assertThat(monster.getHealthMax(), instanceOf(int.class));
    }

    @Test
    public void testGenerateArmour() {
        assertThat(monster.getArmour(), instanceOf(int.class));
    }
}
