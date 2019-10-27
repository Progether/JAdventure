package com.jadventure.game.conversation;


import com.jadventure.game.entities.NPC;
import com.jadventure.game.entities.Player;
import com.jadventure.game.menus.Menus;
import com.jadventure.game.menus.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private int id;
    private String playerPrompt;
    private String text;
    private ConditionType condition;
    private String conditionParameter;
    private List<Integer> responses;
    private ActionType action;

    public Line(int id, String playerPrompt, String text, ConditionType condition, 
            String conditionParameter, List<Integer> responses, ActionType action) {
        this.id = id;
        this.playerPrompt = playerPrompt;
        this.text = text;
        this.condition = condition;
        this.conditionParameter = conditionParameter;
        this.responses = responses;
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getPlayerPrompt() {
        return playerPrompt;
    }

    public ConditionType getCondition() {
        return condition;
    }

    public String getConditionParameter() {
        return conditionParameter;
    }

    public ActionType getAction() {
        return action;
    }

    public Line display(NPC npc, Player player, List<Line> lines) {
        if (responses.size() == 0) {
            return null;
        }
        List<MenuItem> responseList = new ArrayList<>();
        for (Integer responseNum : responses) { 
            Line response = lines.get(responseNum);
            if (ConversationManager.matchesConditions(npc, player, response)) {
                responseList.add(new MenuItem(response.getPlayerPrompt(), null));
            }
        }
        Menus responseMenu = new Menus();
        MenuItem response = responseMenu.displayMenu(responseList);
        for (int responseNum : responses) {
            Line possibleResponse = lines.get(responseNum);
            if (possibleResponse.getPlayerPrompt().equals(response.getCommand())) {
                return possibleResponse;
            }
        }
        return null;
    }
}
