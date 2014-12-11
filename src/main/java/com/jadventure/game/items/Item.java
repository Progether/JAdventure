package com.jadventure.game.items;

import java.util.HashMap;
import java.util.Map;

import com.jadventure.game.QueueProvider;

/**
 * Items lay around in the game world and can be taken with you.
 */
public class Item {
	private final String id;
    private final String type;
    private final String name;
    private final String description;
    private final String position;
    private final Map<String, Integer> properties;

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
        for (Map.Entry<String, Integer> entry : this.properties.entrySet()) {
            QueueProvider.offer(entry.getKey() + ": " + entry.getValue());
        }
    }

}
