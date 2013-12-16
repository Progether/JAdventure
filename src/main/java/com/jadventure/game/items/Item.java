package com.jadventure.game.items;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Item {
    private String name;
    private String itemID;

    public Item(String itemID) {
        HashMap<String,String> itemData = lookUpItem(itemID);
        this.name = itemData.get("name");
        this.itemID = itemID;
    }
    
    public String getName() {
        return this.name;
    }

    public String getItemID() {
        return this.itemID;
    }

    public HashMap<String,String> lookUpItem(String itemID) {
        HashMap<String,HashMap> items = loadItems();
        for (Map.Entry<String,HashMap> entry : items.entrySet()) {
            if (entry.getKey().equals(itemID)) {
                return entry.getValue();
            }
        }
        return new HashMap();
    }

    public HashMap<String,HashMap> loadItems() {
        String fileName = "json/items.json";
        HashMap<String,HashMap> hashMap = new HashMap();
        try {
            Reader reader = new FileReader(fileName);
            Type typeHashMap = new TypeToken<HashMap<String,HashMap<String,HashMap>>>() { }.getType();
            Gson gson = new GsonBuilder().create();
            HashMap<String,HashMap> outerHashMap = gson.fromJson(reader, typeHashMap);
            hashMap = outerHashMap.get("items");
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println( "Unable to open file '" + fileName + "'.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return hashMap;
    }

}
