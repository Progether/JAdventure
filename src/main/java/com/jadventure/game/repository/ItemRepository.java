package com.jadventure.game.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.jadventure.game.entities.EquipmentLocation;
import com.jadventure.game.items.Item;

public class ItemRepository {
    private static final Map<String, EquipmentLocation> EQUIPMENT_POSITION_MAP = new HashMap<>();
    private Map<String, Item> itemMap = new HashMap<>();

    static {
        EQUIPMENT_POSITION_MAP.put("head", EquipmentLocation.HEAD);
        EQUIPMENT_POSITION_MAP.put("chest", EquipmentLocation.CHEST);
        EQUIPMENT_POSITION_MAP.put("leftArm", EquipmentLocation.LEFT_ARM);
        EQUIPMENT_POSITION_MAP.put("leftHand", EquipmentLocation.LEFT_HAND);
        EQUIPMENT_POSITION_MAP.put("rightArm", EquipmentLocation.RIGHT_ARM);
        EQUIPMENT_POSITION_MAP.put("rightHand", EquipmentLocation.RIGHT_HAND);
        EQUIPMENT_POSITION_MAP.put("bothHands", EquipmentLocation.BOTH_HANDS);
        EQUIPMENT_POSITION_MAP.put("bothArms", EquipmentLocation.BOTH_ARMS);
        EQUIPMENT_POSITION_MAP.put("legs", EquipmentLocation.LEGS);
        EQUIPMENT_POSITION_MAP.put("feet", EquipmentLocation.FEET);
    }

	public Item getItem(String id) {
        if (id == null || id.trim().length() == 0) {
            return null;
        }
	    if (!itemMap.containsKey(id)) {
	        throw new RepositoryException("Argument 'id' with value '" + id + "' not found in repository.");
	    }
	    return itemMap.get(id);
	}
	
    // Load all items, from the given file
    protected void load(File repo) {
        try {
			JsonReader reader = new JsonReader(new FileReader(repo));
			load(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    // Load all items, from the given JsonReader
    protected void load(JsonReader reader) {
        JsonObject jsonItems = new JsonObject();
        try {
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(reader).getAsJsonObject();
            jsonItems = json.get("items").getAsJsonObject();
            reader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        for (Map.Entry<String, JsonElement> entry : jsonItems.entrySet()) {
            String id = entry.getKey();
            JsonObject itemData = entry.getValue().getAsJsonObject();
            String type = itemData.get("type").getAsString();
            String name = itemData.get("name").getAsString();
            String description = itemData.get("description").getAsString();
            EquipmentLocation position = EQUIPMENT_POSITION_MAP.get(itemData.get("position").getAsString());
            int level = itemData.get("level").getAsInt();
            JsonObject sProps = itemData.get("properties").getAsJsonObject();
            Map<String, Integer> properties = new TreeMap<>();
            for (Map.Entry<String, JsonElement> entry2 : sProps.entrySet()) {
                Integer propValue = entry2.getValue().getAsInt();
                properties.put(entry2.getKey(), propValue);
            }

            addItem(new Item(id, type, name, description, position, level, properties));
            
        }
    }


    void addItem(Item item) {
        itemMap.put(item.getId(), item);
    }

    private static ItemRepository itemRepository = null;
    public static ItemRepository createRepo() {
        if (itemRepository == null) {
            File file = new File(new File(System.getProperty("user.dir")), "json");

            File dataFile = new File(new File(file, "original_data"), "items.json");
            if (! dataFile.exists()) {
            	throw new RuntimeException("File '" + dataFile + "' does not exist.");
            }

            itemRepository = new ItemRepository();
            itemRepository.load(dataFile);
        }
        return itemRepository;
    }


    public void retrieve(JsonReader jsonReader) {
        load(jsonReader);
    }

    public void store(JsonWriter writer) {
        GsonBuilder bldr = new GsonBuilder().setPrettyPrinting();
        Gson gson = bldr.create();
        Map<String, Map<String, Item>> root = new TreeMap<>();
        root.put("items", itemMap);
        gson.toJson(root, Map.class, writer);
    }

    public Item getRandomFood(int level) {
        return getRandomItem("f", level);
    }

    public Item getRandomWeapon(int level) {
        return getRandomItem("w", level);
    }

    public Item getRandomArmour(int level) {
        return getRandomItem("a", level);
    }

    public Item getRandomPotion(int level) {
        return getRandomItem("p", level);
    }

    public Item getRandomItem(String start, int level) {
        Random rand = new Random();
        int chance = rand.nextInt(100);
        if (chance < 70) {
            Item item = null;
            do {
                item = getRandom(start);
            } while (item.getLevel() > level);
            return item;
        } else {
            return getRandom(start);
        }
    }

    private Item getRandom(String start) {
        Random rand = new Random();
        Item item = null;
        do {
            int itemIndex = rand.nextInt(itemMap.size() - 2);
            List<Item> items = new ArrayList<>(itemMap.values());
            item = items.get(itemIndex + 2); // avoids empty and hands items
        } while (!item.getId().startsWith(start));
        return item;
    }
}
