package com.jadventure.game.navigation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoordinateTest {

    @Test
    public void testRawCoordinate() {
        Coordinate coordinate = new Coordinate("1,1,0");
        assertEquals(1, coordinate.getX());
        assertEquals(1, coordinate.getY());
        assertEquals(0, coordinate.getZ());
    }

    @Test
    public void testBorderingCoordinateNorth() {
        Coordinate coordinate = new Coordinate("1,1,0");
        Coordinate borderingCoordinate = coordinate.getBorderingCoordinate(Direction.NORTH);
        assertEquals(1, borderingCoordinate.getX());
        assertEquals(2, borderingCoordinate.getY());
        assertEquals(0, borderingCoordinate.getZ());
    }
}
