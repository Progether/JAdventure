package com.jadventure.game;

import java.util.ArrayList;
import java.util.Random;

// superclass for all entities (includes player, monsters...)

public abstract class Entity {
    
    // All entities can attack, have health, have names...?
    private int healthMax;
    private int health;
    private String name;
    // levelMult is used to add a multiplier to the attack damage
    private  int level;
    private  int gold;
    private double damage = 30;
    private int critChance = 0;
    // Every point in armour reduces an attackers attack by .33
    private int armour;
    private ArrayList<Item> backpack;
    Random globalRand = new Random();
    
    // maybe not all entities start at full health, etc.
    public Entity() {
        this.healthMax = 100;
        this.health = this.healthMax;
        this.name = "default";
        this.gold = 0;
        
        this.backpack = new ArrayList<Item>();
    }

    // Since all entities can have a level, we need to make a multiplier
    // to increase or decrease damage to monsters based on level and vice versa
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

    // Basic attack method this can be used by both entities
    // Modifies damage based on entity.level and defending entity.armor
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
    
    public ArrayList<Item> die() {
        System.out.println(this.name + " has died. Oh look, he dropped:" );
        if(this.backpack.isEmpty()) {
            System.out.println("Nothing.");
        } else {
            for (Item item : this.backpack) {
                System.out.println(item.getName());
            }
        }
        return this.backpack;
    }
    // Setters and Getters
    
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
        this.healthMax = healthMax;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Item> getBackpack() {
        return backpack;
    }

    public void setBackpack(ArrayList<Item> backpack) {
        this.backpack = backpack;
    }
    public void addItemToBackpack(Item i){
        backpack.add(i);
    }
}
