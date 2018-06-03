package com.jadventure.game;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jadventure.game.entities.Player;

public class CharacterChange {
    public void trigger(Player player, String triggerType, String keyword) {
        JsonParser parser = new JsonParser();
        String fileName = "json/original_data/character_transitions.json";
        try {
            Reader reader = new FileReader(fileName);
            JsonObject json = parser.parse(reader).getAsJsonObject();

            String currentCharacter = player.getCurrentCharacterType();

            JsonObject currentCharacterTransitions;
            JsonObject events;
            JsonObject characterEffects = new JsonObject();
            boolean goAhead = false;
            if (json.has(currentCharacter)) {
                currentCharacterTransitions = json.get(currentCharacter).getAsJsonObject();
                if (currentCharacterTransitions.has(triggerType)) {
                    events = currentCharacterTransitions.get(triggerType).getAsJsonObject();
                    if (events.has(keyword)) {
                        characterEffects = events.get(keyword).getAsJsonObject();
                        goAhead = true;
                    } else {
                        //QueueProvider.offer("Warning: The effects for the '" + triggerType + "' event and the '" + currentCharacter + "' character was not found");
                    }
                } else {
                    //QueueProvider.offer("Warning: The event '" + triggerType + "' for the '" + currentCharacter + "' character was not found");
                }
            } else {
                //QueueProvider.offer("Warning: The character '" + currentCharacter + "' was not found");
            }

            if (goAhead == true) {
                for (Map.Entry<String, JsonElement> entry : characterEffects.entrySet()) {
                    String characterName = entry.getKey();
                    int characterLevelEffect = entry.getValue().getAsInt();
                    int characterLevel = player.getCharacterLevel(characterName); 
                    int newCharacterLevel = characterLevel + characterLevelEffect;
                    player.setCharacterLevel(characterName, newCharacterLevel);
                    checkForCharacterChange(player);
                }
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void checkForCharacterChange(Player player) {
        HashMap<String, Integer> characterLevels = player.getCharacterLevels();
        String currentCharacter = player.getCurrentCharacterType();
        int highestCharacterLevel = player.getCharacterLevel(currentCharacter);
        String highestCharacter = currentCharacter;
        Iterator it = characterLevels.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            int value = (int)pairs.getValue();
            if (value > highestCharacterLevel) {
                highestCharacterLevel = value;
                highestCharacter = (String)pairs.getKey();
            }
        }
        if (!highestCharacter.equals(currentCharacter)) {
            player.setCurrentCharacterType(highestCharacter);
            QueueProvider.offer("You're character type is now changed! You are now a " + highestCharacter + "!");
        }
        it = characterLevels.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            player.setCharacterLevel((String)pairs.getKey(), (int)pairs.getValue());
        }
    }

}
