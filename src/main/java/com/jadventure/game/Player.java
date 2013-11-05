package com.jadventure.game;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Player extends Entity {
    protected static String getProfileFileName(String name) {
        return "json/profiles/" + name + "/" + name + "_profile.json";
    }

    public static boolean profileExists(String name) {
        File file = new File(getProfileFileName(name));
        return file.exists();
    }

    public static Player load(String name) {
        Gson gson = new Gson();
        String fileName = getProfileFileName(name);
        try {
            return gson.fromJson(new FileReader(fileName), Player.class);
        } catch (FileNotFoundException ex) {
            System.out.println( "Unable to open file '" + fileName + "'.");
            return null;
        }
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

    // PlayerStub class is used to save only needed fields
    protected class PlayerStub {
        String name;
        int healthMax;
        int armour;
        double damage;
        int level;

        protected PlayerStub() {
            this.name = Player.this.name;
            this.healthMax = Player.this.healthMax;
            this.armour = Player.this.armour;
            this.damage = Player.this.damage;
            this.level = Player.this.level;
        }
    }

    public void save() {
        Gson gson = new Gson();
        String fileName = getProfileFileName(this.name);
        new File(fileName).getParentFile().mkdirs();
        try {
            gson.toJson(new PlayerStub(), new FileWriter(fileName));
            System.out.println("Your game data was saved.");
        } catch (IOException ex) {
            System.out.println("Unable to save to file '" + fileName + "'.");
        }
    }
}
