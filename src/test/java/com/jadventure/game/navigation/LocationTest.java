package com.jadventure.game.navigation;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

public class LocationTest {

	@Test
	public void createLocation() {
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
}
