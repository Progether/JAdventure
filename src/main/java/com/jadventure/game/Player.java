package com.jadventure.game;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class Player extends Entity {
    protected static String getProfileFileName(String name) {
        return "json/profiles/" + name + "/" + name + "_profile.json";
    }

    public static boolean profileExists(String name) {
        File file = new File(getProfileFileName(name));
        return file.exists();
    }

    public static Player load(String name) {
        Player player = null;

        Gson gson = new Gson();
        String fileName = getProfileFileName(name);
        try {
            Reader reader = new FileReader(fileName);
            player = gson.fromJson(reader, Player.class);
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println( "Unable to open file '" + fileName + "'.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return player;
    }

    public Player(){
        // this needs to be worked on, these values should have a default value
        // set for the begining of the game.
        /*
        Random randGen = new Random();
        int healthMax = 20, armour = 1, rand;
        double damage = 0;
        for (int i = 0; i < 120; i++) {
         rand = randGen.nextInt(3);
         if (rand == 0) {
             healthMax++;
         } else if (rand == 1) {
             armour++;
         } else {
             double damageIncrement = (randGen.nextDouble() * 2.2);
             damage += damageIncrement;
         }
        }
        //	round damage
        damage = Math.round(damage * 4);
        damage /= 4; //	Wasn't working above... (always x.0)
        */
        this.healthMax = 100;
        this.armour = 1;
        this.damage = 50;
        this.level = 8;
    }

    public void save() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", this.name);
        jsonObject.addProperty("healthMax", this.healthMax);
        jsonObject.addProperty("armour", this.armour);
        jsonObject.addProperty("damage", this.damage);
        jsonObject.addProperty("level", this.level);

        Gson gson = new Gson();
        String fileName = getProfileFileName(this.name);
        new File(fileName).getParentFile().mkdirs();
        try {
            Writer writer = new FileWriter(fileName);
            gson.toJson(jsonObject, writer);
            writer.close();
            System.out.println("Your game data was saved.");
        } catch (IOException ex) {
            System.out.println("Unable to save to file '" + fileName + "'.");
        }
    }
}
