package com.jadventure.game.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class StorageTest {

	@Test
	public void addWisky() {
		Storage storage = new Storage();
		storage.add(createWiskyBottle());
		assertEquals(5,  storage.calculateWeight().intValue());

		storage.add(createWiskyBottle());
		assertEquals(10,  storage.calculateWeight().intValue());

		List<Item> items = storage.search("wisky");
		assertNotNull(items);
        assertEquals(items.size(), 1);
        Item wisky = items.get(0);
		assertNotNull(wisky);
		assertEquals(5, wisky.getWeight().intValue());

		assertEquals(10,  storage.calculateWeight().intValue());
	}

    @Test
    public void addAndRemoveWiskyAndBook() {
        Storage storage = new Storage();
        storage.add(createWiskyBottle());
        assertEquals(5, storage.calculateWeight().intValue());
        storage.add(createBook());
        assertEquals(9, storage.calculateWeight().intValue());

        List<Item> items = storage.search("wisky");
        assertNotNull(items);
        assertEquals(items.size(), 1);
        Item wisky = items.get(0);
        assertNotNull(wisky);
        assertEquals(5, wisky.getWeight().intValue());

        items = storage.search("book");
        assertNotNull(items);
        assertEquals(items.size(), 1);
        Item book = items.get(0);
        assertNotNull(book);
        assertEquals(4, book.getWeight().intValue());

        assertEquals(9, storage.calculateWeight().intValue());

        storage.remove(book);
        assertEquals(5, storage.calculateWeight().intValue());
        storage.remove(book);
        assertEquals(5, storage.calculateWeight().intValue());
        storage.remove(wisky);
        assertEquals(0, storage.calculateWeight().intValue());
    }

	private Item createWiskyBottle() {
        Map<String, Integer> properties = new HashMap<>();
        properties.put("health", 2);
        properties.put("weight", 5);
		return new Item("wisky-bottle", "food-liquid", "wisky", "Nice brand", null, 1, properties);
	}

    private Item createBook() {
        Map<String, Integer> properties = new HashMap<>();
        properties.put("health", 0);
        properties.put("weight", 4);
        return new Item("book", "item", "book", "Old book, containing lots of spells", null, 1, properties);
    }
}
