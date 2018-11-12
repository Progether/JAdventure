package com.jadventure.game.repository;

import com.jadventure.game.entities.NPC;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class NpcRepositoryTest {

    NpcRepository npcRepository;

    @Before
    public void setUp() {
        npcRepository = NpcRepository.createRepo();
        assertNotNull(npcRepository);
    }

    @Test
    public void getNpc() {
        NPC guide = npcRepository.getNpc("guide");
        assertNotNull(guide);
        assertEquals("Guide", guide.getName());
    }

    @Test
    public void getNpcWithZeroGold() {
        NPC npcWithZeroGold = npcRepository.getNpc("recruit");
        assertNotNull(npcWithZeroGold);
        assertEquals( 0, npcWithZeroGold.getGold());
    }

    @Test
    public void getNpcWithMoreThanZeroGold() {
        NPC npcWithZeroGold = npcRepository.getNpc("syndicatemember");
        assertNotNull(npcWithZeroGold);
        assertTrue( npcWithZeroGold.getGold() > 0);
    }

    @Test
    public void getNpcWithNoItems() {
        NPC npcWithNoItems = npcRepository.getNpc("sewerrat");
        assertEquals(0, npcWithNoItems.getStorage().getItems().size());
    }

    @Test
    public void getNpcWithItems() {
        NPC npcWithItems = npcRepository.getNpc("syndicatemember");
        assertTrue(npcWithItems.getStorage().getItems().size() > 0);
    }

    @Test(expected = RepositoryException.class)
    public void getNpcThatDoesNotExists() {
        npcRepository.getNpc("nonExistingNpc");
    }

}
