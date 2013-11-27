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
    private JsonObject json;

    private Map<String, ILocation> locations = new HashMap<String, ILocation>();

    private LocationManager() {
        JsonParser parser = new JsonParser();

        try {
            Reader reader = new FileReader(FILE_NAME);

            JsonObject json = parser.parse(reader).getAsJsonObject();
            this.json = json;
            /*
            for(Map.Entry<String, JsonElement> entry: json.entrySet()) {
                locations.put(entry.getKey(), loadLocation(entry.getValue().getAsJsonObject()));
            }
            */
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to load game locations.");
            ex.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.json = json;
    }

    private Location loadLocation(JsonObject json) {
        Location location = new Location();

        Coordinate coordinateString = new Coordinate(json.get("coordinate").getAsString());
        location.setCoordinate(coordinateString);
        location.setTitle(json.get("title").getAsString());
        location.setDescription(json.get("description").getAsString());
        location.setLocationType(LocationType.valueOf(json.get("locationType").getAsString()));
        location.getExits().put(Direction.NORTH, loadExit(coordinateString, Direction.NORTH));
        location.getExits().put(Direction.SOUTH, loadExit(coordinateString, Direction.SOUTH));
        location.getExits().put(Direction.EAST, loadExit(coordinateString, Direction.EAST));
        location.getExits().put(Direction.WEST, loadExit(coordinateString, Direction.WEST));

        return location;
    }
    
    private Exit loadExit(Coordinate coordinate, Direction direction) {
        String coordinateString = coordinate.x+","+coordinate.y+","+coordinate.z;
        Exit exit = new Exit(coordinate, direction);
        
        return exit;
    }

    public ILocation getInitialLocation() {
        Coordinate coordinate = new Coordinate("0,0,0");
        return getLocation(coordinate);
    }

    public ILocation getLocation(Coordinate coordinate) {
        String coordinateString = coordinate.x+","+coordinate.y+","+coordinate.z;
        Location location = loadLocation(this.json.get(coordinateString).getAsJsonObject());
        return location;
    }
}
