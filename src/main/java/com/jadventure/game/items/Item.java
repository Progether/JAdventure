package com.jadventure.game.items;

import java.util.HashMap;
import java.util.Map;

import com.jadventure.game.QueueProvider;

/**
 * Items lay around in the game world and can be taken with you.
 */
public class Item {
	private String id;
    private final String type;
    private String name;
    private String description;
    private String position;
    private Map<String, Integer> properties;

    public Item(String id, String type, String name, String description, Map<String, Integer> properties) {
        this(id, type, name, description, null, properties);
    }
    public Item(String id, String type, String name, String description, String position, Map<String, Integer> properties) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.position = position;
        if (properties != null) {
            this.properties = properties;
        }
        else {
            this.properties = new HashMap<>();
        }
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return type;
    }

    public Integer getWeight() {
        if (properties.containsKey("weight")) {
            return properties.get("weight");
        }

        return Integer.valueOf(0);
    }

    public String getPosition() {
    	return this.position;
    }

    public String getDescription() {
        return this.description;
    }

    public int getProperty(String property) {
        return this.properties.get(property);
    }

    public Map<String, Integer> getProperties() {
        return properties;
    }

    public boolean propertiesContainsKey(String key) {
        return this.properties.containsKey(key);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Item) {
            Item i = (Item) obj;
            return this.name.equals(i.name);
        }
        return false;
    }

    public void display() {
        QueueProvider.offer("Name: " + this.name +
                "\nDescription: " + this.description);
        for (Map.Entry entry : this.properties.entrySet()) {
            QueueProvider.offer(entry.getKey() + ": " + entry.getValue());
        }
    }

//    // gets data about an item based on its itemID
//    public void lookUpItem(String itemID) {
//        String fileName = "json/items.json";
//        JsonObject items = new JsonObject();
//        try {
//            Reader reader = new FileReader(fileName);
//            JsonParser parser = new JsonParser();
//            JsonObject json = parser.parse(reader).getAsJsonObject();
//            items = json.get("items").getAsJsonObject();
//            reader.close();
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        HashMap<String, HashMap> itemList = new HashMap<String, HashMap>();
//        for (Map.Entry<String, JsonElement> entry : items.entrySet()) {
//            HashMap<String, Object> itemDetails = new HashMap<String, Object>();
//            String rawItemID = entry.getKey().toString();
//            JsonObject itemData = entry.getValue().getAsJsonObject();
//            String name = itemData.get("name").getAsString();
//            String description = itemData.get("description").getAsString();
//            String position;
//            try {
//                position = itemData.get("position").getAsString();
//            } catch (NullPointerException ex) {
//            position = "none";
//            }
//            JsonObject sProps = itemData.get("properties").getAsJsonObject();
//            HashMap<String, Integer> properties = new HashMap<String, Integer>();
//            for (Map.Entry<String,JsonElement> entry2 : sProps.entrySet()) {
//                Integer propValue = entry2.getValue().getAsInt();
//                properties.put(entry2.getKey(), propValue);
//            }
//            itemDetails.put("name", name);
//            itemDetails.put("description", description);
//            itemDetails.put("properties", properties);
//            itemDetails.put("position", position);
//            itemList.put(rawItemID,itemDetails);
//        }
//        for (Map.Entry<String, HashMap> item : itemList.entrySet()) {
//            if (item.getKey().equals(itemID)) {
//                this.id = item.getKey();
//                this.name = item.getValue().get("name").toString();
//                this.description = item.getValue().get("description").toString();
//                this.position = item.getValue().get("position").toString();
//                this.properties = getHashMap(item.getValue().get("properties"));
//            }
//        }
//    }
    
    @SuppressWarnings("unchecked")
    private HashMap<String, Integer> getHashMap(Object obj) {
        return (HashMap<String, Integer>) obj;
    }

}
