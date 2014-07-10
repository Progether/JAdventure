package com.jadventure.game.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.jadventure.game.navigation.Coordinate;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.navigation.Location;
import com.jadventure.game.navigation.LocationType;

public class WorldRepositoryTest {

	@Test
	public void singleLocation() throws IOException {
		File worldFile = File.createTempFile("test-world", "json");
		WorldRepository worldRepo = new WorldRepository(worldFile);
		
		worldRepo.addLocation(createLocation());
		
		ILocation location = worldRepo.getLocation(new Coordinate("1,1,0"));
		
		assertNotNull(location);
		assertEquals("Shed", location.getTitle());
	}

	private ILocation createLocation() {
		Coordinate coordinate = new Coordinate(1, 1, 0);
		String title = "Shed";
		String description = "There is a shed under the trees.";
		LocationType locationType = LocationType.FOREST;
		return new Location(coordinate, title, description, locationType);
	}
}
