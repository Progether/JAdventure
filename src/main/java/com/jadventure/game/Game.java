package com.jadventure.game;

import com.jadventure.game.entities.Entity;
import com.jadventure.game.entities.Player;
import com.jadventure.game.monsters.Monster;
import com.jadventure.game.monsters.MonsterFactory;
import com.jadventure.game.navigation.LocationManager;
import com.jadventure.game.prompts.CommandParser;
import com.jadventure.game.QueueProvider;

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
            this.player = player;
            newGameStart(player);
        } else if (playerType.equals("old")) {
            this.player = player;
            QueueProvider.offer("Welcome back, " + player.getName() + "!");
            QueueProvider.offer("");
            player.getLocation().print();
            gamePrompt(player);
        } else {
            QueueProvider.offer("Invalid player type");
        }
    }
    
    /**
     * Starts a new game.
     * It prints the intro first and asks for the name of the player's character
     * and welcomes him/her. After that, it goes to the normal game prompt.
     */
    public void newGameStart(Player player) {
        QueueProvider.offer(player.getIntro());
        String userInput = input.nextLine();
        player.setName(userInput);
        QueueProvider.offer("Welcome to Silliya, " + this.player.getName() + ".");
        QueueProvider.offer("");
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
            QueueProvider.offer("Prompt:");
            String command = input.next().toLowerCase();
            continuePrompt = parser.parse(player, command, continuePrompt);
        }
    }
}
