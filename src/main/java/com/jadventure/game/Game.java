package com.jadventure.game;

import java.util.ArrayList;
import java.util.Scanner;

// main class, gets user input and calls functions
public class Game {
    
    public ArrayList<Monster> monsterList = new ArrayList<Monster>();
    public MonsterFactory monsterFactory = new MonsterFactory();
    public Scanner input = new Scanner(System.in);
    public Monster monster;
    Player player;

    public Game(Player player) {
        if (player == null) { // New Game
            this.player = new Player();
            newGameStart();
        } else { // Existing Game
            this.player = player;
            System.out.println("Welcome back " + player.name + "!");
            new PlayerMenu(player);
        }
    }
    
    public void newGameStart() {
        // Opening dialog
        System.out.println("Hey... you alive?");
        System.out.println("*You let out a groan...*");
        System.out.println("Hey mate, you need to wake up. The guards will be coming around soon and they put a spear through the last guy they found still asleep.");
        System.out.println("*Slowly you sit up.*");
        System.out.println("That's the way! I'm Thorall, what's your name? ");
        String userInput = input.next();
        this.player.name = (userInput);
        System.out.println("Welcome to Silliya " + this.player.name + ".");
        System.out.println("You can type help for a list of commands");
        new PlayerMenu(player);
        
    }
    
    // COMMANDS
    // Command is being used for debugging at this time
    private void attackStats(Entity player, Entity monster) {
        System.out.println("----------------------------------");
        
        System.out.println("p_damage: " + this.player.damage);
        System.out.println("m_armour: " + monster.armour);
        
        System.out.println("mh_before: " + monster.health);
        // Replacing with new attack method
        // player.basicAttack(monster);
        System.out.println("mh_after: " + monster.health);
        
        System.out.println("----------------------------------");
        
        System.out.println("----------------------------------");
        
        System.out.println("m_damage: " + monster.damage);
        System.out.println("p_armour: " + this.player.armour);
        
        System.out.println("ph_before: " + this.player.health);
        // Replacing with new attack method
        // monster.attack(monster, player);
        System.out.println("ph_after: " + this.player.health);
        
        System.out.println("----------------------------------");
    }
}
