package com.jadventure.game.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class ItemTest {

	@Test
	public void createTest() {
		Item milk = createMilk();
		
		assertEquals("pmil1", milk.getId());
		assertEquals("milk", milk.getName());
		assertEquals("Milk in a bottle", milk.getDescription());
        assertEquals(1, milk.getLevel());
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
        Map<String, Integer> properties = new TreeMap<>();
        properties.put("health", 5);
        properties.put("weight", 1);
        properties.put("value", 10);
        
        Item item = new Item("pmil1", "food-liquid", "milk", "Milk in a bottle", 1, properties);
        return item;
    }

    private Item createEgg() {
        Map<String, Integer> properties = new TreeMap<>();
        properties.put("health", 2);
        properties.put("weight", 1);
        properties.put("value", 3);
        
        Item item = new Item("fegg1", "food", "egg", "A nice egg", 1, properties);
        return item;
    }

}
