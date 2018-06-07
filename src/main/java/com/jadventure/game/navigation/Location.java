package com.jadventure.game.navigation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jadventure.game.GameBeans;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.entities.NPC;
import com.jadventure.game.items.Item;
import com.jadventure.game.items.Storage;
import com.jadventure.game.monsters.Monster;
import com.jadventure.game.repository.ItemRepository;
import com.jadventure.game.repository.LocationRepository;
import com.jadventure.game.repository.NpcRepository;
import com.jadventure.game.repository.RepositoryException;

/**
 * The location class mostly deals with getting and setting variables.
 * It also contains the method to print a location's details.
 */
public class Location implements ILocation {
    // @Resource
    protected static ItemRepository itemRepo = GameBeans.getItemRepository();
    protected static NpcRepository npcRepo = GameBeans.getNpcRepository();

    private Coordinate coordinate;
    private String title;
    private String description;
    private LocationType locationType;
    private int dangerRating;
    private Storage storage = new Storage();
    private List<NPC> npcs = new ArrayList<>();
    private List<Monster> monsters = new ArrayList<>();

    public Location() {

    }
    public Location(Coordinate coordinate, String title, String description, LocationType locationType) {
        this.coordinate = coordinate;
        this.title = title;
        this.description = description;
        this.locationType = locationType;
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
        LocationRepository locationRepo = GameBeans.getLocationRepository();
        for(Direction direction: Direction.values()) {
            try {
                borderingLocation = locationRepo.getLocation(getCoordinate().getBorderingCoordinate(direction));
                if (borderingLocation.getCoordinate().getZ() == getCoordinate().getZ()) {
                    exits.put(direction, borderingLocation);
                } else if (getLocationType().equals(LocationType.STAIRS)) {
                    exits.put(direction, borderingLocation);
                }
            }
            catch (RepositoryException ex) {
                //Location does not exist so do nothing
            }
        }
        return exits;
    }

    public Storage getStorage() {
        return storage;
    }
    public List<Item> getItems() {
        return storage.getItems();
    }

    public void addNpcs(List<NPC> npcs) {
        for (NPC npc : npcs) {
            addNpc(npc);
        } 
    }

    public void addNpc(NPC npc) {
        npcs.add(npc);
    }

    public void remove(NPC npc) {
        if (npc.getClass() == Monster.class) {
            removeMonster((Monster) npc);
        } else {
            removeNpc(npc);
        }
    }

    public void removeNpc(NPC npc) {
        for (int i = 0; i < npcs.size(); i++) {
            if (npcs.get(i).equals(npc)) {
                npcs.remove(i);
            }
        }
    }

    public List<NPC> getNpcs() {
        return Collections.unmodifiableList(npcs);
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

    public Item removeItem(Item item) {
        return storage.remove(item);
    }

    public void addItem(Item item) {
        storage.add(item);
    }

    public void print() {
        QueueProvider.offer("\n" + getTitle() + ":");
        QueueProvider.offer("    " + getDescription());
        List<Item> items = getItems();
        if (!items.isEmpty()) {
            QueueProvider.offer("Items:");
            for (Item item : items) {
                QueueProvider.offer("    " + item.getName());
            }
        }
        List<NPC> npcs = getNpcs();
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
