package com.jadventure.game.navigation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jadventure.game.items.Item;
import com.jadventure.game.entities.NPC;

public class LocationTest {

	@Test
	public void newLocation() {
		Coordinate coordinate = new Coordinate(1, 1, 0);
		String title = "At the edge of a forest";
		String description = "The are many big trees and some tick busses, " +
            "looks difficult to go through.";
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
		
		Item bottle = new Item("bottle", "tool", "bottle",  "old bottle", 1, null);
		cave.addItem(bottle);
		
		Item found = cave.removeItem(bottle);
		
		assertEquals(bottle.getId(), found.getId());
	}

	public Location createLocation() {
		Coordinate coordinate = new Coordinate(1, 1, 0);
		String title = "In a dark cave";
		String description = "Quite cold and wet inside here";
		LocationType locationType = LocationType.CAVE;
		return new Location(coordinate, title, description, locationType);
	}

	@Test
	public void testRemove_NPC() {
        Location location = createLocation();
        NPC npc = new NPC("test");
        location.addNpc(npc);
        assertEquals(1, location.getNpcs().size());
        location.remove(npc);
        assertEquals(0, location.getNpcs().size());
	}
}
