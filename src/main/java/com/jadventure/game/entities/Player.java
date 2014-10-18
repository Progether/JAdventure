package com.jadventure.game.entities;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import com.jadventure.game.items.Item;
import com.jadventure.game.items.ItemStack;
import com.jadventure.game.items.Backpack;
import com.jadventure.game.items.Storage;
import com.jadventure.game.navigation.Coordinate;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.navigation.LocationManager;
import com.jadventure.game.navigation.LocationType;
import com.jadventure.game.menus.BattleMenu;
import com.jadventure.game.monsters.Monster;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.DeathException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Iterator;
import java.lang.Math;

/*
 * This class deals with the player and all of its properties.
 * Any method that changes a character or interacts with it should
 * be placed within this class. If a method deals with entities in general or
 * with variables not unique to the player, place it in the entity class.
 */
public class Player extends Entity {
    private ILocation location;
    private int xp;
    
    public Player(){
        
    }

    protected static String getProfileFileName(String name) {
        return "json/profiles/" + name + "/" + name + "_profile.json";
    }

    public static boolean profileExists(String name) {
        File file = new File(getProfileFileName(name));
        return file.exists();
    }

    public static Player load(String name) {
        player = new Player();

        JsonParser parser = new JsonParser();
        String fileName = getProfileFileName(name);
        try {
            Reader reader = new FileReader(fileName);
            JsonObject json = parser.parse(reader).getAsJsonObject();

            player.setName(json.get("name").getAsString());
            player.setHealthMax(json.get("healthMax").getAsInt());
            player.setHealth(json.get("health").getAsInt());
            player.setArmour(json.get("armour").getAsInt());
            player.setDamage(json.get("damage").getAsInt());
            player.setLevel(json.get("level").getAsInt());
            player.setXP(json.get("xp").getAsInt());
            player.setStrength(json.get("strength").getAsInt());
            player.setIntelligence(json.get("intelligence").getAsInt());
            player.setDexterity(json.get("dexterity").getAsInt());
            player.setLuck(json.get("luck").getAsInt());
            player.setStealth(json.get("stealth").getAsInt());
	        player.equipItem("rightHand", new Item(json.get("weapon").getAsString()));
            if (json.has("items")) {
                HashMap<String, Integer> items = new Gson().fromJson(json.get("items"), new TypeToken<HashMap<String, Integer>>(){}.getType());
                ArrayList<ItemStack> itemList = new ArrayList<ItemStack>();
                for (Map.Entry<String, Integer> entry : items.entrySet()) {
                    String itemID = entry.getKey();
                    int amount = entry.getValue();
                    Item item = new Item(itemID);
                    ItemStack itemStack = new ItemStack(amount, item);
                    itemList.add(itemStack);
                }
                float maxWeight = (float)Math.sqrt(player.getStrength()*300);
                player.setStorage(new Backpack(maxWeight, itemList));
            }
            Path orig = Paths.get("json/profiles/"+name+"/locations.json");
            Path dest = Paths.get("json/locations.json");
            Files.copy(orig, dest, StandardCopyOption.REPLACE_EXISTING);
            Coordinate coordinate = new Coordinate(json.get("location").getAsString());
            player.setLocation(LocationManager.getLocation(coordinate));
            reader.close();
        } catch (FileNotFoundException ex) {
            QueueProvider.offer( "Unable to open file '" + fileName + "'.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return player;
    }

    // This is known as the singleton pattern. It allows for only 1 instance of a player.
    private static Player player;
    
    public static Player getInstance(String playerClass){
        player = new Player();
        JsonParser parser = new JsonParser();
        String fileName = "json/npcs.json";
        try {
            Reader reader = new FileReader(fileName);
            JsonObject npcs = parser.parse(reader).getAsJsonObject().get("npcs").getAsJsonObject();
            JsonObject json = new JsonObject();
            for (Map.Entry<String, JsonElement> entry : npcs.entrySet()) {
                if (entry.getKey().equals(playerClass)) {
                    json = entry.getValue().getAsJsonObject();
                }
            }

            player.setName(json.get("name").getAsString());
            player.setHealthMax(json.get("healthMax").getAsInt());
            player.setHealth(json.get("health").getAsInt());
            player.setArmour(json.get("armour").getAsInt());
            player.setDamage(json.get("damage").getAsInt());
            player.setLevel(json.get("level").getAsInt());
            player.setXP(json.get("xp").getAsInt());
            player.setStrength(json.get("strength").getAsInt());
            player.setIntelligence(json.get("intelligence").getAsInt());
            player.setDexterity(json.get("dexterity").getAsInt());
            Random rand = new Random();
            int luck = rand.nextInt(3) + 1;
            player.setLuck(luck);
            player.setStealth(json.get("stealth").getAsInt());
            player.setIntro(json.get("intro").getAsString());
            reader.close();
        } catch (FileNotFoundException ex) {
            QueueProvider.offer( "Unable to open file '" + fileName + "'.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setUpVariables(player);
        return player;
    } 

    public int getXP() {
        return xp;
    }

    public void setXP(int xp) {
        this.xp = xp;
    }

    public static void setUpVariables(Player player) {
        player.setLocation(LocationManager.getInitialLocation());
        float maxWeight = (float)Math.sqrt(player.getStrength()*300);
        player.setStorage(new Backpack(maxWeight));
        player.addItemToStorage(new Item("fmil1"));
    }

    public void getStats(){
        Item weapon = new Item(getWeapon());
        String weaponName = weapon.getName();
        if (weaponName.equals(null) || weaponName.equals("empty")){
            weaponName = "hands";
        }
        String message = "\nPlayer name: " + getName();
              message += "\nCurrent weapon: " + weaponName;
              message += "\nGold: " + getGold();
              message += "\nHealth/Max: " + getHealth() + "/" + getHealthMax();
              message += "\nDamage/Armour: " + getDamage() + "/" + getArmour();
              message += "\nStrength: " + getStrength();
              message += "\nIntelligence: " + getIntelligence();
              message += "\nDexterity: " + getDexterity();
              message += "\nLuck: " + getLuck();
              message += "\nStealth: " + getStealth();
              message += "\nXP: " + getXP();
              message += "\n" + getName() + "'s level: " + getLevel();
        QueueProvider.offer(message);
    }

    public void printBackPack() {
        this.storage.display();
    }

    public void save() {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", getName());
        jsonObject.addProperty("healthMax", getHealthMax());
        jsonObject.addProperty("health", getHealthMax());
        jsonObject.addProperty("armour", getArmour());
        jsonObject.addProperty("damage", getDamage());
        jsonObject.addProperty("level", getLevel());
        jsonObject.addProperty("xp", getXP());
        jsonObject.addProperty("strength", getStrength());
        jsonObject.addProperty("intelligence", getIntelligence());
        jsonObject.addProperty("dexterity", getDexterity());
        jsonObject.addProperty("luck", getLuck());
        jsonObject.addProperty("stealth", getStealth());
        jsonObject.addProperty("weapon", getWeapon());
        HashMap<String, Integer> items = new HashMap<String, Integer>();
        JsonArray itemList = new JsonArray();
        for (ItemStack item : getStorage().getItems()) {
            items.put(item.getItem().getItemID(), item.getAmount());
            JsonPrimitive itemJson = new JsonPrimitive(item.getItem().getItemID());
            itemList.add(itemJson);
        }
        JsonElement itemsJsonObj = gson.toJsonTree(items);
        jsonObject.add("items", itemsJsonObj);
        Coordinate coordinate = getLocation().getCoordinate();
        String coordinateLocation = coordinate.x+","+coordinate.y+","+coordinate.z;
        jsonObject.addProperty("location", coordinateLocation);

        String fileName = getProfileFileName(getName());
        new File(fileName).getParentFile().mkdirs();
        try {
            Writer writer = new FileWriter(fileName);
            gson.toJson(jsonObject, writer);
            writer.close();
            LocationManager.writeLocations();
            Path orig = Paths.get("json/locations.json");
            Path dest = Paths.get("json/profiles/"+getName()+"/locations.json");
            Files.copy(orig, dest, StandardCopyOption.REPLACE_EXISTING);
            QueueProvider.offer("\nYour game data was saved.");
        } catch (IOException ex) {
            QueueProvider.offer("\nUnable to save to file '" + fileName + "'.");
        }
    }

    public ArrayList<Item> searchItem(String itemName, ArrayList<Item> itemList) {
        ArrayList<Item> itemMap = new ArrayList<>();
        for (Item item : itemList) {
            String testItemName = item.getName();
            if (testItemName.equals(itemName)) {
                itemMap.add(item);
            }
        }
        return itemMap;
    }

    public ArrayList<Item> searchItem(String itemName, Storage storage) {
        ArrayList<Item> itemMap = new ArrayList<>();
        for (ItemStack item : storage.getItems()) {
            String testItemName = item.getItem().getName();
            if (testItemName.equals(itemName)) {
                itemMap.add(item.getItem());
            }
        }
        return itemMap;
    }
    
    public ArrayList<Item> searchEquipment(String itemName, HashMap<String, Item> equipment) {
        ArrayList<Item> itemMap = new ArrayList<>();
        for (Item item : equipment.values()) {
            String testItemName = item.getName();
            if (testItemName.equals(itemName)) {
                itemMap.add(item);
            }
        }
        return itemMap;
    }

    public void pickUpItem(String itemName) {
        ArrayList<Item> itemMap = searchItem(itemName, getLocation().getItems());
        if (!itemMap.isEmpty()) {
            Item item = itemMap.get(0);
            Item itemToPickUp = new Item(item.getItemID());
            addItemToStorage(itemToPickUp);
            location.removePublicItem(itemToPickUp.getItemID());
            QueueProvider.offer("\n" + item.getName()+ " picked up");
        }
    }

    public void dropItem(String itemName) {
        ArrayList<Item> itemMap = searchItem(itemName, getStorage());
        if (!itemMap.isEmpty()) {
            Item item = itemMap.get(0);
            Item itemToDrop = new Item(item.getItemID());
            Item weapon = new Item(getWeapon());
            String wName = weapon.getName();

            if (itemName.equals(wName)) {
                dequipItem(wName);
            }
            removeItemFromStorage(itemToDrop);
            location.addPublicItem(itemToDrop.getItemID());
            QueueProvider.offer("\n" + item.getName()+ " dropped");
        }
    }

    public void equipItem(String itemName) {
        ArrayList<Item> itemMap = searchItem(itemName, getStorage());
        if (!itemMap.isEmpty()) {
            Item item = itemMap.get(0);
            HashMap change = this.equipItem(item.getPosition(), item);
            QueueProvider.offer(item.getName()+ " equipped");
            printStatChange(change);
        }
    }

    public void equipItem(String place, String itemName) {
	    Item item = new Item("empty");
	    if (!itemName.equals("empty")) {
            ArrayList<Item> itemMap = searchItem(itemName, getStorage());
            if (!itemMap.isEmpty()) {
                item = itemMap.get(0);
            }
         }
         HashMap change = this.equipItem(place, item);
         QueueProvider.offer(item.getName() + " equipped");
         printStatChange(change);
    }
    
    public void dequipItem(String itemName) {
         ArrayList<Item> itemMap = searchEquipment(itemName, getEquipment());
         if (!itemMap.isEmpty()) {
            Item item = itemMap.get(0);
            HashMap change = this.unequipItem(item);
            QueueProvider.offer(item.getName()+" unequipped");
	        printStatChange(change);
         }
    }

    private void printStatChange(HashMap stats) {
         Set set = stats.entrySet();
         Iterator i = set.iterator();
         while (i.hasNext()) {
              Map.Entry me = (Map.Entry) i.next();
              double value = Double.parseDouble((String) me.getValue());
              switch ((String) me.getKey()) {
                  case "damage": {
                          if (value >= 0.0) {
                              QueueProvider.offer(me.getKey() + ": " + this.getDamage() + " (+" + me.getValue() + ")\n");
                          } else {
                              QueueProvider.offer(me.getKey() + ": " + this.getDamage() + " (" + me.getValue() + ")\n");
                          }
                          break;
                    }
                    case "health": {
                          if (value >= 0) {
                              QueueProvider.offer(me.getKey() + ": " + this.getHealth() + " (+" + me.getValue() + ")\n");
                          } else {
                              QueueProvider.offer(me.getKey() + ": " + this.getHealth() + " (" + me.getValue() + ")\n");
                          }
                          break;
                    }
                    case "armour": {
                          if (value >= 0) {
                              QueueProvider.offer(me.getKey() + ": " + this.getArmour() + " (+" + me.getValue() + ")\n");
                          } else {
                              QueueProvider.offer(me.getKey() + ": " + this.getArmour() + " (" + me.getValue() + ")\n");
                          }
                          break;
                    }
                    case "maxHealth": {
                          if (value  >= 0) {
                              QueueProvider.offer(me.getKey() + ": " + this.getHealthMax() + " (+" + me.getValue() + ")\n");
                          } else {
                              QueueProvider.offer(me.getKey() + ": " + this.getHealthMax() + " (" + me.getValue() + ")\n");
                          }
                          break;
                    }
              }
         }
    }

    public void inspectItem(String itemName) {
        ArrayList<Item> itemMap = searchItem(itemName, getStorage());
        if (itemMap.isEmpty()) {
            itemMap = searchItem(itemName, getLocation().getItems());
        }
        if (!itemMap.isEmpty()) {
            Item item = itemMap.get(0);
            item.display();
        } else {
            QueueProvider.offer("Item doesn't exist within your view.");
        }
    }

    public ILocation getLocation() {
        return location;
    }

    public void setLocation(ILocation location) {
        this.location = location;
    }

    public LocationType getLocationType(){
    	return getLocation().getLocationType();
    }

    public void attack(String opponentName) throws DeathException {
        Monster opponent = null;
        ArrayList<Monster> monsters = getLocation().getMonsters();
        for (int i = 0; i < monsters.size(); i++) {
                 if (monsters.get(i).monsterType.equalsIgnoreCase(opponentName)) {
                 opponent = monsters.get(i);
             }
        }
        if (opponent != null) {
             BattleMenu battleMenu = new BattleMenu(opponent, this);
        } else {
             QueueProvider.offer("Opponent not found");
        }
    }
}
