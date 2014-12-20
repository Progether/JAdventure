package com.jadventure.game;

import com.jadventure.game.entities.Player;
import com.jadventure.game.QueueProvider;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

import java.io.FileReader;
import java.io.Reader;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class CharacterChange {
    public void trigger(Player player, String triggerType, String keyword) {
        JsonParser parser = new JsonParser();
        String fileName = "json/character_transitions.json";
        try {
            Reader reader = new FileReader(fileName);
            JsonObject json = parser.parse(reader).getAsJsonObject();

            String currentCharacter = player.getCurrentCharacterType();

            JsonObject currentCharacterTransitions;
            JsonObject events;
            JsonObject characterEffects = new JsonObject();
            if (json.has(currentCharacter)) {
                currentCharacterTransitions = json.get(currentCharacter).getAsJsonObject();
                if (currentCharacterTransitions.has(triggerType)) {
                    events = currentCharacterTransitions.get(triggerType).getAsJsonObject();
                    if (events.has(keyword)) {
                        characterEffects = events.get(keyword).getAsJsonObject();
                    } else {
                        QueueProvider.offer("Error: The effects for this event was not found");
                        System.exit(1);
                    }
                } else {
                    QueueProvider.offer("Error: This event was not found");
                    System.exit(1);
                }
            } else {
                QueueProvider.offer("Error: This character was not found");
                System.exit(1);
            }

            for (Map.Entry<String, JsonElement> entry : characterEffects.entrySet()) {
                String characterName = entry.getKey();
                int characterLevelEffect = entry.getValue().getAsInt();
                int characterLevel = player.getCharacterLevel(characterName); 
                int newCharacterLevel = characterLevel + characterLevelEffect;
                player.setCharacterLevel(characterName, newCharacterLevel);
                checkForCharacterChange(player);
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
