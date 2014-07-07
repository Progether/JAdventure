package com.jadventure.game.navigation;

import java.util.List;
import java.util.Map;

import com.jadventure.game.IGameElement;
import com.jadventure.game.entities.NPC;
import com.jadventure.game.items.Item;
import com.jadventure.game.items.Storage;
import com.jadventure.game.monsters.Monster;

/**
 * This interface maps all the properties and methods that 
 * pertain to a specific location.
 */
public interface ILocation extends IGameElement {

    String getTitle();
    String getDescription();

	Coordinate getCoordinate();
    LocationType getLocationType();
    Map<Direction, ILocation> getExits();

    List<Item> getItems();
    void addPublicItem(String itemID);
    void removePublicItem(String itemID);

    List<NPC> getNPCs();
    
    List<Monster> getMonsters();
    void setMonsters(Monster monster);
    
    @Deprecated
    void print();

    Storage getStorage();

}
