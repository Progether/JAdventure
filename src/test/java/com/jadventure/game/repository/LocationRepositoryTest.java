package com.jadventure.game.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Before;

import com.jadventure.game.navigation.Coordinate;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.navigation.Location;
import com.jadventure.game.navigation.LocationType;
import com.jadventure.game.GameBeans;

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
}
