package com.jadventure.game.navigation;

import java.util.List;
import java.util.Map;

import com.jadventure.game.entities.NPC;
import com.jadventure.game.items.Item;
import com.jadventure.game.monsters.Monster;

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
    public List<Item> getItems();
    public List<NPC> getNPCs();
    public void removePublicItem(String itemID);
    public void addPublicItem(String itemID);
    public void setMonsters(Monster monster);
    public List<Monster> getMonsters();
    public void print();
}

