package com.jadventure.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

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
        HashMap<String,HashMap> items = loadItems();
        for (Map.Entry<String,HashMap> entry : items.entrySet()) {
            if (entry.getKey().equals(Integer.toString(itemID))) {
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
