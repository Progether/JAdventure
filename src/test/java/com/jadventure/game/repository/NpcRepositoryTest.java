package com.jadventure.game.repository;

import com.jadventure.game.entities.NPC;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class NpcRepositoryTest {

    @Test
    public void createRepo() {
        NpcRepository npcRepo = NpcRepository.createRepo();
        assertNotNull(npcRepo);
    }
    
    @Test
    public void getNpc() {
        NpcRepository npcRepo = NpcRepository.createRepo();
        NPC guide = npcRepo.getNpc("guide");
        assertNotNull(guide);
        assertEquals("Guide", guide.getName());
    }

}
