package com.jadventure.game;

import java.util.Scanner;

import com.jadventure.game.entities.Player;
import com.jadventure.game.navigation.Coordinate;
import com.jadventure.game.prompts.CommandParser;
import com.jadventure.game.repository.LocationRepository;

/**
 * This class contains the main loop that takes the input and
 * does the according actions.
 */
public class Game {
    public static final Coordinate DEFAULT_INITIAL_COORDINATE = new Coordinate(0, 0, -1);

    private LocationRepository locationRepo = GameBeans.getLocationRepository();
    
//    public ArrayList<Monster> monsterList = new ArrayList<Monster>();
//    public MonsterFactory monsterFactory = new MonsterFactory(); 
//    public Monster monster;
    public CommandParser cmdParser;
    public Scanner input = new Scanner(System.in);
    Player player = null;

    public Game(Player player, String playerType) {
        cmdParser = new CommandParser(player);
        
        player.setLocation(locationRepo.getLocation(DEFAULT_INITIAL_COORDINATE));
        if (playerType.equals("new")) { // New Game
            this.player = player;
            newGameStart(player);
        }
        else if (playerType.equals("old")) {
            this.player = player;
            QueueProvider.offer("Welcome back, " + player.getName() + "!");
            QueueProvider.offer("");
            player.getLocation().print();
        }
        else {
            QueueProvider.offer("Invalid player type");
            return;
        }
        gamePrompt(player);
    }
    
    /**
     * Starts a new game.
     * It prints the intro first and asks for the name of the players character
     * and welcomes him/her. After that, it goes to the normal game prompt.
     */
    public void newGameStart(Player player) {
        QueueProvider.offer(player.getIntro());
        String userInput = input.nextLine();
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
    public void gamePrompt(Player player) {
        boolean continuePrompt = true;
        while (continuePrompt) {
            QueueProvider.offer("Prompt:");
            String command = input.next().toLowerCase();
            continuePrompt = cmdParser.parse(player, command, continuePrompt);
        }
    }
}
