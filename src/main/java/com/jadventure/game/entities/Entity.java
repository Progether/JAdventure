package com.jadventure.game.entities;

import java.util.Random;

import com.jadventure.game.items.Item;
import com.jadventure.game.items.Storage;

/**
 * superclass for all entities (includes player, monsters...)
 */
public abstract class Entity {
    // All entities can attack, have health, have names...?
    private String id;
    private String name;
    private String className;
    private int healthMax;
    private int health;
    private String intro;
    // levelMult is used to add a multiplier to the attack damage
    // soemcdnguy4 asks: Where in code is levelMult?
    private int level;
    // stats
    private int strength;
    private int intelligence;
    private int dexterity;
    private int luck;
    private int stealth;
    private int gold;
    private double damage = 30;
    private int critChance = 0;
    // Every point in armour reduces an attackers attack by .33
    private int armour;
    protected Item weapon = null; //"hands";
    protected Storage storage = new Storage(100);
    Random globalRand = new Random();
    
    // maybe not all entities start at full health, etc.
    public Entity() {
        this.healthMax = 100;
        this.health = this.healthMax;
        this.name = "default";
        this.gold = 0;
    }
    
    public Entity(int healthMax, int health, String name, int gold, Storage storage) {
        this.healthMax = healthMax;
        this.health = health;
        this.name = name;
        this.gold = gold;
        this.storage = storage;
    }
    public Entity(String id, String name, int healthMax, int health, double damage, int armour,
    		int level, int strength, int intelligence, int dexterity,
    		int stealth, Item weapon, String introduction, int luck) {
    	this.id = id;
    	this.className = name;
        this.healthMax = healthMax;
        this.health = health;
        this.damage = damage;
        this.armour = armour;
        this.level = level;
        this.strength = strength;
        this.intelligence = intelligence;
        this.dexterity = dexterity;
        this.stealth = stealth;
        this.weapon = weapon;
        this.intro = introduction;

        Random rand = new Random();
        this.luck = rand.nextInt(luck) + 1;
    }
    
    
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getHealth() {
        return this.health;
    }
        
    public void setHealth(int health) {
        this.health = health;
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

    public int getCritChance() {
        return critChance;
    }

    public void setCritChance(int critChance) {
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
        if (getHealth() > healthMax) {
            setHealth(healthMax);
        }
        this.healthMax = healthMax;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
     
    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIntro() {
        return intro;
    }
    
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public Item getWeapon() {
        return weapon;
    }

    public void setWeapon(Item item) {
    	if (item.getType().equalsIgnoreCase("weapon")) {
    		weapon = item;
    	}
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
    
    public void addItemToStorage(Item item){
        storage.add(item);
    }

    public void removeItemFromStorage(Item item) {
        storage.remove(item.getName());
    }
}
