package com.jadventure.game.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.jadventure.game.GameBeans;
import com.jadventure.game.entities.Player;

public class CharacterRepository {
	private Map<String, Player> characters = new HashMap<String, Player>();
	
	
	public void add(Player player) {
		characters.put(player.getId(), player);
	}
	
	public Player getCharacter(String characterType) {
		if (! characters.containsKey(characterType)) {
			throw new RuntimeException("There is no character of type " + characterType);
		}
		return characters.get(characterType);
	}
	
	public void load(JsonReader reader) {
        JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(reader).getAsJsonObject();
        JsonObject jsonCharacters = json.get("characters").getAsJsonObject();
        
        for (Map.Entry<String, JsonElement> entry : jsonCharacters.entrySet()) {
            String characterId = entry.getKey();
            JsonObject jsonCharacter = entry.getValue().getAsJsonObject();
            String name = jsonCharacter.get("name").getAsString();
            String description = jsonCharacter.get("description").getAsString();

            System.out.println("Loading character " + characterId);
            
            int health = jsonCharacter.get("health").getAsInt();
            int healthMax = jsonCharacter.get("healthMax").getAsInt();
            double damage = jsonCharacter.get("damage").getAsDouble();
            int armour = jsonCharacter.get("armour").getAsInt();
            int level = jsonCharacter.get("level").getAsInt();
            int strength = jsonCharacter.get("strength").getAsInt();
            int intelligence = jsonCharacter.get("intelligence").getAsInt();
            int dexterity = jsonCharacter.get("dexterity").getAsInt();
            int stealth = jsonCharacter.get("stealth").getAsInt();
            String weapon = jsonCharacter.get("weapon").getAsString();
            int luck = jsonCharacter.get("luck").getAsInt();

            List<String> introTextParts = new Gson().fromJson(jsonCharacter.get("intro-text"), 
            		new TypeToken<List<String>>(){}.getType());
            String introductionText = null;
            if (introTextParts != null) {
	            StringBuilder strBldr = new StringBuilder();
	            for (String introTextPart : introTextParts) {
	            	strBldr.append(introTextPart);
	            }
	            introductionText = strBldr.toString();
	            System.out.println("  intro text: " + introductionText);
            }

            CharacterBuilder charBldr = new CharacterBuilder();
            charBldr.setId(characterId);
            charBldr.setName(name);
            charBldr.setDescription(description);
            charBldr.setHealth(health);
            charBldr.setHealthMax(healthMax);
            charBldr.setDamage(damage);
            charBldr.setArmour(armour);
            charBldr.setLevel(level);
            charBldr.setStrength(strength);
            charBldr.setIntelligence(intelligence);
            charBldr.setDexterity(dexterity);
            charBldr.setStealth(stealth);
            charBldr.setWeapon(GameBeans.getItemRepository().getItem(weapon));
            charBldr.setLuck(luck);
            charBldr.setIntroduction(introductionText);
            Player character = charBldr.create();
            System.out.println("character " + character.getName() + "  " + character.getId() 
            		+ "  " + character.getType());
			add(character);
            
            
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

	private static CharacterRepository characterRepo = null;
	public static CharacterRepository createRepo() {
        if (characterRepo == null) {
            File file = new File(new File(System.getProperty("user.dir")), "json");
            File dataFile = new File(new File(file, "original_data"), "characters.json");
            if (! dataFile.exists()) {
            	throw new RuntimeException("File '" + dataFile + "' does not exist.");
            }
            
            JsonReader jsonReader;
			try {
				jsonReader = new JsonReader(new FileReader(dataFile));
				characterRepo = new CharacterRepository();
				
				characterRepo.load(jsonReader);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return characterRepo;
	}

}
