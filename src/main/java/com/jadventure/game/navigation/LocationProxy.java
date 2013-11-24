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
    private final int id;

    public LocationProxy(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return LocationManager.INSTANCE.getLocation(id).getTitle();
    }

    @Override
    public String getDescription() {
        return LocationManager.INSTANCE.getLocation(id).getDescription();
    }

    @Override
    public LocationType getLocationType() {
        return LocationManager.INSTANCE.getLocation(id).getLocationType();
    }

    @Override
    public Map<Direction, Exit> getExits() {
        return LocationManager.INSTANCE.getLocation(id).getExits();
    }

    @Override
    public void print() {
        LocationManager.INSTANCE.getLocation(id).print();
    }
}
