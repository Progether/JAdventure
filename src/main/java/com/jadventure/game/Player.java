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
import java.util.ArrayList;

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
        this.healthMax = 100;
        this.health = 100;
        this.armour = 1;
        this.damage = 50;
        this.level = 1;
        
        this.backpack = new ArrayList<Item>();
        Item milk = new Item(1);
        this.backpack.add(milk);
    }

    public void getStats(){
        System.out.println("Player name: " + name +
                            "\nHealth/Max: " + health + "/" + healthMax +
                            "\nDamage/Armour: " + damage + "/" + armour +
                            "\n" + name + "'s level: " + level);
    }

    public void getBackPack() {
        System.out.println("\n--------------------------------------------------------------------\n");
        System.out.println("Backpack: ");

        if (this.backpack.isEmpty()) {
            System.out.println("--Empty--");
        }
        else {
            for (Item item : this.backpack) {
                System.out.println("- " + item.getName());
            }
        }    System.out.println("\n--------------------------------------------------------------------");
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
