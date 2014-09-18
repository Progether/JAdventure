package com.jadventure.game.entities;

import com.jadventure.game.items.Item;
import com.jadventure.game.items.ItemStack;
import com.jadventure.game.items.Backpack;
import com.jadventure.game.items.Storage;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;

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
    private HashMap<String, Item> equipment;
    protected Storage storage;
    Random globalRand = new Random();
    
    // maybe not all entities start at full health, etc.
    public Entity() {
        this.healthMax = 100;
        this.health = this.healthMax;
        this.name = "default";
        this.gold = 0;
        this.equipment = new HashMap<String, Item>();
   }
    
    public Entity(int healthMax, int health, String name, int gold, Storage storage, HashMap<String, Item> equipment) {
        this.healthMax = healthMax;
        this.health = health;
        this.name = name;
        this.gold = gold;
        this.storage = storage;
	this.equipment = equipment;
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

    public HashMap equipItem(String place, Item item) {
	 Item empty = new Item("empty");
	 Item hand = new Item("hands");
	 if (!empty.equals(equipment.get(place)) && !hand.equals(equipment.get(place))) {
              unequipItem(item);
	 } else if (equipment.get(place).equals(hand)) {
   	      this.damage -= hand.properties.get("damage");
	 }
	 if (!item.equals(empty)) {
             place = item.getPosition();
	 }
         this.equipment.put(place, item);
	 HashMap result = new HashMap();
	 switch (item.getItemID().charAt(0)) {
	      case 'w': {
	           this.weapon = item.getItemID();
		   this.damage += item.properties.get("damage");
		   result.put("damage", item.properties.get("damage"));
		   break;
	      } case 'a': {
	           this.armour += item.properties.get("damage");
		   result.put("armour", item.properties.get("armour"));
		   break;
	      } case 'p': {
		   if (item.properties.containsKey("healthMax")) {
	  	        this.healthMax += item.properties.get("healthMax");
		        this.health += item.properties.get("healthMax");
			unequipItem(item); // One use only
		        result.put("health", item.properties.get("healthMax"));
		   }
		   break;
	      } case 'f': {
		   this.health += item.properties.get("health");
		   this.health = (this.health > this.healthMax) ? this.healthMax : this.health;
		   unequipItem(item); //One use only
		   result.put("health", item.properties.get("healthMax"));
		   break;
	      }
          }
	return result;	 
    }
    
    public void unequipItem(Item item) {
	 String place = "";
	 for (String key : this.equipment.keySet()) {
	      if (this.equipment.get(key).equals(item)) {
                   place = key;
	      }
	 }
	 if (place.contains("Hand")) {
	      Item weapon = new Item("hands");
	      this.equipment.put(place, weapon);
	      this.weapon = weapon.getItemID();
	 } else if (!place.isEmpty()) {
	      this.equipment.put(place, new Item("empty"));
	 }
	 if (item.properties.containsKey("damage")) {
	      this.damage -= item.properties.get("damage");
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
