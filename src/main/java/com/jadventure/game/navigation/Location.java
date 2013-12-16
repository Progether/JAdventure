package com.jadventure.game.navigation;

import com.jadventure.game.items.Item;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

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
    private ArrayList<String> items;

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

    public void setItems(ArrayList items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<Item>();
        for (String itemId : this.items) {
            Item itemName = new Item(itemId);
            items.add(itemName);
        }
        return items;
    }

    public void removePublicItem(String itemID) {
        ArrayList<String> items = this.items;
        items.remove(itemID);
        setItems(items);
    }

    public void print() {
        System.out.println(getTitle() + ":");
        System.out.println(getDescription());
        ArrayList<Item> publicItems = getItems();
        if (!publicItems.isEmpty()) {
            System.out.println("Items:");
            for (Item item : publicItems) {
                System.out.println("    "+item.getName());
            }
        }
        System.out.println();
        for (Map.Entry<Direction,ILocation> direction : getExits().entrySet()) {
            System.out.print(direction.getKey().getDescription() + ", ");
            System.out.println(direction.getValue().getDescription());
        }
        System.out.println();
    }
}
