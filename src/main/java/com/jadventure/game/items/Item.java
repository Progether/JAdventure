package com.jadventure.game.items;

import java.util.Map;
import java.util.Map.Entry;

import com.jadventure.game.QueueProvider;

/**
 * This class deals with parsing and interacting with an item.
 */
public class Item {
    private String id;
    private String name;
    private String description;
    private Map<String, Integer> properties;

    public Item(String id, String name, String description, Map<String, Integer> properties) {
        this.id = id;
        this.name = name;
        this.properties = properties;
    }
    
    public String getName() {
        return name;
    }

    public String getItemID() {
        return id;
    }

    public double getWeight() {
        return properties.get("weight");
    }

    public Map<String, Integer> getProperties() {
        return properties;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Item) {
            Item item = (Item) obj;
            return name.equals(item.name);
        }
        return false;
    }

    public void display() {
        QueueProvider.offer("Name: " + this.name +
                "\nDescription: " + this.description);
        for (Entry<String, Integer> entry : this.properties.entrySet()) {
            QueueProvider.offer(entry.getKey() + ": " + entry.getValue());
        }
    }

}
