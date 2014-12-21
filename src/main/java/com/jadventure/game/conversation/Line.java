package com.jadventure.game.conversation;

import com.jadventure.game.QueueProvider;
import com.jadventure.game.menus.Menus;
import com.jadventure.game.menus.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private int id;
    private String playerPrompt;
    private String text;
    private String condition;
    private List<Line> responses;
    private ActionType action;

    public Line(int id, String playerPrompt, String text, String condition, List<Line> responses, ActionType action) {
        this.id = id;
        this.playerPrompt = playerPrompt;
        this.text = text;
        this.condition = condition;
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

    public String getCondition() {
        return condition;
    }

    public ActionType getAction() {
        return action;
    }

    public Line display() {
        QueueProvider.offer("\n" + text + "\n");
        if (responses.size() == 0) {
            return null;
        }
        List<MenuItem> responseList = new ArrayList<>();
        for (Line response : responses) {
            responseList.add(new MenuItem(response.getPlayerPrompt(), null));
        }
        Menus responseMenu = new Menus();
        MenuItem response = responseMenu.displayMenu(responseList);
        for (Line possibleResponse : responses) {
            if (possibleResponse.getPlayerPrompt().equals(response.getCommand())) {
                return possibleResponse;
            }
        }
        return null;
    }
}
