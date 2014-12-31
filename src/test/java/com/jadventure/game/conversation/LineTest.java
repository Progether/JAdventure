package com.jadventure.game.conversation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LineTest {
    @Test
    public void createTest() {
        Line line = new Line(1, "Who are you?", "I am your guide.", ConditionType.NONE, "", null, ActionType.NONE);
        assertEquals(1, line.getId());
        assertEquals("Who are you?", line.getPlayerPrompt());
        assertEquals("I am your guide.", line.getText());
        assertEquals(ConditionType.NONE, line.getCondition());
        assertEquals(ActionType.NONE, line.getAction());
    }
}
