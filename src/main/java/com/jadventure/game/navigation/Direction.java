package com.jadventure.game.navigation;

/**
 * Created with IntelliJ IDEA.
 * User: Cage
 * Date: 23/11/13
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Direction {
    NORTH("To the North"),
    SOUTH("To the South"),
    EAST("To the East"),
    WEST("To the West"),
    DOWN("Heading down"),
    UP("Heading up");

    private final String description;

    private Direction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
