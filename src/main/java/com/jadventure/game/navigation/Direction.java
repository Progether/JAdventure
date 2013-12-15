package com.jadventure.game.navigation;

/**
 * Created with IntelliJ IDEA.
 * User: Cage
 * Date: 23/11/13
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Direction {
    NORTH("To the North", 0, 1, 0),
    SOUTH("To the South", 0, -1, 0),
    EAST("To the East", 1, 0, 0),
    WEST("To the West", -1, 0, 0),
    DOWN("Heading down", 0, 0, -1),
    UP("Heading up", 0, 0, 1);

    private final String description;
    private final int dx;
    private final int dy;
    private final int dz;

    private Direction(String description, int dx, int dy, int dz) {
        this.description = description;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public String getDescription() {
        return description;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getDz() {
        return dz;
    }
}
