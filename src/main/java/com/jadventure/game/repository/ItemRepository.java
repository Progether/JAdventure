package com.jadventure.game.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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

public class ItemRepository extends AbstractRepository {
    private Map<String, Item> itemMap = new HashMap<>();


    public ItemRepository(File repoPath) {
        super(repoPath, "items.json");
    }

    
    public Item getItem(String id) {
        if (! itemMap.containsKey(id)) {
            throw new RepositoryException("Argument 'id' with value '" + id + "' not found in repository.");
        }
        return itemMap.get(id);
    }


    // Load all items, from the given file
    protected void load(File repo) {
        System.out.println("File " + repo);
        JsonObject jsonItems = new JsonObject();
        try {
            Reader reader = new FileReader(repo);
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(reader).getAsJsonObject();
            jsonItems = json.get("items").getAsJsonObject();
            reader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        Map<String, Map> itemMap = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonItems.entrySet()) {
            String rawItemID = entry.getKey().toString();
            JsonObject itemData = entry.getValue().getAsJsonObject();
            String name = itemData.get("name").getAsString();
            String description = itemData.get("description").getAsString();
            JsonObject sProps = itemData.get("properties").getAsJsonObject();
            Map properties = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry2 : sProps.entrySet()) {
                Integer propValue = entry2.getValue().getAsInt();
                properties.put(entry2.getKey(), propValue);
            }
            Map itemDetails = new HashMap<>();
            itemDetails.put("name", name);
            itemDetails.put("description", description);
            itemDetails.put("properties", properties);
            itemMap.put(rawItemID,itemDetails);
        }
        
        for (Map.Entry<String, Map> entry: itemMap.entrySet()) {
            String id = entry.getKey();
            String name = entry.getValue().get("name").toString();
            String description = entry.getValue().get("description").toString();
            Map<String, Integer> properties = (HashMap<String,Integer>)entry.getValue().get("properties");
            
            addItem(new Item(id, name, description, properties));
        }

    }


    void addItem(Item item) {
        itemMap.put(item.getId(), item);
    }


    private static ItemRepository itemRepository = null;
    public static ItemRepository createRepo() {
        if (itemRepository == null) {
            File file = new File(new File(System.getProperty("user.dir")), "json");
            itemRepository = new ItemRepository(file);
            itemRepository.load();
        }
        return itemRepository;
    }


    public void retrieve(JsonReader jsonReader) {
        throw new RuntimeException("The method " + ItemRepository.class.getSimpleName() 
                + ".retrieve(JsonReader jsonReader), has not yet been implemented.");
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
