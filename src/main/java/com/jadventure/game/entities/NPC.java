package com.jadventure.game.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import com.jadventure.game.QueueProvider;

/**
 * This class deals with Non Player Character (NPC) and all of their properties.
 * Any method that changes a NPC or a player  interacts with it should
 * be placed within this class. If a method deals with entities in general or
 * with variables not unique to the NPC, place it in the entity class.
 */
public class NPC extends Entity {
    private int xpGain;
    
    public NPC(String entityID) {
        JsonParser parser = new JsonParser();
        String fileName = "json/npcs.json";

        try {
            Reader reader = new FileReader(fileName);
            JsonObject npcs = parser.parse(reader).getAsJsonObject().get("npcs").getAsJsonObject();
            JsonObject json = new JsonObject();
            for (Map.Entry<String, JsonElement> entry : npcs.entrySet()) {
                if (entry.getKey().equals(entityID)) {
                    json = entry.getValue().getAsJsonObject();
                }
            }
            setName(json.get("name").getAsString());
            setHealth(json.get("health").getAsInt());
            setDamage(json.get("damage").getAsInt());
            setArmour(json.get("armour").getAsInt());
            setHealthMax(json.get("healthMax").getAsInt());
            setLevel(json.get("level").getAsInt());
            setStrength(json.get("strength").getAsInt());
            setIntelligence(json.get("intelligence").getAsInt());
            setDexterity(json.get("dexterity").getAsInt());
            setStealth(json.get("stealth").getAsInt());
        } catch (FileNotFoundException ex) {
            QueueProvider.offer("Unable to open file '" + fileName + "'.");
        }
    }

    public int getXPGain() {
        return xpGain;
    }

    public void setXPGain(int xpGain) {
        this.xpGain = xpGain;
    }
}
