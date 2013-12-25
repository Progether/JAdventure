package com.jadventure.game.navigation;

import java.util.Map;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Cage
 * Date: 23/11/13
 * Time: 10:56 PM
 * To change this template use File | Settings | File Templates.
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
