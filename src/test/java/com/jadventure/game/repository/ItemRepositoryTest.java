package com.jadventure.game.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.jadventure.game.items.Item;

public class ItemRepositoryTest {

    @Test
    public void addItem() {
        ItemRepository itemRepo = new ItemRepository();
        
        itemRepo.addItem(createMilk());
        
        Item item = itemRepo.getItem("milk-bottle");

        assertNotNull(item);
        assertEquals("milk-bottle", item.getId());
        assertEquals("milk", item.getName());
        assertEquals("Milk in a bottle", item.getDescription());
        
        assertNotNull(item.getProperties());
        assertEquals(Integer.valueOf(5), item.getProperties().get("health"));
        assertEquals(Integer.valueOf(1), item.getProperties().get("weight"));
        assertEquals(Integer.valueOf(10), item.getProperties().get("value"));
    }

    @Test
    public void addItems() {
        ItemRepository itemRepo = new ItemRepository();
        
        itemRepo.addItem(createMilk());
        itemRepo.addItem(createEgg());
        
        Item eggItem = itemRepo.getItem("egg-1");
        assertEquals("A nice egg", eggItem.getDescription());
        
        Item milkItem = itemRepo.getItem("milk-bottle");
        assertEquals("Milk in a bottle", milkItem.getDescription());
    }

    @Test
    public void storeItems() throws IOException {
        ItemRepository itemRepo = new ItemRepository();
        
        itemRepo.addItem(createMilk());
        itemRepo.addItem(createEgg());

        Writer writer = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(writer);
        itemRepo.store(jsonWriter);
        jsonWriter.flush();
        
        String expected = "{\"items\":{"
                + "\"egg-1\":{\"id\":\"egg-1\",\"type\":\"food\",\"name\":\"egg\",\"description\":\"A nice egg\","
                + "\"level\":1,\"properties\":{\"health\":2,\"value\":3,\"weight\":1}},"
                + "\"milk-bottle\":{\"id\":\"milk-bottle\",\"type\":\"potion\",\"name\":\"milk\",\"description\":\"Milk in a bottle\","
                + "\"level\":1,\"properties\":{\"health\":5,\"value\":10,\"weight\":1}}"
                + "}}";
        String gsonMsg = writer.toString();
        assertEquals(expected, gsonMsg);
    }

    @Test
    public void retrieveItems() {
        String input = "{\"items\":{"
                + "\"fegg1\":{\"id\":\"fegg1\",\"type\": \"food\",\"name\":\"egg\",\"description\":\"A nice egg\","
                + "\"position\":\"mouth\",\"level\":1,"
                + "\"properties\":{\"weight\":1,\"value\":3,\"health\":2}},"
                + "\"pmil1\":{\"id\":\"pmil1\",\"type\": \"potion\",\"name\":\"milk\",\"description\":\"Milk in a bottle\","
                + "\"position\":\"mouth\",\"level\":1,"
                + "\"properties\":{\"weight\":1,\"value\":10,\"health\":5}}"
                + "}}";
        
        JsonReader jsonReader = new JsonReader(new StringReader(input));

        ItemRepository itemRepo = new ItemRepository();
        
        itemRepo.retrieve(jsonReader);

        Item eggItem = itemRepo.getItem("fegg1");
        assertEquals("A nice egg", eggItem.getDescription());
        
        Item milkItem = itemRepo.getItem("pmil1");
        assertEquals("Milk in a bottle", milkItem.getDescription());
    }

    @Test
    public void load() {
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("items.json");
		
		ItemRepository itemRepo = new ItemRepository();
		JsonReader reader = new JsonReader(new InputStreamReader(resourceAsStream));
		itemRepo.load(reader);
        
    	Item item = itemRepo.getItem("pmil2");
    	assertNotNull(item);
    	assertEquals("potion", item.getType());

    	item = itemRepo.getItem("wdag1");
    	assertNotNull(item);
    	assertEquals("weapon", item.getType());
    }
    
    @Test
    public void getRandomItemTest() {
        ItemRepository itemRepo = ItemRepository.createRepo();

        Item food = itemRepo.getRandomFood(1);
        Item weapon = itemRepo.getRandomWeapon(1);
        Item armour = itemRepo.getRandomArmour(1);
        Item potion = itemRepo.getRandomPotion(1);

        assertNotNull(food);
        assertNotNull(weapon);
        assertNotNull(armour);
        assertNotNull(potion);
    }
    
    private Item createMilk() {
        Map<String, Integer> properties = new TreeMap<>();
        properties.put("health", 5);
        properties.put("weight", 1);
        properties.put("value", 10);

        return new Item("milk-bottle", "potion", "milk", "Milk in a bottle", 1, properties);
    }

    private Item createEgg() {
        Map<String, Integer> properties = new TreeMap<>();
        properties.put("health", 2);
        properties.put("weight", 1);
        properties.put("value", 3);

        return new Item("egg-1", "food", "egg", "A nice egg", 1, properties);
    }

}
