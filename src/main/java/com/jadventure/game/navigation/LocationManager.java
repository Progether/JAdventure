package com.jadventure.game.navigation;

import com.jadventure.game.entities.NPC;
import com.jadventure.game.items.Item;
import com.jadventure.game.repository.ItemRepository;
import com.jadventure.game.GameBeans;
import com.jadventure.game.QueueProvider;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * This class loads the locations from the locations.json file on start.
 * It also provides methods for getting the initial location and the current location.
 */
public class LocationManager {
    private static ItemRepository itemRepo = GameBeans.getItemRepository();
    private static LocationManager instance = null;
    private String fileName;

    private static class Locations {
        private static Map<Coordinate, ILocation> locations = new HashMap<Coordinate, ILocation>();
    }

    private LocationManager(String profileName) {
        fileName = "json/profiles/" + profileName + "/locations.json";
        JsonParser parser = new JsonParser();
        File f = new File(fileName);
        if (!f.exists()) {
            copyLocationsFile();
        }
        try {
            Reader reader = new FileReader(fileName);
            JsonObject json = parser.parse(reader).getAsJsonObject();
            for(Map.Entry<String, JsonElement> entry: json.entrySet()) {
                Locations.locations.put(new Coordinate(entry.getKey()), loadLocation(entry.getValue().getAsJsonObject()));
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LocationManager getInstance(String profileName) {
        if (instance == null) {
            instance = new LocationManager(profileName);
        }
        return instance;
    }

    private Location loadLocation(JsonObject json) {
        Location location = new Location();
        Coordinate coordinate = new Coordinate(json.get("coordinate").getAsString());
        location.setCoordinate(coordinate);
        location.setTitle(json.get("title").getAsString());
        location.setDescription(json.get("description").getAsString());
        location.setLocationType(LocationType.valueOf(json.get("locationType").getAsString()));
        location.setDangerRating(json.get("danger").getAsInt());
        if (json.has("items")) {
            List<String> items = new Gson().fromJson(json.get("items"), new TypeToken<List<String>>(){}.getType());
            for (String id : items) {
                location.addItem(itemRepo.getItem(id));
            }
        }
        if (json.has("npcs")) {
            List<String> npcs = new Gson().fromJson(json.get("npcs"), new TypeToken<List<String>>(){}.getType());
            for (String npc : npcs) {
                location.addNpc(npc);
            }
        }
        return location;
    }

    public static void writeLocations(String profileName) {
        try {
            JsonObject jsonObject = new JsonObject();
            for (Map.Entry<Coordinate,ILocation> entry : Locations.locations.entrySet()) {
                ILocation location = entry.getValue();
                JsonObject locationJsonElement = new JsonObject();
                locationJsonElement.addProperty("title", location.getTitle());
                locationJsonElement.addProperty("coordinate", location.getCoordinate().toString());
                locationJsonElement.addProperty("description", location.getDescription());
                locationJsonElement.addProperty("locationType", location.getLocationType().toString());
                locationJsonElement.addProperty("danger", String.valueOf(location.getDangerRating()));
                JsonArray itemList = new JsonArray();
                List<Item> items = location.getItems();
                if (items.size() > 0) {
                    for (Item item : items) {
                        JsonPrimitive itemJson = new JsonPrimitive(item.getId());
                        itemList.add(itemJson);
                    }
                    locationJsonElement.add("items", itemList);
                }
                JsonArray npcList = new JsonArray();
                List<NPC> npcs = location.getNPCs();
                if (npcs.size() > 0) {
                    for (NPC npc : npcs) {
                        JsonPrimitive npcJson = new JsonPrimitive(npc.getId());
                        npcList.add(npcJson);
                    }
                    locationJsonElement.add("npcs", npcList);
                }
                jsonObject.add(location.getCoordinate().toString(), locationJsonElement);
            }
            Writer writer = new FileWriter("json/profiles/" + profileName + "/locations.json");
            Gson gson = new Gson();
            gson.toJson(jsonObject, writer);
            writer.close();
            QueueProvider.offer("The game locations were saved.");
        } catch (IOException ex) {
            QueueProvider.offer("Unable to save to file json/profiles/" + profileName + "/locations.json");
        }
    }

    public static ILocation getInitialLocation(String profileName) {
        getInstance(profileName);
        instance.reload();
        Coordinate coordinate = new Coordinate(0, 0, -1);
        return getLocation(coordinate);
    }

    public static ILocation getLocation(Coordinate coordinate) {
        return Locations.locations.get(coordinate);
    }

    private void reload() {
        JsonParser parser = new JsonParser();
        File f = new File(fileName);
        if (!f.exists()) {
            copyLocationsFile();
        }
        try {
            Reader reader = new FileReader(fileName);
            JsonObject json = parser.parse(reader).getAsJsonObject();
            for(Map.Entry<String, JsonElement> entry: json.entrySet()) {
                Locations.locations.put(new Coordinate(entry.getKey()), loadLocation(entry.getValue().getAsJsonObject()));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyLocationsFile() {
        File source = new File("json/locations.json");
        File dest = new File(fileName);
        dest.mkdirs();
        try {
        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
