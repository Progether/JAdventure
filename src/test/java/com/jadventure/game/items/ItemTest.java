package com.jadventure.game.items;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ItemTest {

	@Test
	public void createTest() {
		Item milk = createMilk();
		
		assertEquals("milk-bottle", milk.getId());
		assertEquals("milk", milk.getName());
		assertEquals("Milk in a bottle", milk.getDescription());
		assertEquals(Integer.valueOf(1), milk.getWeight());
//		assertEquals("milk-bottle", milk.getItemID());
	}
	
    private Item createMilk() {
        Map<String, Integer> properties = new HashMap<>();
        properties.put("health", Integer.valueOf(5));
        properties.put("weight", Integer.valueOf(1));
        properties.put("value", Integer.valueOf(10));
        
        Item item = new Item("milk-bottle", "food-liquid", "milk", "Milk in a bottle", properties);
        return item;
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
