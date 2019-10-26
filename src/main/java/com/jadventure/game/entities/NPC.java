package com.jadventure.game.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


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
    
    public NPC() {
        allies = new ArrayList<>();
        enemies = new ArrayList<>();
    }
    
    public NPC(String entityID) {
        allies = new ArrayList<>();
        enemies = new ArrayList<>();
        this.id = entityID;
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
    
    public void setAllies( List<String> allies ) {
        this.allies = allies;
    }
    
    public void setEnemies( List<String> enemies ) {
        this.enemies = enemies;
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
