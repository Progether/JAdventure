package com.jadventure.game.navigation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Cage
 * Date: 23/11/13
 * Time: 10:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Location implements ILocation {
    private Coordinate coordinate;
    private String title;
    private String description;
    private LocationType locationType;

    public Location() {

    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public Map<Direction, ILocation> getExits() {
        Map<Direction, ILocation> exits = new HashMap<Direction, ILocation>();
        ILocation borderingLocation;
        for(Direction direction: Direction.values()) {
            borderingLocation = LocationManager.INSTANCE.getLocation(getCoordinate().getBorderingCoordinate(direction));
            if (borderingLocation != null) {
                exits.put(direction, borderingLocation);
            }
        }
        return exits;
    }

    public void print() {
        System.out.println(getTitle() + ":");
        System.out.println(getDescription());
        System.out.println();
        for (Map.Entry<Direction,ILocation> direction : getExits().entrySet()) {
            System.out.print(direction.getKey().getDescription() + ", ");
            System.out.println(direction.getValue().getDescription());
        }
        System.out.println();
    }
}
