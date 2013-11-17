package com.jadventure.game;

import java.util.ArrayList;
import java.util.Random;

public class MonsterCreator {
    
    Random random = new Random();
    
    public void generate(ArrayList<Monster> monsterList) {
        Monster monster = new Monster();
        
        monster.setHealthMax((int)generateHealth());
        monster.setHealth(monster.getHealthMax());
        
        monster.setArmour(generateArmour());
        monster.setDamage(generateDamage());
        monster.setName(generateName());
        
        monsterList.add(monster);		
    }
    
    private int generateArmour() {
        int armour = 1;
        // Redundant and unneeded
        // random = new Random();
        
        armour = random.nextInt(100);
        return armour;
    }
    
    private float generateHealth() {
        float health = 100;
        // Redundant and unneeded
        // random = new Random();
        
        health = random.nextInt(100);
        return health;
    }
    
    private double generateDamage() {
        double damage = 1;
        // Redundant and unneeded
        // random = new Random();
        damage = random.nextInt(10);
        return damage;
    }
    
    private String generateName() {
        String name = "unassigned";
        int temp = random.nextInt(15);
        
        if (temp <= 5) {
            name = "Bugbear";
        } else if (temp > 5 && temp <= 10) {
            name = "Goblin";
        } else if (temp > 10) {
            name = "Troll";
        }
        return name;
    }
    
}
