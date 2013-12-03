package com.jadventure.game.navigation;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Cage
 * Date: 23/11/13
 * Time: 11:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationProxy implements ILocation {
    private final Coordinate coordinate;

    public LocationProxy(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public String getTitle() {
        return LocationManager.INSTANCE.getLocation(coordinate).getTitle();
    }

    @Override
    public String getDescription() {
        return LocationManager.INSTANCE.getLocation(coordinate).getDescription();
    }

    @Override
    public LocationType getLocationType() {
        return LocationManager.INSTANCE.getLocation(coordinate).getLocationType();
    }

    @Override
    public Map<Direction, Exit> getExits() {
        return LocationManager.INSTANCE.getLocation(coordinate).getExits();
    }

    @Override
    public void print() {
        LocationManager.INSTANCE.getLocation(coordinate).print();
    }
}
