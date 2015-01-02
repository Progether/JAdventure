package com.jadventure.game.entities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.jadventure.game.GameBeans;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.items.Item;
import com.jadventure.game.items.ItemStack;
import com.jadventure.game.items.Storage;
import com.jadventure.game.repository.ItemRepository;

/**
 * superclass for all entities (includes player, monsters...)
 */
public abstract class Entity {
    // @Resource
    protected ItemRepository itemRepo = GameBeans.getItemRepository();
    
    // All entities can attack, have health, have names
    private int healthMax;
    private int health;
    private String name;
    private String intro;
    private int level;
    // Statistics
    private int strength;
    private int intelligence;
    private int dexterity;
    private int luck;
    private int stealth;
    private int gold;
    private double damage = 30;
    private double critChance = 0.0;
    private int armour;
    private String weapon = "empty";
    private Map<EquipmentLocation, Item> equipment;
    protected Storage storage;

    public Entity() {
    	this(100, 100, "default", 0, null, new HashMap<EquipmentLocation, Item>());
    }
    
    public Entity(int healthMax, int health, String name, int gold, Storage storage, Map<EquipmentLocation, Item> equipment) {
        this.healthMax = healthMax;
        this.health = health;
        this.name = name;
        this.gold = gold;
        if (storage != null) {
        	this.storage = storage;
        }
        else {
        	this.storage = new Storage(300);
        }
	    this.equipment = equipment;
    }
    

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
        if (health > healthMax) {
            health = healthMax;
        }
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getCritChance() {
        return critChance;
    }

    public void setCritChance(double critChance) {
        this.critChance = critChance;
    }

    public int getArmour() {
        return armour;
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public int getHealthMax() {
        return healthMax;
    }

    public void setHealthMax(int healthMax) {
        this.healthMax = healthMax;
        if (health > healthMax) {
            health = healthMax;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
     
    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIntro() {
        return this.intro;
    }
    
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Map<EquipmentLocation, Item> getEquipment() {
        return Collections.unmodifiableMap(equipment);
    }

    public int getStrength() {
        return strength;
    }
    
    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
 
    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getStealth() {
        return stealth;
    }

    public void setStealth(int stealth) {
        this.stealth = stealth;
    }

    public String getWeapon() {
        return weapon;
    }

    public Map<String, String> equipItem(EquipmentLocation place, Item item) {
        double oldDamage = this.damage;
        Item empty = itemRepo.getItem("empty");
        if (place == null) {
            place = item.getPosition();
        }
        if (equipment.get(place) != null) {
            if (!empty.equals(equipment.get(place))) {
                unequipItem(equipment.get(place));
            }
        }
        if (place == EquipmentLocation.BOTH_HANDS) {
            Item leftHand = equipment.get(EquipmentLocation.LEFT_HAND);
            Item rightHand = equipment.get(EquipmentLocation.RIGHT_HAND);
            if (leftHand != null && !empty.equals(leftHand)) {
                unequipItem(leftHand);
            }
            if (rightHand != null && !empty.equals(rightHand)) {
                unequipItem(rightHand);
            }
        }
        equipment.put(place, item);
        removeItemFromStorage(item);
        Map<String, String> result = new HashMap<String, String>();
        switch (item.getId().charAt(0)) {
        case 'w': {
            this.weapon = item.getId();
            this.damage += item.getProperty("damage");
            double diffDamage = this.damage - oldDamage;
            result.put("damage", String.valueOf(diffDamage));
            break;
        }
        case 'a': {
            this.armour += item.getProperty("armour");
            result.put("armour", String.valueOf(item.getProperty("armour")));
            break;
        }
        case 'p': {
            if (item.containsProperty("healthMax")) {
                int healthOld = this.getHealth();
                this.healthMax += item.getProperty("healthMax");
                this.health += item.getProperty("health");
                this.health = (this.health > this.healthMax) ? this.healthMax : this.health;
                int healthNew = this.health;
                unequipItem(item); // One use only
                removeItemFromStorage(item);
                if (healthNew != healthOld) {
                    result.put("health", String.valueOf(health - healthOld));
                } else {
                    result.put("health", String.valueOf(item.getProperty("healthMax")));
                }
            }
            break;
        }
        case 'f': {
            int healthOld = this.getHealth();
            this.health += item.getProperty("health");
            this.health = (this.health > this.healthMax) ? this.healthMax
                    : this.health;
            unequipItem(item); // One use only
            removeItemFromStorage(item);
            result.put("health", String.valueOf(health - healthOld));
            break;
        }
        }
        return result;
    }

    public Map<String, String> unequipItem(Item item) {
        double oldDamage = damage;
        for (EquipmentLocation key : equipment.keySet()) {
            if (equipment.get(key).equals(item)) {
                equipment.put(key, null);
            }
        }
        addItemToStorage(item);
        Map<String, String> result = new HashMap<String, String>();
        if (item.containsProperty("damage")) {
            weapon = "hands";
            damage -= item.getProperty("damage");
            double diffDamage = damage - oldDamage;
            result.put("damage", String.valueOf(diffDamage));
        }
        return result;
    }

    public void printEquipment() {
        QueueProvider.offer("\n------------------------------------------------------------");
        QueueProvider.offer("Equipped Items:");
        if (equipment.keySet().size() == 0) {
            QueueProvider.offer("--Empty--");
        } else {
            for (Map.Entry<EquipmentLocation, Item> item : equipment.entrySet()) {
                if (item.getKey() != null && item.getValue() != null) {
                    QueueProvider.offer(item.getKey() + " - " + item.getValue().getName());
                }
            }
        }
        QueueProvider.offer("------------------------------------------------------------"); 
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void printStorage() {
       storage.display();
    } 
    
    public void addItemToStorage(Item item) {
        if (!item.equals(itemRepo.getItem("empty")) && !item.equals(itemRepo.getItem("hands"))) {
            storage.addItem(new ItemStack(1, item));
        }
    }

    public void removeItemFromStorage(Item item) {
        if (!item.equals(itemRepo.getItem("empty"))) {
            storage.removeItem(new ItemStack(1, item)); 
        }
    }

}
