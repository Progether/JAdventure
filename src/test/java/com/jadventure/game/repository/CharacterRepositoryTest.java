package com.jadventure.game.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import com.google.gson.stream.JsonReader;
import com.jadventure.game.entities.Player;

public class CharacterRepositoryTest {

	@Test
	public void load() {
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("characters.json");
		
		CharacterRepository characterRepo = new CharacterRepository();
		JsonReader reader = new JsonReader(new InputStreamReader(resourceAsStream));
		characterRepo.load(reader);
		
		Player sewerRat = characterRepo.getCharacter("sewer-rat");
		assertNotNull(sewerRat);
		assertEquals("sewer-rat", sewerRat.getId());
		assertEquals("Sewer Rat", sewerRat.getType());
		assertEquals(0, sewerRat.getArmour());
//		assertEquals(1.0, sewerRat.getDamage());
		assertEquals(1, sewerRat.getDexterity());
		assertEquals(100, sewerRat.getHealth());
		assertEquals(100, sewerRat.getHealthMax());
		assertEquals(2, sewerRat.getIntelligence());
		assertEquals(1, sewerRat.getLevel());
//		assertEquals(2, sewerRat.getLuck());
		assertTrue(sewerRat.getIntro().length() > 0);
		
	}

}
