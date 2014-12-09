package com.jadventure.game.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.jadventure.game.items.Item;

public class ItemRepository {
	private Map<String, Item> itemMap = new HashMap<>();

	public Item getItem(String id) {
		if (id == null || id.trim().length() == 0) {
			return null;
		}
	    if (! itemMap.containsKey(id)) {
	        throw new RepositoryException("Argument 'id' with value '" + id + "' not found in repository.");
	    }
	    return itemMap.get(id);
	}
	
    // Load all items, from the given file
    protected void load(File repo) {
        System.out.println("File " + repo);
        try {
			JsonReader reader = new JsonReader(new FileReader(repo));
			load(reader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    // Load all items, from the given file
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
            String position = itemData.get("position").getAsString();
            JsonObject sProps = itemData.get("properties").getAsJsonObject();
            Map<String, Integer> properties = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry2 : sProps.entrySet()) {
                Integer propValue = entry2.getValue().getAsInt();
                properties.put(entry2.getKey(), propValue);
            }

            addItem(new Item(id, type, name, description, position, properties));
            
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
        
//        throw new RuntimeException("The method " + ItemRepository.class.getSimpleName() 
//                + ".retrieve(JsonReader jsonReader), has not yet been implemented.");
//        Gson gson = new Gson();
//        gson.fromJson(jsonReader, );
    }

    public void store(JsonWriter writer) {
        GsonBuilder bldr = new GsonBuilder().setPrettyPrinting();
        Gson gson = bldr.create();
        Map<String, Map<String, Item>> root = new HashMap<>();
        root.put("items", itemMap);
        gson.toJson(root, Map.class, writer);
    }

}
