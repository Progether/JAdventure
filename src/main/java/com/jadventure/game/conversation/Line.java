package com.jadventure.game.conversation;

import com.jadventure.game.QueueProvider;

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

    public String getText() {
        return text;
    }

    public String getPlayerPrompt() {
        return playerPrompt;
    }

    public void display() {
        QueueProvider.offer(text);
        QueueProvider.offer("\n");
        for (Line response : responses) {
            QueueProvider.offer(response.getPlayerPrompt());
        }
        QueueProvider.offer("What is your choice?");
    }
}
