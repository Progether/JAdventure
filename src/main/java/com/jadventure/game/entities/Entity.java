package com.jadventure.game.entities;

import com.jadventure.game.items.Item;
import com.jadventure.game.items.ItemStack;
import com.jadventure.game.items.Backpack;
import com.jadventure.game.items.Storage;
import com.jadventure.game.QueueProvider;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

/**
 * superclass for all entities (includes player, monsters...)
 */
public abstract class Entity {
    
    // All entities can attack, have health, have names
    private int healthMax;
    private int health;
    private String name;
    private String className;
    private String intro;
    private int level;
    // Statstics
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
    private HashMap<String, Item> equipment;
    public HashMap<String, Integer> classStats = new HashMap<String, Integer>() {
        {
            put("Recruit", 0);
            put("Sewer Rat", 0);
        };
    };
    private String currentClassName;
    protected Storage storage;
    Random globalRand = new Random();

    public String getCurrentClass() {
        return this.currentClassName;
    }

    public void setCurrentClass(String className) {
        this.currentClassName = className;
    }

    public void checkCurrentClass() {
        Iterator it = this.classStats.entrySet().iterator();
        int highestClassLevel = 0;
        String highestClassName = "";
        while (it.hasNext()) {
            Map.Entry<String, Integer> pairs = (Map.Entry<String, Integer>)it.next();
            if (pairs.getValue() > highestClassLevel) {
                highestClassLevel = pairs.getValue();
                highestClassName = pairs.getKey();
            }
        }
        this.currentClassName = highestClassName;
    }
    
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

    public HashMap<String, Item> getEquipment() {
        return equipment;
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
         double oldDamage = this.damage;
             Item empty = new Item("empty");
         if (place.equals("")) {
                 place = item.getPosition();
         }
         if (equipment.get(place) != null) {
              if (!empty.equals(equipment.get(place))) {
                   unequipItem(equipment.get(place));
              }
         }
         this.equipment.put(place, item);
         HashMap<String, String> result = new HashMap<String, String>();
         switch (item.getItemID().charAt(0)) {
              case 'w': {
                  this.weapon = item.getItemID();
                  this.damage += item.getProperty("damage");
                  double diffDamage = this.damage - oldDamage;
                  result.put("damage", String.valueOf(diffDamage));
                  break;
              } case 'a': {
                   this.armour += item.getProperty("armour");
                   result.put("armour", String.valueOf(item.getProperty("armour")));
                   break;
              } case 'p': {
                   if (item.propertiesContainsKey("healthMax")) {
                        this.healthMax += item.getProperty("healthMax");
                        this.health += item.getProperty("healthMax");
                        unequipItem(item); // One use only
                        removeItemFromStorage(item);
                        result.put("health", String.valueOf(item.getProperty("healthMax")));
                   }
                   break;
              } case 'f': {
                   int healthOld = this.getHealth();
                   this.health += item.getProperty("health");
                   this.health = (this.health > this.healthMax) ? this.healthMax : this.health;
                   unequipItem(item); //One use only
                   removeItemFromStorage(item);
                   result.put("health", String.valueOf(health - healthOld));
                   break;
              }
         }
         return result;	 
    }
    
    public HashMap unequipItem(Item item) {
         double oldDamage = this.damage;
         String place = "";
         for (String key : this.equipment.keySet()) {
              if (this.equipment.get(key).equals(item)) {
                       place = key;
              }
         }
         if (!place.isEmpty()) {
              this.equipment.put(place, new Item("empty"));
         }
         HashMap<String, String> result = new HashMap<String, String>();
         if (item.propertiesContainsKey("damage")) {
            this.weapon = "hands";   
            this.damage -= item.getProperty("damage");
            double diffDamage = this.damage - oldDamage;
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
            for (Map.Entry<String, Item> item : equipment.entrySet()) {
                if (!item.getKey().equals("mouth")) {
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
    
    public void addItemToStorage(Item i) {
        if (!i.equals(new Item("empty"))) {
            storage.addItem(new ItemStack(1, i));
        }
    }

    public void removeItemFromStorage(Item i) {
        if (!i.equals(new Item("empty"))) {
            storage.removeItem(new ItemStack(1, i)); 
        }
    }
}
