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
import com.jadventure.game.classes.Recruit;
import com.jadventure.game.classes.SewerRat;
import com.jadventure.game.navigation.Coordinate;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.navigation.LocationManager;
import com.jadventure.game.navigation.LocationType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * This class deals with the player and all of its properties.
 * Any method that changes a character or interacts with it should
 * be placed within this class. If a method deals with entities in general or
 * with variables not unique to the player, place it in the entity class.
 */
public class Player extends Entity {
    private ILocation location;
    public static final double MAX_BACKPACK_WEIGHT = 60.0;
    
    public Player(){
        setStorage(new Backpack(MAX_BACKPACK_WEIGHT));
        Item milk = new Item("fmil1");
        addItemToStorage(milk);
    }

    protected static String getProfileFileName(String name) {
        return "json/profiles/" + name + "/" + name + "_profile.json";
    }

    public static boolean profileExists(String name) {
        File file = new File(getProfileFileName(name));
        return file.exists();
    }

    public static Player load(String name) {
        Player player = new Player();

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
            player.setWeapon(json.get("weapon").getAsString());
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
                player.setStorage(new Backpack(MAX_BACKPACK_WEIGHT, itemList));
            }
            Coordinate coordinate = new Coordinate(json.get("location").getAsString());
            player.setLocation(LocationManager.getLocation(coordinate));

            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println( "Unable to open file '" + fileName + "'.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return player;
    }

    // This is known as the singleton pattern. It allows for only 1 instance of a player.
    private static Player player;
    
    public static Player getInstance(String playerClass){
        if(playerClass.equals("recruit")){
            // Instead of having a huge constructor, this is much more readable.
            player =  new Recruit();
            player.setLocation(LocationManager.getInitialLocation());
            return player;
            
        } else if(playerClass.equals("sewerrat")) {
            player = new SewerRat();
            player.setLocation(LocationManager.getInitialLocation());
            return player;
        }
        return player;
    }

    public void getStats(){
        System.out.println("Player name: " + getName() +
                            "\nCurrent weapon: " + player.getWeapon() +
                            "\nGold: " + player.getGold() +
                            "\nHealth/Max: " + getHealth() + "/" + getHealthMax() +
                            "\nDamage/Armour: " + getDamage() + "/" + getArmour() +
                            "\n" + getName() + "'s level: " + getLevel());
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
        jsonObject.addProperty("level", getWeapon());
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
            System.out.println("Your game data was saved.");
        } catch (IOException ex) {
            System.out.println("Unable to save to file '" + fileName + "'.");
        }
    }

        public ArrayList<Item> searchItem(String itemName, ArrayList<Item> itemList) {
        ArrayList<Item> itemMap = new ArrayList();
        for (Item item : itemList) {
            String testItemName = item.getName();
            if (testItemName.equals(itemName)) {
                itemMap.add(item);
            }
        }
        return itemMap;
    }

    public ArrayList<Item> searchItem(String itemName, Storage storage) {
        ArrayList<Item> itemMap = new ArrayList();
        for (ItemStack item : storage.getItems()) {
            String testItemName = item.getItem().getName();
            if (testItemName.equals(itemName)) {
                itemMap.add(item.getItem());
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
        }
    }

    public void dropItem(String itemName) {
        ArrayList<Item> itemMap = searchItem(itemName, getStorage());
        if (!itemMap.isEmpty()) {
            Item item = itemMap.get(0);
            Item itemToDrop = new Item(item.getItemID());
            removeItemFromStorage(itemToDrop);
            location.addPublicItem(itemToDrop.getItemID());
        }
    }

    public void equipItem(String itemName) {
        ArrayList<Item> itemMap = searchItem(itemName, getStorage());
        if (!itemMap.isEmpty()) {
            Item item = itemMap.get(0);
            setWeapon(item.getItemID());
        }
    }

    public void dequipItem(String itemName) {
        ArrayList<Item> itemMap = searchItem(itemName, getStorage());
        if (!itemMap.isEmpty()) {
            Item item = itemMap.get(0);
            setWeapon("hands");
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

}
