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
 * Time: 10:19 PM
 * To change this template use File | Settings | File Templates.
 */
public final class Exit {
    private String description;
    private ILocation location;
    // We can implement more functionality on the exits later, for example locked exits, or requisites to pass through

    public Exit(Coordinate coordinate, Direction direction) {
        String json;
        String coordinateString;
        Coordinate testCoordinate;
        
        if (direction == Direction.NORTH) {
            coordinateString = coordinate.x+","+Integer.toString(Integer.parseInt(coordinate.y)+1)+","+coordinate.z;
            testCoordinate = new Coordinate(coordinateString);
            json = getCoordinate(testCoordinate);
            if (json != null) {
                this.description = json;
            }
        } else if (direction == Direction.SOUTH) {
            coordinateString = coordinate.x+","+Integer.toString(Integer.parseInt(coordinate.y)-1)+","+coordinate.z;
            testCoordinate = new Coordinate(coordinateString);
            json = getCoordinate(testCoordinate);
            if (json != null) {
                this.description = json;
            }
        } else if (direction == Direction.EAST) {
            coordinateString = Integer.toString(Integer.parseInt(coordinate.x)+1)+","+coordinate.y+","+coordinate.z;
            testCoordinate = new Coordinate(coordinateString);
            json = getCoordinate(testCoordinate);
            if (json != null) {
                this.description = json;
            }
        } else if (direction == Direction.WEST) {
            coordinateString = Integer.toString(Integer.parseInt(coordinate.x)-1)+","+coordinate.y+","+coordinate.z;
            testCoordinate = new Coordinate(coordinateString);
            json = getCoordinate(testCoordinate);
            json = getCoordinate(coordinate);
            if (json != null) {
                this.description = json;
            }
        } else {
            System.out.println("No direction found");
        }
    }

    public String getCoordinate(Coordinate coordinate) {
        JsonParser parser = new JsonParser();
        String response = null;
        
        try {
            Reader reader = new FileReader("json/locations.json");
            JsonObject json = parser.parse(reader).getAsJsonObject();
            String coordinateString = coordinate.x+","+coordinate.y+","+coordinate.z;
            for(Map.Entry<String, JsonElement> entry: json.entrySet()) {
                if ((entry.getKey()).equals(coordinateString)) {
                    JsonObject filteredJson = entry.getValue().getAsJsonObject();
                    response = filteredJson.get("description").getAsString();
                    return response;
                }
            }
            reader.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException e) {
        }
        return response;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ILocation getLocation() {
        return this.location;
    }

    public void setLocation(ILocation location) {
        this.location = location;
    }

    public void print(Direction direction) {
        System.out.println(direction.getDescription() + " " + getDescription());
    }
}
