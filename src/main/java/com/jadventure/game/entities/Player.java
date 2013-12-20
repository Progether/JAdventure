package com.jadventure.game.entities;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.jadventure.game.items.Item;
import com.jadventure.game.classes.Recruit;
import com.jadventure.game.classes.SewerRat;
import com.jadventure.game.navigation.Coordinate;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.navigation.LocationManager;
import com.jadventure.game.navigation.LocationType;
import com.jadventure.game.navigation.Location;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Player extends Entity {
    private ILocation location;
    
    public Player(){
        setBackpack(new ArrayList<Item>());
        Item milk = new Item("fmil1");
        addItemToBackpack(milk);
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
                ArrayList<String> items = new Gson().fromJson(json.get("items"), new TypeToken<ArrayList<String>>(){}.getType());
                ArrayList<Item> itemList = new ArrayList<Item>();
                for (String itemID : items) {
                   Item item = new Item(itemID);
                   itemList.add(item);
                }
                player.setBackpack(itemList);
            }
            Coordinate coordinate = new Coordinate(json.get("location").getAsString());
            player.setLocation(LocationManager.INSTANCE.getLocation(coordinate));

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
            player.setLocation(LocationManager.INSTANCE.getInitialLocation());
            return player;
            
        } else if(playerClass.equals("sewerrat")) {
            player = new SewerRat();
            player.setLocation(LocationManager.INSTANCE.getInitialLocation());
            return player;
        }
        return player;
    }

    public void addItem(Item i){
        
    }

    public void getStats(){
        System.out.println("Player name: " + getName() +
                            "\nHealth/Max: " + getHealth() + "/" + getHealthMax() +
                            "\nDamage/Armour: " + getDamage() + "/" + getArmour() +
                            "\n" + getName() + "'s level: " + getLevel());
    }

    public void printBackPack() {
        System.out.println("\n--------------------------------------------------------------------");
        System.out.println("Backpack: ");

        if (getBackpack().isEmpty()) {
            System.out.println("--Empty--");
        }
        else {
            for (Item item : getBackpack()) {
                System.out.println("- " + item.getName());
            }
        }    System.out.println("\n--------------------------------------------------------------------");
    }

    public void save() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", getName());
        jsonObject.addProperty("healthMax", getHealthMax());
        jsonObject.addProperty("health", getHealthMax());
        jsonObject.addProperty("armour", getArmour());
        jsonObject.addProperty("damage", getDamage());
        jsonObject.addProperty("level", getLevel());
        jsonObject.addProperty("weapon", getWeapon());
        ArrayList<String> items = new ArrayList<String>();
        JsonArray itemList = new JsonArray();
        for (Item item : getBackpack()) {
            JsonPrimitive itemJson = new JsonPrimitive(item.getItemID());
            itemList.add(itemJson);
        }
        jsonObject.add("items", itemList);
        Coordinate coordinate = getLocation().getCoordinate();
        String coordinateLocation = coordinate.x+","+coordinate.y+","+coordinate.z;
        jsonObject.addProperty("location", coordinateLocation);

        Gson gson = new Gson();
        String fileName = getProfileFileName(getName());
        new File(fileName).getParentFile().mkdirs();
        try {
            Writer writer = new FileWriter(fileName);
            gson.toJson(jsonObject, writer);
            writer.close();
            LocationManager.INSTANCE.writeLocations();
            Path origFile = Paths.get("json/locations.json");
            Path destFile = Paths.get("json/profiles/" + getName() + "/locations.json");
            Files.copy(origFile, destFile, StandardCopyOption.REPLACE_EXISTING);
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

    public void pickUpItem(String itemName) {
        ArrayList<Item> itemMap = searchItem(itemName, getLocation().getItems());
        if (!itemMap.isEmpty()) {
            Item item = itemMap.get(0);
            Item itemToPickUp = new Item(item.getItemID());
            addItemToBackpack(itemToPickUp);
            location.removePublicItem(itemToPickUp.getItemID());
        }
    }

    public void dropItem(String itemName) {
        ArrayList<Item> itemMap = searchItem(itemName, getBackpack());
        if (!itemMap.isEmpty()) {
            Item item = itemMap.get(0);
            Item itemToDrop = new Item(item.getItemID());
            removeItemFromBackpack(itemToDrop);
            location.addPublicItem(itemToDrop.getItemID());
        }
    }

    public void equipItem(String itemName) {
        ArrayList<Item> itemMap = searchItem(itemName, getBackpack());
        if (!itemMap.isEmpty()) {
            Item item = itemMap.get(0);
            setWeapon(item.getItemID());
        }
    }

    public void dequipItem(String itemName) {
        ArrayList<Item> itemMap = searchItem(itemName, getBackpack());
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
