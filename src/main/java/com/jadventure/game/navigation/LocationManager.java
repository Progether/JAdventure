package com.jadventure.game.navigation;

import com.jadventure.game.items.Item;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

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

    private Map<Coordinate, ILocation> locations = new HashMap<Coordinate, ILocation>();

    private LocationManager() {
        JsonParser parser = new JsonParser();

        try {
            Reader reader = new FileReader(FILE_NAME);

            JsonObject json = parser.parse(reader).getAsJsonObject();

            for(Map.Entry<String, JsonElement> entry: json.entrySet()) {
                locations.put(new Coordinate(entry.getKey()), loadLocation(entry.getValue().getAsJsonObject()));
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

        Coordinate coordinate = new Coordinate(json.get("coordinate").getAsString());
        location.setCoordinate(coordinate);
        location.setTitle(json.get("title").getAsString());
        location.setDescription(json.get("description").getAsString());
        location.setLocationType(LocationType.valueOf(json.get("locationType").getAsString()));
        if (json.has("items")) {
            ArrayList<String> items = new Gson().fromJson(json.get("items"), new TypeToken<List<String>>(){}.getType());
            location.setItems(items);
        } else {
            ArrayList<String> items = new ArrayList<String>();
            location.setItems(items);
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
                locationJsonElement.addProperty("coordinate", location.getCoordinate().toString());
                locationJsonElement.addProperty("description", location.getDescription());
                locationJsonElement.addProperty("locationType", location.getLocationType().toString());
                JsonArray itemList = new JsonArray();
                ArrayList<Item> items = location.getItems();
                if (items.size() > 0) {
                    for (Item item : location.getItems()) {
                        JsonPrimitive itemJson = new JsonPrimitive(item.getItemID());
                        itemList.add(itemJson);
                    }
                    locationJsonElement.add("items", itemList);
                }
                jsonObject.add(location.getCoordinate().toString(), locationJsonElement);
            }
            Writer writer = new FileWriter("json/locations.json");
            Gson gson = new Gson();
            gson.toJson(jsonObject, writer);
            writer.close();
            System.out.println("Your game data was saved.");
        } catch (IOException ex) {
            System.out.println("Unable to save to file json/locations.json");
        }
    }

    public ILocation getInitialLocation() {
        Coordinate coordinate = new Coordinate(0, 0, -1);
        return getLocation(coordinate);
    }

    public ILocation getLocation(Coordinate coordinate) {
        return locations.get(coordinate);
    }

}
