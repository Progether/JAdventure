package com.jadventure.game.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.jadventure.game.items.Item;

public class ItemRepositoryTest {

    @Test
    public void addItem() throws IOException {
        File itemFile = File.createTempFile("test-items", "json");
        
        ItemRepository itemRepo = new ItemRepository(itemFile);
        
        itemRepo.addItem(createMilk());
        
        Item item = itemRepo.getItem("milk-bottle");

        assertNotNull(item);
        assertEquals("milk-bottle", item.getItemID());
        assertEquals("milk", item.getName());
        assertEquals("Milk in a bottle", item.getDescription());
        
        assertNotNull(item.getProperties());
        assertEquals(Integer.valueOf(5), item.getProperties().get("health"));
        assertEquals(Integer.valueOf(1), item.getProperties().get("weight"));
        assertEquals(Integer.valueOf(10), item.getProperties().get("value"));
    }

    @Test
    public void addItems() throws IOException {
        File itemFile = File.createTempFile("test-items", "json");
        
        ItemRepository itemRepo = new ItemRepository(itemFile);
        
        itemRepo.addItem(createMilk());
        itemRepo.addItem(createEgg());
        
        Item eggItem = itemRepo.getItem("egg-1");
        assertEquals("A nice egg", eggItem.getDescription());
        
        Item milkItem = itemRepo.getItem("milk-bottle");
        assertEquals("Milk in a bottle", milkItem.getDescription());
    }

    @Test
    public void storeItems() throws IOException {
        File itemFile = File.createTempFile("test-items", "json");
        
        ItemRepository itemRepo = new ItemRepository(itemFile);
        
        itemRepo.addItem(createMilk());
        itemRepo.addItem(createEgg());

        Writer writer = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(writer);
        itemRepo.store(jsonWriter);
        jsonWriter.flush();
        
        String expected = "{\"items\":{"
                + "\"egg-1\":{\"id\":\"egg-1\",\"name\":\"egg\",\"description\":\"A nice egg\","
                + "\"properties\":{\"weight\":1,\"value\":3,\"health\":2}},"
                + "\"milk-bottle\":{\"id\":\"milk-bottle\",\"name\":\"milk\",\"description\":\"Milk in a bottle\","
                + "\"properties\":{\"weight\":1,\"value\":10,\"health\":5}}"
                + "}}";
        String gsonMsg = writer.toString();
        assertEquals(expected, gsonMsg);
    }

    @Test
    @Ignore
    public void retrieveItems() throws IOException {
        String input = "{\"items\":{"
                + "\"egg-1\":{\"id\":\"egg-1\",\"name\":\"egg\",\"description\":\"A nice egg\","
                + "\"properties\":{\"weight\":1,\"value\":3,\"health\":2}},"
                + "\"milk-bottle\":{\"id\":\"milk-bottle\",\"name\":\"milk\",\"description\":\"Milk in a bottle\","
                + "\"properties\":{\"weight\":1,\"value\":10,\"health\":5}}"
                + "}}";

        JsonReader jsonReader = new JsonReader(new StringReader(input));

        File itemFile = File.createTempFile("test-items", "json");
        
        ItemRepository itemRepo = new ItemRepository(itemFile);
        
        itemRepo.retrieve(jsonReader);

        Item eggItem = itemRepo.getItem("egg-1");
        assertEquals("A nice egg", eggItem.getDescription());
        
        Item milkItem = itemRepo.getItem("milk-bottle");
        assertEquals("Milk in a bottle", milkItem.getDescription());
    }

    private Item createMilk() {
        Map<String, Integer> properties = new HashMap<>();
        properties.put("health", Integer.valueOf(5));
        properties.put("weight", Integer.valueOf(1));
        properties.put("value", Integer.valueOf(10));
        
        Item item = new Item("milk-bottle", "milk", "Milk in a bottle", properties);
        return item;
    }

    private Item createEgg() {
        Map<String, Integer> properties = new HashMap<>();
        properties.put("health", Integer.valueOf(2));
        properties.put("weight", Integer.valueOf(1));
        properties.put("value", Integer.valueOf(3));
        
        Item item = new Item("egg-1", "egg", "A nice egg", properties);
        return item;
    }

}
