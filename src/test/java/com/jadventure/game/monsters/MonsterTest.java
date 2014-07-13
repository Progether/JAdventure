package com.jadventure.game.monsters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.jadventure.game.items.Item;
import com.jadventure.game.repository.MonsterBuilder;

@RunWith(JUnit4.class)
public class MonsterTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void create() {
    	String id = "troll";
    	String type = "monster";
    	String name = "Troll";
    	int healthMax = 20;
    	int health = 10;
    	double damage = 9.0;
    	int armour = 9;
    	Item weapon = null;
    	Item egg = createEgg();
    	
    	MonsterBuilder bldr = new MonsterBuilder();
    	bldr.setId(id);
		bldr.setType(type);
    	bldr.setName(name);
    	bldr.setHealthMax(healthMax);
    	bldr.setHealth(health);
		bldr.setDamage(damage);
		bldr.setArmour(armour);
		bldr.setWeapon(weapon);
		bldr.getStorage().add(egg);
    	
    	Monster troll = bldr.create();
    	
        assertEquals(id, troll.getId());
        assertEquals(type, troll.getType());
        assertEquals(name, troll.getName());
        assertEquals(healthMax, troll.getHealthMax());
        assertEquals(health, troll.getHealth());
//        assertEquals(damage, troll.getDamage());
        assertEquals(armour, troll.getArmour());
        assertEquals(weapon, troll.getWeapon());
        assertTrue(troll.getStorage().contains(egg.getName()));
        assertEquals(egg, troll.getStorage().getItem(egg.getName()));
    }

    private Item createEgg() {
        Map<String, Integer> properties = new HashMap<>();
        properties.put("health", Integer.valueOf(2));
        properties.put("weight", Integer.valueOf(1));
        properties.put("value", Integer.valueOf(3));
        
        Item item = new Item("egg-1", "food", "egg", "A nice egg", properties);
        return item;
    }

}
