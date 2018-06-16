package com.jadventure.game.repository;

import com.jadventure.game.entities.NPC;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EncounteredNpcRepository extends NpcRepository{
    static String fileName;
    
    private static NpcRepository npcRepository = null;
    public static NpcRepository createRepo(String profileName) {
        if (npcRepository == null) {
            File repoFile = new File("json/profiles/" + profileName + "/encNpcs.json");
            if (!repoFile.exists()) {
	            throw new RuntimeException("File" + repoFile + "does not exist");
            }
            npcRepository = new NpcRepository();
            npcRepository.load(repoFile);
        }
        return npcRepository;
    }
    
    public static void addNpc(String profileName, String name, int health, String id) {
        EncounteredNpcRepository repo = new EncounteredNpcRepository();
        File repoFile = new File("json/profiles/" + profileName + "/encNpcs.json");
        repo.load(repoFile);
        
    }
}