package com.jadventure.game.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ItemTest {

	@Test
	public void createTest() {
		Item milk = createMilk();
		
		assertEquals("fmil1", milk.getId());
		assertEquals("milk", milk.getName());
		assertEquals("Milk in a bottle", milk.getDescription());
		assertEquals(Integer.valueOf(1), milk.getWeight());
	}
	
	@Test
	public void checkEqual() {
		Item milk = createMilk();

		assertEquals(milk, createMilk());
		assertTrue(milk.equals(createMilk()));
	}

	@Test
	public void checkNotEqual() {
		Item milk = createMilk();
		Item egg = createEgg();
		
		assertNotEquals(milk, egg);
		assertFalse(milk.equals(egg));
	}

    private Item createMilk() {
        Map<String, Integer> properties = new HashMap<>();
        properties.put("health", Integer.valueOf(5));
        properties.put("weight", Integer.valueOf(1));
        properties.put("value", Integer.valueOf(10));
        
        Item item = new Item("fmil1", "food-liquid", "milk", "Milk in a bottle", properties);
        return item;
    }

    private Item createEgg() {
        Map<String, Integer> properties = new HashMap<>();
        properties.put("health", Integer.valueOf(2));
        properties.put("weight", Integer.valueOf(1));
        properties.put("value", Integer.valueOf(3));
        
        Item item = new Item("fegg1", "food", "egg", "A nice egg", properties);
        return item;
    }

}
