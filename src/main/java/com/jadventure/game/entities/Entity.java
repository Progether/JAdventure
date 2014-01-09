package com.jadventure.game.entities;

import com.jadventure.game.items.Item;
import com.jadventure.game.items.ItemStack;
import com.jadventure.game.items.Backpack;
import com.jadventure.game.items.Storage;

import java.util.ArrayList;
import java.util.Random;

/**
 * superclass for all entities (includes player, monsters...)
 */
public abstract class Entity {
    
    // All entities can attack, have health, have names...?
    private int healthMax;
    private int health;
    private String name;
    private String className;
    private String intro;
    // levelMult is used to add a multiplier to the attack damage
    private int level;
    // stats
    private int strength;
    private int intelligence;
    private int dexterity;
    private int luck;
    private int stealth;
    private  int gold;
    private double damage = 30;
    private int critChance = 0;
    // Every point in armour reduces an attackers attack by .33
    private int armour;
    private String weapon = "hands";
    private String head = "none";
    private String chest = "shirt";
    private String legs = "pants";
    private String arms = "none";
    private String feet = "none";
    private String shield = "none";

    protected Storage storage;
    Random globalRand = new Random();
    
    // maybe not all entities start at full health, etc.
    public Entity() {
        this.healthMax = 100;
        this.health = this.healthMax;
        this.name = "default";
        this.gold = 0;
        
        this.storage = new Backpack(60.0); // what is a good amount?
    }
    
    public Entity(int healthMax, int health, String name, int gold, Storage storage) {
        this.healthMax = healthMax;
        this.health = health;
        this.name = name;
        this.gold = gold;
        this.storage = storage;
    }

    /**
     * Since all entities can have a level, we need to make a multiplier
     * to increase or decrease damage to monsters based on level and vice versa
     */
    public double levelMult(int attackL, int defenderL) {
        double m = 1;
        
        if (attackL > defenderL) {
            m = 1 + ((attackL - defenderL) * .1);
        } else if (attackL < defenderL) {
            m = 1 - ((defenderL - attackL) * .1);
            return m;
        } else if (attackL == defenderL) {
            return m;
        }
        return m;
    }

    /**
     * Basic attack method this can be used by both entities
     * Modifies damage based on entity.level and defending entity.armor
     */
    public double basicAttack(Entity defender) {
        double damageDone = 0;
        // Calculate damage multiplier and reduce attack by armor
        damageDone = ((this.damage * levelMult(this.level, defender.level)) - (defender.armour * .333));
        
        // Start Debug
        System.out.println("Player attack damage: " + this.damage);
        System.out.println("Player attack multiplier: " + levelMult(this.level, defender.level));
        System.out.println("Defender armour: " + defender.armour);
        System.out.println("Defender armour redcued damage by: " + (defender.armour * .333));
        System.out.println("Player modified attack damage: " + damageDone);
        // End Debug
        return damageDone;
    }
    
    public Storage die() {
        System.out.println(this.name + " has died. Oh look, he dropped:" );
        if(this.storage.isEmpty()) {
            System.out.println("Nothing.");
        } else {
            System.out.println(this.storage.toString());
        }
        return this.storage;
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

    public String getHead() {
        return head;
    }

    public void setHead(String headID) {
        if (!headID.equals(this.head)) {
            if (headID.equals("none")) {
                if (!this.head.equals("none")) {
                    Item head = new Item(this.head);
                    int armour = head.properties.get("armour");
                    this.armour = this.armour - armour;
                }
                this.head = "none";
            } else {
                Item head = new Item(headID);
                int armour = head.properties.get("armour");
                this.armour = this.armour + armour;
                this.head = head.getItemID();
            }
        }
    }


    public String getChest() {
        return chest;
    }

    public void setChest(String chestID) {
        if (!chestID.equals(this.chest)) {
            if (chestID.equals("shirt")) {
                if (!this.chest.equals("shirt")) {
                    Item chest = new Item(this.chest);
                    int armour = chest.properties.get("armour");
                    this.armour = this.armour - armour;
                }
                this.chest = "shirt";
            } else {
                Item chest = new Item(chestID);
                int armour = chest.properties.get("armour");
                this.armour = this.armour + armour;
                this.chest = chest.getItemID();
            }
        }
    }


    public String getLegs() {
        return legs;
    }

    public void setLegs(String legsID) {
        if (!legsID.equals(this.legs)) {
            if (legsID.equals("pants")) {
                if (!this.legs.equals("pants")) {
                    Item legs = new Item(this.legs);
                    int armour = legs.properties.get("armour");
                    this.armour = this.armour - armour;
                }
                this.legs = "pants";
            } else {
                Item legs = new Item(legsID);
                int armour = legs.properties.get("armour");
                this.armour = this.armour + armour;
                this.legs = legs.getItemID();
            }
        }
    }


    public String getArms() {
        return arms;
    }

    public void setArms(String armsID) {
        if (!armsID.equals(this.arms)) {
            if (armsID.equals("none")) {
                if (!this.arms.equals("none")) {
                    Item arms = new Item(this.arms);
                    int armour = arms.properties.get("armour");
                    this.armour = this.armour - armour;
                }
                this.arms = "none";
            } else {
                Item arms = new Item(armsID);
                int armour = arms.properties.get("armour");
                this.armour = this.armour + armour;
                this.arms = arms.getItemID();
            }
        }
    }


    public String getFeet() {
        return feet;
    }

    public void setFeet(String feetID) {
        if (!feetID.equals(this.feet)) {
            if (feetID.equals("none")) {
                if (!this.feet.equals("none")) {
                    Item feet = new Item(this.feet);
                    int armour = feet.properties.get("armour");
                    this.armour = this.armour - armour;
                }
                this.feet = "none";
            } else {
                Item feet = new Item(feetID);
                int armour = feet.properties.get("armour");
                this.armour = this.armour + armour;
                this.feet = feet.getItemID();
            }
        }
    }


    public String getShield() {
        return shield;
    }

    public void setShield(String shieldID) {
        if (!shieldID.equals(this.shield)) {
            if (shieldID.equals("none")) {
                if (!this.shield.equals("none")) {
                    Item shield = new Item(this.shield);
                    int armour = shield.properties.get("armour");
                    this.armour = this.armour - armour;
                }
                this.shield = "none";
            } else {
                Item shield = new Item(shieldID);
                int armour = shield.properties.get("armour");
                this.armour = this.armour + armour;
                this.shield = shield.getItemID();
            }
        }
    }


    public int getHealthMax() {
        return healthMax;
    }

    public void setHealthMax(int healthMax) {
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
        if (!weaponID.equals(this.weapon)) {
            if (weaponID.equals("hands")) {
                if (!this.weapon.equals("hands")) {
                    Item weapon = new Item(this.weapon);
                    int damage = weapon.properties.get("damage");
                    this.damage = this.damage - damage;
                }
                this.weapon = "hands";
            } else {
                Item weapon = new Item(weaponID);
                int damage = weapon.properties.get("damage");
                this.damage = this.damage + damage;
                this.weapon = weapon.getItemID();
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
