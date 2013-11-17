package com.jadventure.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * @deprecated
 */
public class MonsterCreator {
    
    Random random = new Random();
    
    public void Generate(ArrayList<Monster> monsterList) {
//        Monster monster = new Monster()
//        
//        monster.healthMax = ((int)GenerateHealth());
//        monster.health = (monster.healthMax);
//        
//        monster.armour = (GenerateArmour());
//        monster.damage = (GenerateDamage());
//        monster.name = (GenerateName());
//        
//        monsterList.add(monster);		
    }
    
    private int GenerateArmour() {
        int armour = 1;
        // Redundant and unneeded
        // random = new Random();
        
        armour = random.nextInt(100);
        return armour;
    }
    
    private float GenerateHealth() {
        float health = 100;
        // Redundant and unneeded
        // random = new Random();
        
        health = random.nextInt(100);
        return health;
    }
    
    private double GenerateDamage() {
        double damage = 1;
        // Redundant and unneeded
        // random = new Random();
        damage = random.nextInt(10);
        return damage;
    }
    
    private String GenerateName() {
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
