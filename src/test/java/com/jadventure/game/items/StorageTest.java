package com.jadventure.game.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class StorageTest {

	@Test
	public void create() {
		Storage storage = new Storage();
		storage.add(createWiskyBottle());
		assertEquals(5,  storage.calculateWeight().intValue());

		storage.add(createWiskyBottle());
		assertEquals(10,  storage.calculateWeight().intValue());

		Item wisky = storage.remove("wisky");
		assertNotNull(wisky);
		assertEquals(5, wisky.getWeight().intValue());

		assertEquals(5,  storage.calculateWeight().intValue());
	}

	private Item createWiskyBottle() {
        Map<String, Integer> properties = new HashMap<>();
        properties.put("health", Integer.valueOf(2));
        properties.put("weight", Integer.valueOf(5));
		return new Item("wisky-bottle", "wisky", "Nice brand", properties);
	}
}
