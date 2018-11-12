package com.jadventure.game.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.jadventure.game.entities.NPC;
import com.jadventure.game.items.Storage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** NpcRepository is the repository for all npcs 
 * instead of new Npc(npcId) use 
 * NpcRepository npcRepository = NpcRepository.createRepo();
 * npcRepository.getNPC(npcId)
 */
public class NpcRepository {
    private Map<String,NPC> npcMap = new HashMap<String,NPC>();
    static String fileName = "json/original_data/npcs.json";
    
    // Load all items, from the given file
    protected void load(File repo) {
        try {
			JsonReader reader = new JsonReader(new FileReader(repo));
			load(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    // Load all items, from the given file
    protected void load(JsonReader reader) {
        JsonObject jsonNPCs = new JsonObject();
        try {
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(reader).getAsJsonObject();
            jsonNPCs = json.get("npcs").getAsJsonObject();
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        for (Map.Entry<String, JsonElement> entry : jsonNPCs.entrySet()) {
            String id = entry.getKey();
            JsonObject npcData = entry.getValue().getAsJsonObject();
            String name = npcData.get("name").getAsString();
            int health =  npcData.get("health").getAsInt();
            int damage = npcData.get("damage").getAsInt();
            int armour = npcData.get("armour").getAsInt();
            int healthMax = npcData.get("healthMax").getAsInt();
            int level = npcData.get("level").getAsInt();
            int intelligence = npcData.get("intelligence").getAsInt();
            int dexterity = npcData.get("dexterity").getAsInt();
            int stealth = npcData.get("stealth").getAsInt();
            int strength = npcData.get("strength").getAsInt();
            int xpGain = npcData.get("xp").getAsInt();
            int gold = 0;
            if (npcData.has("gold")) {
                gold = npcData.get("gold").getAsInt();
            }
            // construct npc
            NPC npc = new NPC(id);
            npc.setName(name);
            npc.setHealth(health);
            npc.setDamage(damage);
            npc.setArmour(armour);
            npc.setHealthMax(healthMax);
            npc.setLevel(level);
            npc.setIntelligence(intelligence);
            npc.setDexterity(dexterity);
            npc.setStealth(stealth);
            npc.setStrength(strength);
            npc.setGold(gold);
            npc.setXPGain(xpGain);
            float maxWeight = (float)Math.sqrt(strength*300);
            npc.setStorage( new Storage(maxWeight) );
            if (npcData.has("sellLimit") && npcData.has("items")) {
                int itemLimit = npcData.get("sellLimit").getAsInt();
                int i = 0;
                npc.setItems(npcData, itemLimit, i);
            }
            JsonArray alliesJson = npcData.get("allies").getAsJsonArray();
            List<String> allies = new ArrayList<>();
            for (JsonElement ally : alliesJson) {
                allies.add(ally.getAsString());
            }
            npc.setAllies(allies);
            List<String> enemies = new ArrayList<>();
            JsonArray enemiesJson = npcData.get("enemies").getAsJsonArray();
            for (JsonElement enemy : enemiesJson) {
                enemies.add(enemy.getAsString());
            }
            npc.setEnemies(enemies);
            npcMap.put(id,npc);
        }
    }
    
    private static NpcRepository npcRepository = null;
    public static NpcRepository createRepo() {
        if ( npcRepository == null) {
            File dataFile = new File(fileName);
            if (! dataFile.exists()) {
            	throw new RuntimeException("File '" + dataFile + "' does not exist.");
            }

            npcRepository = new NpcRepository();
            npcRepository.load(dataFile);
        }
        return npcRepository;
    }
    
    public NPC getNpc(String npcId) {
        if (npcId == null || npcId.trim().length() == 0) {
            return null;
        }
        if (!npcMap.containsKey(npcId)) {
            throw new RepositoryException("Argument 'npcId' with value '" + npcId + "' not found in repository.");
        }
        return npcMap.get(npcId);
    }

}
