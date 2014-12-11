package com.jadventure.game.navigation;

/**
 * This class takes two possible constructions: either a string or three integers.
 * Either way, it is parsed into a Coordinate.
 */
public class Coordinate {
    public final int x;
    public final int y;
    public final int z;

    /**
     * Create an 3D coordinate based on a String.
     *
     * @param rawCoordinate - A String containing three numbers, separated by a comma,
     * like {@code 'x, y, z'}
     */
    public Coordinate(String rawCoordinate) {
        String[] parts = rawCoordinate.split(",");
        this.x = Integer.parseInt(parts[0]);
        this.y = Integer.parseInt(parts[1]);
        this.z = Integer.parseInt(parts[2]);
    }

    /**
     * Create an 3D coordinate based on a String.
     * 
     * @param x - The X position
     * @param y - The Y position
     * @param z - The Z position
     */
    public Coordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinate getBorderingCoordinate(Direction direction) {
        return new Coordinate(x + direction.getDx(), y + direction.getDy(), z + direction.getDz());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    } 

    public int getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        if (z != that.z) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }
}
