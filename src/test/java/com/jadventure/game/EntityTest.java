package com.jadventure.game;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import com.jadventure.game.entities.Entity;
import com.jadventure.game.entities.Player;
import com.jadventure.game.items.Backpack;
import com.jadventure.game.items.Item;
import com.jadventure.game.items.ItemStack;
import com.jadventure.game.items.Storage;

public class EntityTest {
    private Entity entity;

    @Before
    public void setUp() {
        entity = new Player();
    }

    @After
    public void destroy() {
        entity = null;
    }

    @Test
    public void testCreation() {
        Assert.assertTrue(entity instanceof Entity);
    }

    @Test
    public void testType() {
        testInt(entity.getHealthMax());
        testInt(entity.getHealth());
        Object test = entity.getName();
        Assert.assertTrue(test instanceof String);
        testInt(entity.getLevel());
        testInt(entity.getStrength());
        testInt(entity.getIntelligence());
        testInt(entity.getDexterity());
        testInt(entity.getLuck());
        testInt(entity.getStealth());
        testInt(entity.getGold());
        test = entity.getDamage();
        Assert.assertTrue(test instanceof Double);
        test = entity.getWeapon();
        Assert.assertTrue(test instanceof String);
        test = entity.getEquipment();
        Assert.assertTrue(test instanceof HashMap);
    }

    @Test
    public void testSetters() {
        entity.setHealth(50);
        Assert.assertEquals(entity.getHealth(), 50);
        Assert.assertTrue(entity.getHealthMax() >= entity.getHealth());
        entity.setGold(10);
        Assert.assertEquals(entity.getGold(), 10);
        entity.setArmour(20);
        Assert.assertEquals(entity.getArmour(), 20);
        entity.setHealthMax(30);
        Assert.assertEquals(entity.getHealthMax(), 30);
        Assert.assertTrue(entity.getHealth() <= entity.getHealthMax());
        entity.setLevel(3);
        Assert.assertEquals(entity.getLevel(), 3);
    }

    @Test
    public void testStorage() {
        Item item = new Item("fmil1");
        entity.setStorage(new Backpack(300));
        entity.addItemToStorage(item);
        Assert.assertEquals(entity.getStorage().getItems().get(0).getItem(), item);
    }

    private void testInt(Object test) {
        Assert.assertTrue(test instanceof Integer);
    }
}
