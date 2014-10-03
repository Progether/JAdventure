package com.jadventure.game.navigation;

import com.jadventure.game.items.Item;
import com.jadventure.game.items.ItemStack;
import com.jadventure.game.entities.NPC;
import com.jadventure.game.monsters.Monster;

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
    public ArrayList<Item> getItems();
    public ArrayList<NPC> getNPCs();
    public void removePublicItem(String itemID);
    public void addPublicItem(String itemID);
    public void addPublicItems(ArrayList<ItemStack> items);
    public void addMonster(Monster monster);
    public void removeMonster(Monster monster);
    public ArrayList<Monster> getMonsters();
    public void print();
    public int getDangerRating();
    public void setDangerRating(int dangerRating);
}

