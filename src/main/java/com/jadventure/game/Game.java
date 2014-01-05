package com.jadventure.game;

import com.jadventure.game.entities.Entity;
import com.jadventure.game.entities.Player;
import com.jadventure.game.monsters.Monster;
import com.jadventure.game.monsters.MonsterFactory;
import com.jadventure.game.navigation.LocationManager;
import com.jadventure.game.prompts.CommandParser;

import java.util.ArrayList;
import java.util.Scanner;

// main class, gets user input and calls functions
public class Game {
    
    public ArrayList<Monster> monsterList = new ArrayList<Monster>();
    public MonsterFactory monsterFactory = new MonsterFactory(); 
    public CommandParser parser;
    public Scanner input = new Scanner(System.in);
    public Monster monster;
    Player player = null;

    public Game(Player player, String playerType) {
        parser = new CommandParser(player);
        player.setLocation(LocationManager.INSTANCE.getInitialLocation());
        if (playerType.equals("new")) { // New Game
            this.player = player;
            newGameStart(player);
        } else if (playerType.equals("old")) { // Existing Game
            this.player = player;
            System.out.println("Welcome back " + player.getName() + "!");
            System.out.println();
            player.getLocation().print();
            gamePrompt(player);
        } else {
            System.out.println("Invalid player transfer");
        }
    }
    
    public void newGameStart(Player player) {
        System.out.println(player.getIntro());
        String userInput = input.next();
        player.setName(userInput);
        System.out.println("Welcome to Silliya " + this.player.getName() + ".");
        System.out.println();
        player.getLocation().print();
        
        gamePrompt(player);
    }

    public void gamePrompt(Player player) {
        boolean continuePrompt = true;
        while (continuePrompt) {
            System.out.println("Prompt:");
            String command = input.nextLine().toLowerCase();
            continuePrompt = parser.parse(player, command, continuePrompt);
        }
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
