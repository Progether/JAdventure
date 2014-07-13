package com.jadventure.game.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import com.google.gson.stream.JsonReader;
import com.jadventure.game.monsters.Monster;

public class MonsterRepositoryTest {

	@Test
	public void load() {
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("monsters.json");
		
		MonsterRepository monsterRepo = new MonsterRepository();
		JsonReader reader = new JsonReader(new InputStreamReader(resourceAsStream));
		monsterRepo.load(reader);
		
		Monster monster = monsterRepo.getMonster("troll");
		assertNotNull(monster);
		assertEquals("troll", monster.getId());
		assertEquals("Troll", monster.getName());
		assertEquals(1, monster.getArmour());
//		assertEquals(1.0, sewerRat.getDamage());
		assertEquals(0, monster.getDexterity());
		assertEquals(50, monster.getHealth());
		assertEquals(50, monster.getHealthMax());
		assertEquals(0, monster.getIntelligence());
		assertEquals(0, monster.getLevel());
//		assertEquals(2, sewerRat.getLuck());
		
	}

}
