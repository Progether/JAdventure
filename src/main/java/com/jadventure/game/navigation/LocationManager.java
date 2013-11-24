package com.jadventure.game.navigation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Cage
 * Date: 23/11/13
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public enum LocationManager {
    INSTANCE;
    private static final String FILE_NAME = "json/locations.json";

    private Map<Integer, ILocation> locations = new HashMap<Integer, ILocation>();

    private LocationManager() {
        JsonParser parser = new JsonParser();

        try {
            Reader reader = new FileReader(FILE_NAME);

            JsonObject json = parser.parse(reader).getAsJsonObject();
            for(Map.Entry<String, JsonElement> entry: json.entrySet()) {
                locations.put(Integer.valueOf(entry.getKey()), loadLocation(entry.getValue().getAsJsonObject()));
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to load game locations.");
            ex.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Location loadLocation(JsonObject json) {
        Location location = new Location();

        location.setId(json.get("id").getAsInt());
        location.setTitle(json.get("title").getAsString());
        location.setDescription(json.get("description").getAsString());
        location.setLocationType(LocationType.valueOf(json.get("locationType").getAsString()));

        JsonObject exits = json.get("exits").getAsJsonObject();
        for(Map.Entry<String, JsonElement> entry: exits.entrySet()) {
            location.getExits().put(Direction.valueOf(entry.getKey()), loadExit(entry.getValue().getAsJsonObject()));
        }

        return location;
    }

    private Exit loadExit(JsonObject json) {
        Exit exit = new Exit();

        exit.setDescription(json.get("description").getAsString());
        exit.setLocation(new LocationProxy(json.get("location").getAsInt()));

        return exit;
    }

    public ILocation getInitialLocation() {
        return getLocation(1);
    }

    public ILocation getLocation(int id) {
        return locations.get(id);
    }
}
