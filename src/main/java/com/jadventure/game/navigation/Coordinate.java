package com.jadventure.game.navigation;

public class Coordinate {
    public String x;
    public String y;
    public String z;

    public Coordinate(String rawCoordinate) {
        String[] parts = rawCoordinate.split(",");
        this.x = parts[0];
        this.y = parts[1];
        this.z = parts[2];
    }

}
