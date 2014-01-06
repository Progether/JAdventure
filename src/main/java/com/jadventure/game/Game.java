package com.jadventure.game;

import com.jadventure.game.entities.Entity;
import com.jadventure.game.entities.Player;
import com.jadventure.game.monsters.Monster;
import com.jadventure.game.monsters.MonsterFactory;
import com.jadventure.game.navigation.LocationManager;
import com.jadventure.game.prompts.CommandParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class contains the main loop that takes the input and
 * does the according actions.
 */
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
            try {
                Path orig = Paths.get("json/original_data/locations.json");
                Path dest = Paths.get("json/locations.json");
                Files.copy(orig, dest, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                System.out.println("Unable to load new locations file.");
            }
            this.player = player;
            newGameStart(player);
        } else if (playerType.equals("old")) {
            this.player = player;
            System.out.println("Welcome back " + player.getName() + "!");
            System.out.println();
            player.getLocation().print();
            gamePrompt(player);
        } else {
            System.out.println("Invalid player type");
        }
    }
    
    /**
     * Starts a new game.
     * It prints the intro first and asks for the name of the player's character
     * and welcomes him/her. After that, it goes to the normal game prompt.
     */
    public void newGameStart(Player player) {
        System.out.println(player.getIntro());
        String userInput = input.next();
        player.setName(userInput);
        System.out.println("Welcome to Silliya " + this.player.getName() + ".");
        System.out.println();
        player.getLocation().print();
        
        gamePrompt(player);
    }

    /**
     * This is the main loop for the player-game interaction. It gets input from the
     * command line and checks if it is a recognised command.
     *
     * This keeps looping as long as the player didn't type an exit command.
     */
    public void gamePrompt(Player player) {
        boolean continuePrompt = true;
        while (continuePrompt) {
            System.out.println("Prompt:");
            String command = input.nextLine().toLowerCase();
            continuePrompt = parser.parse(player, command, continuePrompt);
        }
    }
}
