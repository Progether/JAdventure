package com.jadventure.game.conversation;

import com.jadventure.game.entities.NPC;
import com.jadventure.game.entities.Player;
import com.jadventure.game.QueueProvider;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConversationManager {
    private static ConversationManager instance = null;
    private Map<NPC, List<Line>> lines = new HashMap<NPC, List<Line>>();
    private static final Map<String, ActionType> ACTION_TYPE_MAP = new HashMap<>();

    static {
        ACTION_TYPE_MAP.put("no action", ActionType.NO_ACTION);
        ACTION_TYPE_MAP.put("attack", ActionType.ATTACK);
        ACTION_TYPE_MAP.put("buy", ActionType.BUY);
        ACTION_TYPE_MAP.put("sell", ActionType.SELL);
        ACTION_TYPE_MAP.put("give", ActionType.GIVE);
        ACTION_TYPE_MAP.put("take", ActionType.TAKE);
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
            json = json.get("npcs").getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entries = json.entrySet();
            for (Map.Entry<String, JsonElement> entry : entries) {
                NPC npc = new NPC(entry.getKey());
                JsonObject details = entry.getValue().getAsJsonObject();
                if (details.get("conversations") != null) {
                    JsonArray conversation = details.get("conversations").getAsJsonArray();
                    addConversation(npc, conversation);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void addConversation(NPC npc, JsonArray conversation) {
        List<Line> start = new ArrayList<>();
        int i = 0;
        for (JsonElement entry : conversation) {
            JsonObject details = entry.getAsJsonObject();
            if ("".equals(details.get("player").getAsString())) {
                start.add(getLine(i++, conversation));
            }
        }
        lines.put(npc, start);
    }

    private Line getLine(int index, JsonArray conversation) {
        JsonObject line = conversation.get(index).getAsJsonObject();
        List<Line> responses = new ArrayList<>();
        if (line.get("response") != null) {
            for (JsonElement i : line.get("response").getAsJsonArray()) {
                responses.add(getLine(i.getAsInt(), conversation));
            }
        }
        String playerPrompt = line.get("player").getAsString();
        String text = line.get("text").getAsString();
        String condition = line.get("condition").getAsString();
        ActionType action = ACTION_TYPE_MAP.get(line.get("action").getAsString());
        return new Line(index, playerPrompt, text, condition, responses, action);
    }

    public void startConversation(NPC npc, Player player) {
    
    }
}
