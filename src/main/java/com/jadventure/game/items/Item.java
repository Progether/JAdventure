package com.jadventure.game.items;

import java.util.Map;

import com.jadventure.game.IGameElement;
import com.jadventure.game.IGameElementVisitor;

/**
 * This class deals with parsing and interacting with an item.
 */
public class Item implements IGameElement {
    private String id;
    private String name;
    private String description;
    private Map<String, Integer> properties;

    public Item(String id, String name, String description, Map<String, Integer> properties) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.properties = properties;
    }
    
    public String getId() {
    	return id;
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    
    public Integer getWeight() {
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

	@Override
	public void accept(IGameElementVisitor visitor) {
		visitor.visit(this);
	}

}
