package com.jadventure.game.navigation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jadventure.game.items.Item;

public class LocationTest {

	@Test
	public void newLocation() {
		Coordinate coordinate = new Coordinate(1, 1, 0);
		String title = "At the edge of a forest";
		String description = "The are many big trees and some tick busses, looks difficult to go through.";
		LocationType locationType = LocationType.FOREST;
		Location location = new Location(coordinate, title, description, locationType);
		
		assertEquals(coordinate, location.getCoordinate());
		assertEquals(title, location.getTitle());
		assertEquals(description, location.getDescription());
		assertEquals(locationType, location.getLocationType());
	}

	@Test
	public void placeItem() {
		Location cave = createLocation();
		
		Item bottle = new Item("bottle", "Wisky bottle",  "A pity it is empty", null);
		cave.dropItem(bottle);
		
		Item found = cave.pickItem(bottle.getName());
		
		assertEquals(bottle.getId(), found.getId());
	}

	public Location createLocation() {
		Coordinate coordinate = new Coordinate(1, 1, 0);
		String title = "In a dark cave";
		String description = "Quite cold and wet inside here";
		LocationType locationType = LocationType.CAVE;
		return new Location(coordinate, title, description, locationType);
	}
}
