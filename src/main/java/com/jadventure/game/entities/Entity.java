package com.jadventure.game.entities;

import java.util.Random;

import com.jadventure.game.GameBeans;
import com.jadventure.game.items.Item;
import com.jadventure.game.items.ItemStack;
import com.jadventure.game.items.Storage;
import com.jadventure.game.repository.ItemRepository;

/**
 * superclass for all entities (includes player, monsters...)
 */
public abstract class Entity {
    // FIXME Remove static
    protected static ItemRepository itemRepo = GameBeans.getItemRepository();
    
    // All entities can attack, have health, have names...?
    private int healthMax;
    private int health;
    private String name;
    private String className;
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
    private String weapon = "hands";
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
        return this.intro;
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

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weaponID) {
        if (! weaponID.equals(this.weapon)) {
            if (weaponID.equals("hands")) {
                if (! this.weapon.equals("hands")) {
                    Item weapon = itemRepo.getItem(this.weapon);
                    int damage = weapon.getProperties().get("damage");
                    this.damage = this.damage - damage;
                }
                this.weapon = "hands";
            } else {
                Item weapon = itemRepo.getItem(weaponID);
                int damage = weapon.getProperties().get("damage");
                this.damage = this.damage + damage;
                this.weapon = weapon.getId();
            }
        }
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
    
    public void addItemToStorage(Item i){
        storage.addItem(new ItemStack(1, i));
    }

    public void removeItemFromStorage(Item i) {
        storage.removeItem(new ItemStack(1, i)); //TODO: fix this
    }
}
