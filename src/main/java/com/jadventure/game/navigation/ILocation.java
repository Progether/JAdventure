package com.jadventure.game.navigation;

import java.util.List;
import java.util.Map;

import com.jadventure.game.entities.NPC;
import com.jadventure.game.items.Item;
import com.jadventure.game.items.Storage;
import com.jadventure.game.monsters.Monster;

/**
 * This interface maps all the properties and methods that 
 * pertain to a specific location.
 */
public interface ILocation {
    Coordinate getCoordinate();
    String getTitle();
    String getDescription();
    LocationType getLocationType();

    List<Item> getItems();
    Storage getStorage();

    void addItem(Item item);
    Item removeItem(Item item);

    List<NPC> getNpcs();
    List<Monster> getMonsters();

    void addMonster(Monster monster);
    void removeMonster(Monster monster);

    void addNpcs(List<String> npcIds);
    void addNpc(String npcID);
    void removeNpc(NPC npc);

    int getDangerRating();
    void setDangerRating(int dangerRating);

    Map<Direction, ILocation> getExits();
    void print();
}

