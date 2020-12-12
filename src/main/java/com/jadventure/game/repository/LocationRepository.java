package com.jadventure.game.repository;

import com.jadventure.game.entities.NPC;
import com.jadventure.game.items.Item;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.navigation.Location;
import com.jadventure.game.navigation.LocationType;
import com.jadventure.game.navigation.Coordinate;
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
public class LocationRepository {
    private ItemRepository itemRepo = GameBeans.getItemRepository();
    private NpcRepository npcRepo = GameBeans.getNpcRepository();
    private String fileName;
    private Map<Coordinate, ILocation> locations;
    private static LocationRepository instance;

    public LocationRepository(String profileName) {
        locations = new HashMap<>();
        fileName = "json/profiles/" + profileName + "/locations.json";
        load();
    }

    public static LocationRepository createRepo(String profileName) {
        if ("".equals(profileName)) {
            return instance;
        }
        if (instance == null) {
            instance = new LocationRepository(profileName);
        } else if (!instance.getFileName().contains(profileName)) {
            instance = new LocationRepository(profileName);
        }
        return instance;
    }

    private String getFileName() {
        return fileName;
    }

    private void load() {
        JsonParser parser = new JsonParser();
        File f = new File(fileName);
        if (!f.exists()) {
            copyLocationsFile();
        }
        try {
            Reader reader = new FileReader(fileName);
            JsonObject json = parser.parse(reader).getAsJsonObject();
            for(Map.Entry<String, JsonElement> entry: json.entrySet()) {
                locations.put(new Coordinate(entry.getKey()), loadLocation(entry.getValue().getAsJsonObject()));
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ILocation loadLocation(JsonObject json) {
        Coordinate coordinate =
            new Coordinate(json.get("coordinate").getAsString());
        String title = json.get("title").getAsString();
        String description = json.get("description").getAsString();
        LocationType locationType =
            LocationType.valueOf(json.get("locationType").getAsString());
        ILocation location = new Location(coordinate, title, description,
                locationType);
        location.setDangerRating(json.get("danger").getAsInt());
        if (json.has("items")) {
            List<String> items = new Gson().fromJson(json.get("items"),
                    new TypeToken<List<String>>(){}.getType());
            for (String id : items) {
                location.addItem(itemRepo.getItem(id));
            }
        }
        if (json.has("npcs")) {
            List<String> npcs = new Gson().fromJson(json.get("npcs"),
                    new TypeToken<List<String>>(){}.getType());
            for (String npc : npcs) {
                location.addNpc(npcRepo.getNpc(npc));
            }
        }
        return location;
    }

    public void writeLocations() {
        try {
            JsonObject jsonObject = new JsonObject();
            for (Map.Entry<Coordinate,ILocation> entry : locations.entrySet()) {
                ILocation location = entry.getValue();
                JsonObject locationJsonElement = new JsonObject();
                locationJsonElement.addProperty("title", location.getTitle());
                locationJsonElement.addProperty("coordinate",
                        location.getCoordinate().toString());
                locationJsonElement.addProperty("description",
                        location.getDescription());
                locationJsonElement.addProperty("locationType",
                        location.getLocationType().toString());
                locationJsonElement.addProperty("danger",
                        String.valueOf(location.getDangerRating()));
                JsonArray itemList = new JsonArray();
                List<Item> items = location.getItems();
                if (items.size() > 0) {
                    for (Item item : items) {
                        JsonPrimitive itemJson =
                            new JsonPrimitive(item.getId());
                        itemList.add(itemJson);
                    }
                    locationJsonElement.add("items", itemList);
                }
                JsonArray npcList = new JsonArray();
                List<NPC> npcs = location.getNpcs();
                if (npcs.size() > 0) {
                    for (NPC npc : npcs) {
                        JsonPrimitive npcJson = new JsonPrimitive(npc.getId());
                        npcList.add(npcJson);
                    }
                    locationJsonElement.add("npcs", npcList);
                }
                jsonObject.add(location.getCoordinate().toString(),
                        locationJsonElement);
            }
            Writer writer = new FileWriter(fileName);
            Gson gson = new Gson();
            gson.toJson(jsonObject, writer);
            writer.close();
            QueueProvider.offer("The game locations were saved.");
        } catch (IOException ex) {
            QueueProvider.offer("Unable to save to file " + fileName);
        }
    }

    public ILocation getInitialLocation() {
        String profileName = fileName.split("/")[2];
        instance = null;
        LocationRepository.createRepo(profileName);
        load();
        Coordinate coordinate = new Coordinate(0, 0, -1);
        return getLocation(coordinate);
    }

    public ILocation getLocation(Coordinate coordinate) {
        if (coordinate == null) {
            return null;
        }
        if (!locations.containsKey(coordinate)) {
            throw new RepositoryException("Argument 'coordinate' with value '" + coordinate.toString() + "' not found in repository");
        }
        return locations.get(coordinate);
    }

    private void copyLocationsFile() {
        File source = new File("json/original_data/locations.json");
        File dest = new File(fileName);
        dest.mkdirs();
        try {
        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addLocation(ILocation location) {
        locations.put(location.getCoordinate(), location);
    }
}
