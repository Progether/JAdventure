package com.jadventure.game.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jadventure.game.GameBeans;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.entities.NPC;
import com.jadventure.game.items.Item;
import com.jadventure.game.items.ItemStack;
import com.jadventure.game.monsters.Monster;
import com.jadventure.game.repository.ItemRepository;

/**
 * The location class mostly deals with getting and setting variables.
 * It also contains the method to print a location's details.
 */
public class Location implements ILocation {
    // @Resource
    protected static ItemRepository itemRepo = GameBeans.getItemRepository();

    private Coordinate coordinate;
    private String title;
    private String description;
    private LocationType locationType;
    private int dangerRating;
    private List<Item> items;
    private List<NPC> npcs;
    private List<Monster> monsters = new ArrayList<>();

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

    public int getDangerRating() {
        return dangerRating;
    }

    public void setDangerRating(int dangerRating) {
        this.dangerRating = dangerRating;
    }

    // It checks each direction for an exit and adds it to the exits hashmap if it exists.
    public Map<Direction, ILocation> getExits() {
        Map<Direction, ILocation> exits = new HashMap<Direction, ILocation>();
        ILocation borderingLocation;
        for(Direction direction: Direction.values()) {
            borderingLocation = LocationManager.getLocation(getCoordinate().getBorderingCoordinate(direction));
            if (borderingLocation != null) {
                if (borderingLocation.getCoordinate().getZ() == getCoordinate().getZ()) {
                    exits.put(direction, borderingLocation);
                } else if (getLocationType().equals(LocationType.STAIRS)) {
                    exits.put(direction, borderingLocation);
                }
            } 
        }
        return exits;
    }

    public void setItems(ArrayList<String> items) {
        ArrayList<Item> itemsList = new ArrayList<>();
        for (String itemId : items) {
            Item itemName = itemRepo.getItem(itemId);
            itemsList.add(itemName);
        }
        this.items = itemsList;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        for (Item item : this.items) {
            items.add(item);
        }
        return items;
    }

    public void setNPCs(ArrayList<String> npcs) {
        ArrayList<NPC> npcList = new ArrayList<NPC>();
        for (String npcID : npcs) {
            NPC npc = new NPC(npcID);
            npcList.add(npc);
        } 
        this.npcs = npcList;
    }

    public ArrayList<NPC> getNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        for (NPC npc : this.npcs) {
            npcs.add(npc);
        }
        return npcs;
    }
   
    public void addMonster(Monster monster) {
        if (monster != null) {
            monsters.add(monster);
        }
    }

    public void removeMonster(Monster monster) {
        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).equals(monster)) {
                monsters.remove(i);
            }
        }
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void removePublicItem(String itemID) {
        items.remove(itemID);
    }

    public void addPublicItem(String itemID) {
        items.add(itemRepo.getItem(itemID));
    }

    public void addPublicItems(ArrayList<ItemStack> items) {
        for (int i = 0; i < items.size(); i++) {
            String itemId = items.get(i).getItem().getId();
            addPublicItem(itemId);
        }
    }

    public void print() {
        QueueProvider.offer("\n" + getTitle() + ":");
        QueueProvider.offer("    " + getDescription());
        ArrayList<Item> publicItems = getItems();
        if (!publicItems.isEmpty()) {
            QueueProvider.offer("Items:");
            for (Item item : publicItems) {
                QueueProvider.offer("    " + item.getName());
            }
        }
        ArrayList<NPC> npcs = getNPCs();
        if (!npcs.isEmpty()) {
            QueueProvider.offer("NPCs:");
            for (NPC npc : npcs) {
                QueueProvider.offer("   " + npc.getName());
            }
        }
        QueueProvider.offer("");
        for (Map.Entry<Direction,ILocation> direction : getExits().entrySet()) {
		QueueProvider.offer(direction.getKey().getDescription() + ": ");
    		QueueProvider.offer("    " + direction.getValue().getDescription());
        }
    }
}
