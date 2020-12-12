package com.jadventure.game.repository;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

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
        if (!repoFile.exists()) {
            throw new RuntimeException("Could not find NPC Repository");
        } else {
            JsonObject jsonEncNpcs = new JsonObject();
            try {
                JsonReader reader = new JsonReader(new FileReader(repoFile));
                JsonParser parser = new JsonParser();
                JsonObject json = parser.parse(reader).getAsJsonObject();
                jsonEncNpcs = json.get("npcs").getAsJsonObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}