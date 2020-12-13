package com.jadventure.game.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;

import com.jadventure.game.navigation.Coordinate;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.navigation.Location;
import com.jadventure.game.navigation.LocationType;

public class LocationRepositoryTest {
    LocationRepository locationRepo;

    @Before
    public void setUp() {
        locationRepo = new LocationRepository("test");
    }

    @Test
    public void testGetLocation() {
        Coordinate coordinate = new Coordinate(0,0,-2);
        String title = "test location";
        String description = "You are in a massive empty room";
        LocationType locationType = LocationType.CAVE;
        ILocation location = new Location(coordinate, title, description, locationType);
        locationRepo.addLocation(location);
        assertEquals(location, locationRepo.getLocation(coordinate));
    }

    @Test(expected = RepositoryException.class)
    public void getNonExistingLocation() {
        Coordinate coordinate = new Coordinate(0,0,-2);
        locationRepo.getLocation(coordinate);
    }

    @Test
    public void getLocationExpectADarkCave() {
        Coordinate coordinate = new Coordinate(7,2,-1);
        ILocation location = locationRepo.getLocation(coordinate);
        assertEquals("Cave", location.getTitle());
        assertEquals("A dark cave", location.getDescription());
        assertEquals(LocationType.CAVE, location.getLocationType());
        assertEquals(4, location.getDangerRating());
    }
}
