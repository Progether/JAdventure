package com.jadventure.game.items;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.google.gson.internal.LinkedTreeMap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/*
 * This class deals with parsing and interacting with an item.
 */
public class Item {
    private String name;
    private String itemID;
    private String description;
    private String bodypart;
    public HashMap<String, Integer> properties;

    public Item(String itemID) {
        lookUpItem(itemID);
    }
    
    public String getName() {
        return this.name;
    }

    public String getItemID() {
        return this.itemID;
    }

    public String getBodypart(){
        return this.bodypart;
    }

    public double getWeight() {
        return this.properties.get("weight");
    }

    public boolean equals(Object obj) {
        if(obj instanceof Item) {
            Item i = (Item) obj;
            return this.name.equals(i.name);
        }
        return false;
    }

    public void display() {
        System.out.println("Name: " + this.name +
                "\nDescription: " + this.description);
        for (Map.Entry entry : this.properties.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // gets data about an item based on its itemID
    public void lookUpItem(String itemID) {
        String fileName = "json/items.json";
        JsonObject items = new JsonObject();
        try {
            Reader reader = new FileReader(fileName);
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(reader).getAsJsonObject();
            items = json.get("items").getAsJsonObject();
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println( "Unable to open file '" + fileName + "'.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        HashMap<String,HashMap> itemList = new HashMap();
        for (Map.Entry<String, JsonElement> entry : items.entrySet()) {
            HashMap itemDetails = new HashMap();
            String rawItemID = entry.getKey().toString();
            JsonObject itemData = entry.getValue().getAsJsonObject();
            String name = itemData.get("name").getAsString();
            String description = itemData.get("description").getAsString();
            String bodypart = itemData.get("bodypart").getAsString();
            //HashMap<String,Integer>
            JsonObject sProps = itemData.get("properties").getAsJsonObject();
            HashMap properties = new HashMap();
            for (Map.Entry<String,JsonElement> entry2 : sProps.entrySet()) {
                Integer propValue = entry2.getValue().getAsInt();
                properties.put(entry2.getKey(), propValue);
            }
            itemDetails.put("name", name);
            itemDetails.put("description", description);
            itemDetails.put("properties", properties);
            itemDetails.put("bodypart", bodypart);
            itemList.put(rawItemID,itemDetails);
        }
        for (Map.Entry<String, HashMap> item : itemList.entrySet()) {
            if (item.getKey().equals(itemID)) {
                this.itemID = item.getKey();
                this.name = item.getValue().get("name").toString();
                this.description = item.getValue().get("description").toString();
                this.bodypart = item.getValue().get("bodypart").toString();
                this.properties = (HashMap<String,Integer>)item.getValue().get("properties");
            }
        }

    }

}
