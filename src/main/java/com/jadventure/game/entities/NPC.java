package com.jadventure.game.entities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.jadventure.game.QueueProvider;
import com.jadventure.game.items.Storage;

/**
 * This class deals with Non Player Character (NPC) and all of their properties.
 * Any method that changes a NPC or a player  interacts with it should
 * be placed within this class. If a method deals with entities in general or
 * with variables not unique to the NPC, place it in the entity class.
 */
public class NPC extends Entity {
    private int xpGain;
    private String id;
    private List<String> allies;
    private List<String> enemies;
    
    public NPC(String entityID) {
        this.id = entityID;
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
            setStrength(json.get("strength").getAsInt());
            if (json.has("gold")) {
                setGold(json.get("gold").getAsInt());
            }
            float maxWeight = (float)Math.sqrt(getStrength()*300);
            setStorage(new Storage(maxWeight));
            if (json.has("sellLimit") && json.has("items")) {
                int itemLimit = json.get("sellLimit").getAsInt();
                int i = 0;
                setItems(json, itemLimit, i);
            }
            allies = new ArrayList<>();
            JsonArray alliesJson = json.get("allies").getAsJsonArray();
            for (JsonElement ally : alliesJson) {
                allies.add(ally.getAsString());
            }
            enemies = new ArrayList<>();
            JsonArray enemiesJson = json.get("enemies").getAsJsonArray();
            for (JsonElement enemy : enemiesJson) {
                enemies.add(enemy.getAsString());
            }
        } catch (FileNotFoundException ex) {
            QueueProvider.offer("Unable to open file '" + fileName + "'.");
        }
    }

    public void setItems(JsonObject json, int itemLimit, int i) {
        JsonArray items = json.get("items").getAsJsonArray();
        JsonArray itemTypes = json.get("tradingEmphasis").getAsJsonArray();
        boolean cont;
        for (JsonElement item : items) {
            if (i == itemLimit) {
                break;
            }

            cont = false;
            char itemType = item.getAsString().charAt(0);
            for (JsonElement type : itemTypes) {
                if (itemType == type.getAsString().charAt(0)) {
                    cont = true;
                }
            }

            Random rand = new Random();
            int j = rand.nextInt(100) + 1;
            if (cont) {
                if ((j > 0) && (j <= 95)) {
                    addItemToStorage(itemRepo.getItem(item.getAsString()));
                    i++;
                }
            } else {
                if ((j > 95) && (j <= 100)) {
                    addItemToStorage(itemRepo.getItem(item.getAsString()));
                    i++;
                }
            }
        }
        if (i != itemLimit) {
            setItems(json, itemLimit, i);
        }
    }

    public List<String> getAllies() {
        return allies;
    }

    public List<String> getEnemies() {
        return enemies;
    }

    public int getXPGain() {
        return xpGain;
    }

    public void setXPGain(int xpGain) {
        this.xpGain = xpGain;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof NPC) {
            NPC npc = (NPC) obj;
            return npc.getId().equals(id);
        }
        return false;
    }
}
