package com.jadventure.game.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.items.Item;
import com.jadventure.game.navigation.Coordinate;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.navigation.Location;
import com.jadventure.game.navigation.LocationType;

public class WorldRepository extends AbstractRepository {
    private Map<Coordinate, ILocation> world = new HashMap<>();

    
    public WorldRepository(File repoPath) {
        super(repoPath, "world.json");
    }


    public ILocation getLocation(Coordinate coordinate) {
        return world.get(coordinate);
    }

    public void addLocation(ILocation location) {
        world.put(location.getCoordinate(), location);
    }

    
    protected void load(File repo) {
    	System.out.println("Loading World '" + repo + "'");
    	
        JsonParser parser = new JsonParser();

        try {
            Reader reader = new FileReader(repo);

            JsonObject json = parser.parse(reader).getAsJsonObject();

            // For every location in the locations.file, it parses the location uses loadLocation() and adds it
            // to the locations Map.
            for (Map.Entry<String, JsonElement> entry: json.entrySet()) {
                Coordinate coordinate = new Coordinate(entry.getKey());
				Location location = createLocation(entry.getValue().getAsJsonObject());
				System.out.println("Loaded '" + coordinate + "'");
				world.put(coordinate, location);
            }

            reader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Location createLocation(JsonObject json) {
        

        Coordinate coordinate = new Coordinate(json.get("coordinate").getAsString());
        String title = json.get("title").getAsString();
        String description = json.get("description").getAsString();
        LocationType locationType = LocationType.valueOf(json.get("locationType").getAsString());
        Location location = new Location(coordinate, title, description, locationType);
        if (json.has("items")) {
            List<String> items = new Gson().fromJson(json.get("items"), new TypeToken<List<String>>(){}.getType());
            location.setItems(items);
        } 
        else {
            List<String> items = new ArrayList<String>();
            location.setItems(items);
        }
        if (json.has("npcs")) {
            List<String> npcs = new Gson().fromJson(json.get("npcs"), new TypeToken<List<String>>(){}.getType());
            location.setNPCs(npcs);
        } 
        else {
            List<String> npcs = new ArrayList<String>();
            location.setNPCs(npcs);
        }
        return location;
    }
    
    
    public void save() {
        try {
            JsonObject jsonObject = new JsonObject();
            for (Map.Entry<Coordinate,ILocation> entry : world.entrySet()) {
                ILocation location = entry.getValue();
                JsonObject locationJsonElement = new JsonObject();
                locationJsonElement.addProperty("title", location.getTitle());
                locationJsonElement.addProperty("coordinate", location.getCoordinate().toString());
                locationJsonElement.addProperty("description", location.getDescription());
                locationJsonElement.addProperty("locationType", location.getLocationType().toString());
                JsonArray itemList = new JsonArray();
                List<Item> items = location.getItems();
                if (items.size() > 0) {
                    for (Item item : location.getItems()) {
                        JsonPrimitive itemJson = new JsonPrimitive(item.getId());
                        itemList.add(itemJson);
                    }
                    locationJsonElement.add("items", itemList);
                }
                jsonObject.add(location.getCoordinate().toString(), locationJsonElement);
            }
            Writer writer = new FileWriter("json/locations-save.json");
            Gson gson = new Gson();
            gson.toJson(jsonObject, writer);
            writer.close();
            QueueProvider.offer("The game locations were saved.");
        } catch (IOException ex) {
            QueueProvider.offer("Unable to save to file json/locations-save.json");
        }
    }


    // ----[ Can be removed once Dependency Injection is in place ]----
    private static WorldRepository worldRepo;
    public static WorldRepository createRepo() {
        if (worldRepo == null) {
            File file = new File(new File(System.getProperty("user.dir")), "json");

            File dataFile = new File(new File(file, "original_data"), "world.json");
            if (! dataFile.exists()) {
            	throw new RuntimeException("File '" + dataFile + "' does not exist.");
            }

            worldRepo = new WorldRepository(file);
            worldRepo.load();
        }
        return worldRepo;
    }

}
