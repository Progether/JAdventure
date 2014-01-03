package com.jadventure.game.navigation;

import java.util.Map;
import java.util.ArrayList;

/**
 * This interface maps all the properties and methods that 
 * pertain to a specific location.
 */
public interface ILocation {
    public Coordinate getCoordinate();
    public String getTitle();
    public String getDescription();
    public LocationType getLocationType();
    public Map<Direction, ILocation> getExits();
    public ArrayList<String> getItems();
    public void print();
}
