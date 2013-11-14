package com.jadventure.game;

import java.util.HashMap;
import java.util.Map;

public class Item {
    private String name;

    public Item(int itemID) {
        HashMap<String,String> itemData = lookUpItem(itemID);
        this.name = itemData.get("name");
    }
    
    public String getName() {
        return this.name;
    }

    public HashMap<String,String> lookUpItem(int itemID) {
        final HashMap<String,String> milk = new HashMap()
        {{
             put("name", "milk");
             put("description", "some delicious milk");
             put("type", "food");
             put("healthIncrease", "5");
        }};
        HashMap<String,HashMap> items = new HashMap()
        {{
             put("1", milk);
        }};
        for (Map.Entry<String,HashMap> entry : items.entrySet()) {
            if (entry.getKey().equals(Integer.toString(itemID))) {
                return entry.getValue();
            }
        }
        return new HashMap();
    }

}
