package com.jadventure.game.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jadventure.game.IGameElement;
import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.items.Item;
import com.jadventure.game.items.Storage;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.navigation.LocationType;
import com.jadventure.game.repository.CharacterBuilder;

/**
 * This class deals with the player and all of its properties.
 * Any method that changes a character or interacts with it should
 * be placed within this class. If a method deals with entities in general or
 * with variables not unique to the player, place it in the entity class.
 */
public class Player extends Entity implements IGameElement {
    private ILocation location;


    public Player() {
    	
    }
    public Player(CharacterBuilder charBldr) {
    	this(charBldr.getId(),
    			charBldr.getName(),
    			charBldr.getHealthMax(),
    			charBldr.getHealth(),
    			charBldr.getDamage(),
    			charBldr.getArmour(),
    			charBldr.getLevel(),
    			charBldr.getStrength(),
    			charBldr.getIntelligence(),
    			charBldr.getDexterity(),
    			charBldr.getStealth(),
    			charBldr.getWeapon(),
    			charBldr.getIntroduction(),
    			charBldr.getLuck());
    }
    public Player(String id, String name, int healthMax, int health, double damage,
			int armour, int level, int strength, int intelligence,
			int dexterity, int stealth, Item weapon, String introduction,
			int luck) {
		super(id, name, healthMax, health, damage, armour, level, strength,
				intelligence, dexterity, stealth, weapon, introduction, luck);
	}

	@Deprecated
    public void getStatistics() {
        QueueProvider.offer("\nPlayer name: " + getName() +
                            "\nCurrent weapon: " + weapon.getName() +
                            "\nGold: " + getGold() +
                            "\nHealth/Max: " + getHealth() + "/" + getHealthMax() +
                            "\nDamage/Armour: " + getDamage() + "/" + getArmour() +
                            "\nStrength: " + getStrength() +
                            "\nIntelligence: " + getIntelligence() +
                            "\nDexterity: " + getDexterity() +
                            "\nLuck: " + getLuck() +
                            "\nStealth: " + getStealth() +
                            "\n" + getName() + "'s level: " + getLevel());
    }


    public List<Item> searchItem(String itemName, List<Item> itemList) {
        List<Item> itemMap = new ArrayList<>();
        for (Item item : itemList) {
            String testItemName = item.getName();
            if (testItemName.equals(itemName)) {
                itemMap.add(item);
            }
        }
        return itemMap;
    }

    public List<Item> searchItem(String itemName, Storage storage) {
    	if (! storage.contains(itemName)) {
    		return Collections.emptyList();
    	}
    	
    	return storage.getItems().get(itemName);
    }

    public boolean pickUpItem(String itemName) {
    	if (getLocation().getStorage().contains(itemName)) {
    		Item item = getLocation().getStorage().remove(itemName);
    		getStorage().add(item);
    		return true;
    	}
    	return false;
    }

    public boolean dropItem(String itemName) {
    	if (getStorage().contains(itemName)) {
    		Item item = getStorage().remove(itemName);
    		getLocation().getStorage().add(item);
    		return true;
    	}
    	else if (weapon.getName().equals(itemName)) {
    		dequipItem(itemName);
    	}
    	return false;
    }

    public void equipItem(String itemName) {
        List<Item> itemMap = searchItem(itemName, getStorage());
        if (!itemMap.isEmpty()) {
            Item item = itemMap.get(0);
            setWeapon(item);
            QueueProvider.offer("\n" + item.getName()+ " equipped");
        }
    }

    public void dequipItem(String itemName) {
    	if (weapon != null && weapon.getName().equalsIgnoreCase(itemName)) {
    		storage.add(weapon);
            QueueProvider.offer("\n" + weapon.getName() + " dequipped");
    		setWeapon(null);
    	}
    }

    public ILocation getLocation() {
        return location;
    }

    public void setLocation(ILocation location) {
        this.location = location;
    }

    public LocationType getLocationType() {
    	return getLocation().getLocationType();
    }


	@Override
	public void accept(IGameElementVisitor visitor) {
		visitor.visit(this);
	}

}
