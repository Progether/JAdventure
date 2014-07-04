package com.jadventure.game.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jadventure.game.GameBeans;
import com.jadventure.game.IGameElement;
import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.entities.NPC;
import com.jadventure.game.items.Item;
import com.jadventure.game.items.Storage;
import com.jadventure.game.monsters.Monster;
import com.jadventure.game.repository.ItemRepository;
import com.jadventure.game.repository.LocationRepository;

/**
 * The location class mostly deals with getting and setting variables.
 * It also contains the method to print a location's details.
 */
public class Location implements ILocation, IGameElement {
    private LocationRepository locationRepo = GameBeans.getLocationRepository();
    
    private Coordinate coordinate;
    private String title;
    private String description;
    private LocationType locationType;
    private List<String> items;
    private Storage storage = new Storage();
    private List<String> npcs;
    private List<Monster> monsters = new ArrayList<Monster>();
    
    private ItemRepository itemRepo = GameBeans.getItemRepository();

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

    // It checks each direction for an exit and adds it to the exits hashmap if it exists.
    public Map<Direction, ILocation> getExits() {
        Map<Direction, ILocation> exits = new HashMap<Direction, ILocation>();
        ILocation borderingLocation;
        for (Direction direction: Direction.values()) {
            borderingLocation = locationRepo.getLocation(getCoordinate().getBorderingCoordinate(direction));
            if (borderingLocation != null) {
                exits.put(direction, borderingLocation);
            }
        }
        return exits;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<Item>();
        for (String itemId : this.items) {
            Item item = itemRepo.getItem(itemId);
            items.add(item);
        }
        return items;
    }

    public void setNPCs(List<String> npcs) {
        this.npcs = npcs;
    }

    public ArrayList<NPC> getNPCs() {
        ArrayList<NPC> npcs = new ArrayList<NPC>();
        for (String npcID : this.npcs) {
            NPC npc = new NPC(npcID);
            npcs.add(npc);
        }
        return npcs;
    }
    
    public void setMonsters(Monster monster) {
        List<Monster> list = this.monsters;
        list.add(monster);
        this.monsters = list;
    }

    public List<Monster> getMonsters() {
        return this.monsters;
    }

    public void removePublicItem(String itemID) {
        List<String> items = this.items;
        items.remove(itemID);
        setItems(items);
    }

    public void addPublicItem(String itemID) {
        List<String> items = this.items;
        items.add(itemID);
        setItems(items);
    }

    public void print() {
        QueueProvider.offer(getTitle() + ":");
        QueueProvider.offer(getDescription());
        ArrayList<Item> publicItems = getItems();
        if (!publicItems.isEmpty()) {
            QueueProvider.offer("Items:");
            for (Item item : publicItems) {
                QueueProvider.offer("    "+item.getName());
            }
        }
        ArrayList<NPC> npcs = getNPCs();
        if (!npcs.isEmpty()) {
            QueueProvider.offer("NPCs:");
            for (NPC npc : npcs) {
                QueueProvider.offer("   "+npc.getName());
            }
        }
        QueueProvider.offer("");
        for (Map.Entry<Direction,ILocation> direction : getExits().entrySet()) {
            System.out.print(direction.getKey().getDescription() + ", ");
            QueueProvider.offer(direction.getValue().getDescription());
        }
        QueueProvider.offer("");
    }

	@Override
	public void accept(IGameElementVisitor visitor) {
		visitor.visit(this);
	}

    @Override
    public Storage getStorage() {
        // TODO Auto-generated method stub
        return null;
    }
}
