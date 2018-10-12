package com.jadventure.game.repository;

import com.jadventure.game.entities.NPC;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EncounteredNpcRepositoryTest {

    @Test
    public void createRepo() {
        NpcRepository npcRepo = EncounteredNpcRepository.createRepo();
        assertNotNull(npcRepo);
    }
    
    @Test
    public void getNpc() {
        NpcRepository npcRepo = EncounteredNpcRepository.createRepo();
        NPC guide = npcRepo.getNpc("guide");
        assertNotNull(guide);
        assertEquals("Guide", guide.getName());
    }

}
