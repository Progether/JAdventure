package com.jadventure.game;

import java.util.ArrayList;
import java.util.Scanner;

// main class, gets user input and calls functions
public class Game {
    
    public ArrayList<Monster> monsterList = new ArrayList<Monster>();
    public MonsterFactory monsterFactory = new MonsterFactory(); 
    public Scanner input = new Scanner(System.in);
    public Monster monster;
    Player player = null;

    public Game(Player player) {
        if (player == null) { // New Game
            this.player = Player.getInstance();
            newGameStart();
        } else { // Existing Game
            this.player = player;
            System.out.println("Welcome back " + player.getName() + "!");
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
        player.setName(userInput);
        System.out.println("Welcome to Silliya " + this.player.getName() + ".");
        System.out.println("You can type help for a list of commands");
        
        new PlayerMenu(player);
        
    }
    
    // COMMANDS
    // Command is being used for debugging at this time
    private void attackStats(Entity player, Entity monster) {
        System.out.println("----------------------------------");
        
        System.out.println("p_damage: " + this.player.getDamage());
        System.out.println("m_armour: " + monster.getArmour());
        
        System.out.println("mh_before: " + monster.getArmour());
        // Replacing with new attack method
        // player.basicAttack(monster);
        System.out.println("mh_after: " + monster.getHealth());
        
        System.out.println("----------------------------------");
        
        System.out.println("----------------------------------");
        
        System.out.println("m_damage: " + monster.getDamage());
        System.out.println("p_armour: " + this.player.getArmour());
        
        System.out.println("ph_before: " + this.player.getHealth());
        // Replacing with new attack method
        // monster.attack(monster, player);
        System.out.println("ph_after: " + this.player.getHealth());
        
        System.out.println("----------------------------------");
    }
}
