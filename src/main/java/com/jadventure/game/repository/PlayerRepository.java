package com.jadventure.game.repository;

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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.jadventure.game.Game;
import com.jadventure.game.GameBeans;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.classes.Recruit;
import com.jadventure.game.classes.SewerRat;
import com.jadventure.game.entities.Player;
import com.jadventure.game.items.Item;
import com.jadventure.game.items.ItemStack;
import com.jadventure.game.items.Storage;
import com.jadventure.game.navigation.Coordinate;

public class PlayerRepository {
    private static LocationRepository locationRepo = GameBeans.getLocationRepository();
    private static ItemRepository itemRepo = GameBeans.getItemRepository();
    private Player player;

    
    public Player create(String type) {
        if (type.equals("recruit")){
            // Instead of having a huge constructor, this is much more readable.
            player = new Recruit();
            setUpVariables(player);
            return player;
        }
        else if (type.equals("sewerrat")) {
            player = new SewerRat();
            setUpVariables(player);
            return player;
        }
        return player;
    }

    private static void setUpVariables(Player player) {
        player.setLocation(locationRepo.getLocation(Game.DEFAULT_INITIAL_COORDINATE));
        float maxWeight = (float)Math.sqrt(player.getStrength() * 300);
        player.setStorage(new Storage(null, maxWeight));
        player.addItemToStorage(itemRepo.getItem("fmil1"));
    }
    
    
    public Player getPlayer() {
        return player;
    }
    public Player getPlayer(String name) {
        load(name);
        return getPlayer();
    }
    
    public void save() {
        save(player);
    }


    protected String getProfileFileName(String name) {
        return "json/profiles/" + name + "/" + name + "_profile.json";
    }
    /**
     * Returns {@code true} if the given player name, is a previously saved game,
     * otherwise {@code false}.
     * 
     * @param name - The name of the player (as used in a previous game)
     * @return {@code true} if the given player name, is a previously saved game,
     * otherwise {@code false}.
     */
    public boolean isExistingPlayerProfile(String name) {
        File file = new File(getProfileFileName(name));
        return file.exists();
    }

    public void load(String name) {
        System.out.println("Loading player data '" + name + "'");
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
            player.setStrength(json.get("strength").getAsInt());
            player.setStrength(json.get("intelligence").getAsInt());
            player.setStrength(json.get("dexterity").getAsInt());
            player.setStrength(json.get("luck").getAsInt());
            player.setStrength(json.get("stealth").getAsInt());
            player.setWeapon(json.get("weapon").getAsString());
            if (json.has("items")) {
                HashMap<String, Integer> items = new Gson().fromJson(json.get("items"), new TypeToken<HashMap<String, Integer>>(){}.getType());
                ArrayList<ItemStack> itemList = new ArrayList<ItemStack>();
                for (Map.Entry<String, Integer> entry : items.entrySet()) {
                    String itemID = entry.getKey();
                    int amount = entry.getValue();
                    Item item = itemRepo.getItem(itemID);
                    ItemStack itemStack = new ItemStack(amount, item);
                    itemList.add(itemStack);
                }
                float maxWeight = (float)Math.sqrt(player.getStrength()*300);
                player.setStorage(new Storage(itemList, maxWeight));
            }
            Path orig = Paths.get("json/profiles/" + name + "/locations.json");
            Path dest = Paths.get("json/locations.json");
            Files.copy(orig, dest, StandardCopyOption.REPLACE_EXISTING);
            Coordinate coordinate = new Coordinate(json.get("location").getAsString());
            player.setLocation(locationRepo.getLocation(coordinate));
            reader.close();
        } catch (FileNotFoundException ex) {
            QueueProvider.offer( "Unable to open file '" + fileName + "'.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.player = player;
    }

    
    public void save(Player player) {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", player.getName());
        jsonObject.addProperty("healthMax", player.getHealthMax());
        jsonObject.addProperty("health", player.getHealthMax());
        jsonObject.addProperty("armour", player.getArmour());
        jsonObject.addProperty("damage", player.getDamage());
        jsonObject.addProperty("level", player.getLevel());
        jsonObject.addProperty("strength", player.getStrength());
        jsonObject.addProperty("intelligence", player.getIntelligence());
        jsonObject.addProperty("dexterity", player.getDexterity());
        jsonObject.addProperty("luck", player.getLuck());
        jsonObject.addProperty("stealth", player.getStealth());
        jsonObject.addProperty("weapon", player.getWeapon());
        HashMap<String, Integer> items = new HashMap<String, Integer>();
        JsonArray itemList = new JsonArray();
        for (ItemStack item : player.getStorage().getItems()) {
            items.put(item.getItem().getItemID(), item.getAmount());
            JsonPrimitive itemJson = new JsonPrimitive(item.getItem().getItemID());
            itemList.add(itemJson);
        }
        JsonElement itemsJsonObj = gson.toJsonTree(items);
        jsonObject.add("items", itemsJsonObj);
        Coordinate coordinate = player.getLocation().getCoordinate();
        String coordinateLocation = coordinate.x + "," + coordinate.y + "," + coordinate.z;
        jsonObject.addProperty("location", coordinateLocation);

        String fileName = getProfileFileName(player.getName());
        new File(fileName).getParentFile().mkdirs();
        try {
            Writer writer = new FileWriter(fileName);
            gson.toJson(jsonObject, writer);
            writer.close();
            locationRepo.save();
            Path orig = Paths.get("json/locations.json");
            Path dest = Paths.get("json/profiles/" + player.getName() + "/locations.json");
            Files.copy(orig, dest, StandardCopyOption.REPLACE_EXISTING);
            QueueProvider.offer("\nYour game data was saved.");
        } 
        catch (IOException ex) {
            QueueProvider.offer("\nUnable to save to file '" + fileName + "'.");
        }
    }

    private static PlayerRepository playerRepo;
    public static PlayerRepository createRepo() {
        if (playerRepo == null) {
//            File file = new File(new File(System.getProperty("user.dir")), "json");
            playerRepo = new PlayerRepository();
//            playerRepo.load();
        }
        return playerRepo;
    }

}
