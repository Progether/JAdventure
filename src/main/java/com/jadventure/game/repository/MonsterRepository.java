package com.jadventure.game.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.jadventure.game.GameBeans;
import com.jadventure.game.items.Storage;
import com.jadventure.game.monsters.Monster;

public class MonsterRepository {
	private Map<String, Monster> monsters = new HashMap<>();
	
	
	public void add(Monster monster) {
		monsters.put(monster.getId(), monster);
	}
	
	public Monster getMonster(String id) {
		if (! monsters.containsKey(id)) {
			throw new RuntimeException("There is no monster with id " + id);
		}
		return monsters.get(id);
	}
	
	public void load(JsonReader reader) {
        JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(reader).getAsJsonObject();
        JsonObject jsonCharacters = json.get("monsters").getAsJsonObject();
        
        for (Map.Entry<String, JsonElement> entry : jsonCharacters.entrySet()) {
        	MonsterBuilder monsterBldr = new MonsterBuilder();
        	monsterBldr.setId(entry.getKey());
            JsonObject jsonMonster = entry.getValue().getAsJsonObject();
            monsterBldr.setName(jsonMonster.get("name").getAsString());
            monsterBldr.setDescription(jsonMonster.get("description").getAsString());

            System.out.println("Loading monster " + monsterBldr.getId());
            
            monsterBldr.setHealth(jsonMonster.get("health").getAsInt());
            monsterBldr.setHealthMax(jsonMonster.get("healthMax").getAsInt());
            monsterBldr.setDamage(jsonMonster.get("damage").getAsDouble());
            monsterBldr.setArmour(jsonMonster.get("armour").getAsInt());
            String weaponStr = jsonMonster.get("weapon").getAsString();
            monsterBldr.setWeapon(GameBeans.getItemRepository().getItem(weaponStr));
            Storage storage = monsterBldr.getStorage();

            Map<String, Integer> monsterItems = new Gson().fromJson(jsonMonster.get("items"), 
            		new TypeToken<Map<String, Integer>>(){}.getType());
            if (monsterItems != null) {
	            for (Entry<String, Integer> itemEntry : monsterItems.entrySet()) {
	            	for (int index = 0; index < itemEntry.getValue(); index++) {
	            		storage.add(GameBeans.getItemRepository().getItem(itemEntry.getKey()));
	            	}
	            }
            }

            Monster monster = monsterBldr.create();
            System.out.println("monster " + monster.getName() + "  " + monster.getId() 
            		+ "  " + monster.getType());
			add(monster);
            
            
//            JsonObject jsonProps = jsonCharacter.get("properties").getAsJsonObject();
//            Map<String, Object> properties = new HashMap<>();
//            for (Map.Entry<String, JsonElement> propEntry : jsonProps.entrySet()) {
//            	String  propKey = propEntry.getKey();
//                Integer propValue = propEntry.getValue().getAsInt();
//                properties.
//            }
            
        }
	}
	
	public void save(JsonWriter writer) {
		
	}

	private static MonsterRepository monsterRepo = null;
	public static MonsterRepository createRepo() {
        if (monsterRepo == null) {
            File file = new File(new File(System.getProperty("user.dir")), "json");
            File dataFile = new File(new File(file, "original_data"), "monsters.json");
            if (! dataFile.exists()) {
            	throw new RuntimeException("File '" + dataFile + "' does not exist.");
            }
            
            JsonReader jsonReader;
			try {
				jsonReader = new JsonReader(new FileReader(dataFile));
				monsterRepo = new MonsterRepository();
				
				monsterRepo.load(jsonReader);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return monsterRepo;
	}

}
