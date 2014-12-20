package com.jadventure.game.conversation;

import com.jadventure.game.entities.NPC;
import com.jadventure.game.QueueProvider;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversationManager {
    private static ConversationManager instance = null;

    private static class Lines {
        private static Map<NPC, List<Line>> lines = new HashMap<NPC, List<Line>>();
    }
    
    private ConversationManager() {
       load(); 
    } 

    public static ConversationManager getInstance() {
        if (instance == null) {
            instance = new ConversationManager();
        }
        return instance;
    }

    private void load() {
        String fileName = "json/npcs.json";
        JsonParser parser = new JsonParser();
        File f = new File(fileName);
        try {
            Reader reader = new FileReader(fileName);
            JsonObject json = parser.parse(reader).getAsJsonObject();
            QueueProvider.offer(json.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
