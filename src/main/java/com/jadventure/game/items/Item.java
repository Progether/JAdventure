package com.jadventure.game.items;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import com.jadventure.game.QueueProvider;
import com.jadventure.game.entities.EquipmentLocation;

/**
 * Items lay around in the game world and can be taken with you.
 */
public class Item {
	private final String id;
    private final String type;
    private final String name;
    private final String description;
    private final EquipmentLocation position;
    private final int level;
    private final Map<String, Integer> properties;

    public Item(String id, String type, String name, String description, int level, Map<String, Integer> properties) {
        this(id, type, name, description, null, level, properties);
    }
    public Item(String id, String type, String name, String description, EquipmentLocation position, int level, Map<String, Integer> properties) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.position = position;
        this.level = level;
        if (properties != null) {
            this.properties = properties;
        }
        else {
            this.properties = new TreeMap<>();
        }
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
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

    public EquipmentLocation getPosition() {
    	return position;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public int getProperty(String property) {
        if (! properties.containsKey(property)) {
            return 0;
        }

        return properties.get(property);
    }

    public Map<String, Integer> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    public boolean containsProperty(String key) {
        return properties.containsKey(key);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Item) {
            Item i = (Item) obj;
            return name.equals(i.name);
        }
        return false;
    }

    public void display() {
        QueueProvider.offer("Name: " + name +
                "\nDescription: " + description + "\nLevel: " + level);
        for (Map.Entry<String, Integer> entry : properties.entrySet()) {
            QueueProvider.offer(entry.getKey() + ": " + entry.getValue());
        }
    }

}
