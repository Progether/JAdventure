package com.jadventure.game;

import com.jadventure.game.entities.Entity;
import com.jadventure.game.entities.Player;
import com.jadventure.game.monsters.Monster;
import com.jadventure.game.monsters.MonsterFactory;
import com.jadventure.game.navigation.LocationManager;
import com.jadventure.game.prompts.CommandParser;

import java.util.ArrayList;

/**
 * This class contains the main loop that takes the input and
 * does the according actions.
 */
public class Game {
    public ArrayList<Monster> monsterList = new ArrayList<Monster>();
    public MonsterFactory monsterFactory = new MonsterFactory(); 
    public CommandParser parser;
    public Monster monster;
    Player player = null;

    public Game(Player player, String playerType) throws DeathException {
          this.parser = new CommandParser(player);
          this.player = player;
          this.player.setLocation(LocationManager.INSTANCE.getInitialLocation());
          if (playerType.equals("new")) { // New Game
              newGameStart(player);
          } else if (playerType.equals("old")) {
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
    public void newGameStart(Player player) throws DeathException {
        QueueProvider.offer(player.getIntro());
        String userInput = QueueProvider.take();
        player.setName(userInput);
        QueueProvider.offer("Welcome to Silliya, " + player.getName() + ".");
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
    public void gamePrompt(Player player) throws DeathException {
        boolean continuePrompt = true;
        try {
            while (continuePrompt) {
                QueueProvider.offer("Prompt:");
                String command = QueueProvider.take().toLowerCase();
                continuePrompt = parser.parse(player, command, continuePrompt);
            }
        } catch (DeathException e) {
            if (e.getLocalisedMessage().equals("replay")) {
                return;
            } else {
                throw e;
            }
        }
    }
}
