package com.jadventure.game.conversation;

import com.jadventure.game.GameBeans;
import com.jadventure.game.entities.NPC;
import com.jadventure.game.entities.Player;
import com.jadventure.game.items.Item;
import com.jadventure.game.repository.EncounteredNpcRepository;
import com.jadventure.game.repository.ItemRepository;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.DeathException;
import com.jadventure.game.Trading;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jadventure.game.repository.NpcRepository;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConversationManager {
    private static NpcRepository npcRepository = EncounteredNpcRepository.createRepo();
    private static ConversationManager instance = null;
    private Map<NPC, List<Line>> lines = new HashMap<NPC, List<Line>>();
    private static final Map<String, ActionType> ACTION_TYPE_MAP = new HashMap<>();
    private static final Map<String, ConditionType> CONDITION_TYPE_MAP = new HashMap<>();

    static {
        ACTION_TYPE_MAP.put("none", ActionType.NONE);
        ACTION_TYPE_MAP.put("attack", ActionType.ATTACK);
        ACTION_TYPE_MAP.put("buy", ActionType.BUY);
        ACTION_TYPE_MAP.put("sell", ActionType.SELL);
        ACTION_TYPE_MAP.put("trade", ActionType.TRADE);
        ACTION_TYPE_MAP.put("give", ActionType.GIVE);
        ACTION_TYPE_MAP.put("take", ActionType.TAKE);
        CONDITION_TYPE_MAP.put("none", ConditionType.NONE);
        CONDITION_TYPE_MAP.put("ally", ConditionType.ALLY);
        CONDITION_TYPE_MAP.put("enemy", ConditionType.ENEMY);
        CONDITION_TYPE_MAP.put("level", ConditionType.LEVEL);
        CONDITION_TYPE_MAP.put("item", ConditionType.ITEM);
        CONDITION_TYPE_MAP.put("char type", ConditionType.CHAR_TYPE);
    }

    public ConversationManager() {
       load(); 
    } 

    public static ConversationManager getInstance() {
        if (instance == null) {
            instance = new ConversationManager();
        }
        return instance;
    }

    private void load() {
        String fileName = "json/original_data/npcs.json";
        JsonParser parser = new JsonParser();
        try {
            Reader reader = new FileReader(fileName);
            JsonObject json = parser.parse(reader).getAsJsonObject();
            json = json.get("npcs").getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entries = json.entrySet();
            for (Map.Entry<String, JsonElement> entry : entries) {
                NPC npc = npcRepository.getNpc(entry.getKey());
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
            start.add(getLine(i++, conversation));
        }
        lines.put(npc, start);
    }

    private Line getLine(int index, JsonArray conversation) {
        JsonObject line = conversation.get(index).getAsJsonObject();
        List<Integer> responses = new ArrayList<>();
        if (line.get("response") != null) {
            for (JsonElement i : line.get("response").getAsJsonArray()) {
                responses.add(i.getAsInt());
            }
        }
        String playerPrompt = line.get("player").getAsString();
        String text = line.get("text").getAsString();
        String[] con = line.get("condition").getAsString().split("=");
        ConditionType condition = CONDITION_TYPE_MAP.get(con[0]);
        String conditionParameter = (con.length == 1) ? "" : con[1];
        ActionType action = ACTION_TYPE_MAP.get(line.get("action").getAsString());
        return new Line(index, playerPrompt, text, condition, conditionParameter, responses, action);
    }

    public void startConversation(NPC npc, Player player) throws DeathException {
        List<Line> conversation = null;
        //Workaround as <code>lines.get(npc)</code> is not working.
        Iterator it = lines.entrySet().iterator();
        while (it.hasNext()) {
            @SuppressWarnings("unchecked")
            Map.Entry<NPC, List<Line>> entry = (Map.Entry<NPC, List<Line>>) it.next();
            if (entry.getKey().equals(npc)) {
                conversation = entry.getValue();
            }
            it.remove();
        }
        if (conversation != null) {
            Line start = null;
            for (Line l : conversation) {
                if ("".equals(l.getPlayerPrompt()) && 
                            ConversationManager.matchesConditions(npc, player, l)) {
                    start = l;
                    break;
                }
            }
            if (start != null) {
                QueueProvider.offer(start.getText());
                Line response = start.display(npc, player, conversation);
                triggerAction(start, npc, player);
                while (response != null) {
                   QueueProvider.offer(response.getText());
                   triggerAction(response, npc, player);
                   Line temp_response = response.display(npc, player, conversation);
                   response = temp_response;
               }
            }
        }
    }

    private void triggerAction(Line line, NPC npc, Player player) throws DeathException {
        switch (line.getAction()) {
            case ATTACK:
                QueueProvider.offer("\n" + npc.getName() + " is now attacking you!\n");
                player.attack(npc.getName());
                break;
            case TRADE:
                Trading t = new Trading(npc, player);
                t.trade(true, true);
                break;
        }     
    }

    public static boolean matchesConditions(NPC npc, Player player, Line line) {
        switch(line.getCondition()) {
            case ALLY:
                return npc.getAllies().contains(player.getCurrentCharacterType());
            case ENEMY:
                return npc.getEnemies().contains(player.getCurrentCharacterType());
            case LEVEL:
                int requiredLevel = Integer.parseInt(line.getConditionParameter());
                return player.getLevel() >= requiredLevel;
            case ITEM:
                ItemRepository itemRepo = GameBeans.getItemRepository();
                Item requiredItem = itemRepo.getItem(line.getConditionParameter());
                return player.hasItem(requiredItem);
            case CHAR_TYPE:
                String charType = line.getConditionParameter();
                return charType.equals(player.getCurrentCharacterType());
            default: // No condition
                return true;
        }
    }
}
