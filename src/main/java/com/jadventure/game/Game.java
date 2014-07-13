package com.jadventure.game;

import java.util.Scanner;

import com.jadventure.game.entities.Player;
import com.jadventure.game.navigation.Coordinate;
import com.jadventure.game.prompts.CommandParser;
import com.jadventure.game.repository.WorldRepository;

/**
 * This class contains the main loop that takes the input and
 * does the according actions.
 */
public class Game {
    public static final Coordinate DEFAULT_INITIAL_COORDINATE = new Coordinate(0, 0, -1);

    private WorldRepository worldRepo = GameBeans.getWorldRepository();
    
//    public ArrayList<Monster> monsterList = new ArrayList<Monster>();
//    public MonsterFactory monsterFactory = new MonsterFactory(); 
//    public Monster monster;
    public CommandParser cmdParser;
    public Scanner input = new Scanner(System.in);
    Player player = null;

    public Game(Player player, String playerType) {
        cmdParser = new CommandParser();
        
        player.setLocation(worldRepo.getLocation(DEFAULT_INITIAL_COORDINATE));
        if (playerType.equals("new")) { // New Game
            this.player = player;
            newGameStart(player);
        }
        else if (playerType.equals("old")) {
            this.player = player;
            TextBuilderVisitor visitor = new TextBuilderVisitor();
            visitor.append("Welcome back, " + player.getName() + "!");
            visitor.append("");
            player.getLocation().accept(visitor);
            QueueProvider.offer(visitor.toString());
        }
        else {
            QueueProvider.offer("Invalid player type");
            return;
        }
        gamePrompt(player);
    }
    
    /**
     * Starts a new game.
     * It prints the introduction text first and asks for the name of the players character
     * and welcomes him/her. After that, it goes to the normal game prompt.
     */
    public void newGameStart(Player player) {
        QueueProvider.offer(player.getIntro());
        String userInput = input.nextLine();
        player.setName(userInput);
        TextBuilderVisitor visitor = new TextBuilderVisitor();
        visitor.append("Welcome to Silliya, " + player.getName() + ".");
        visitor.append("");
        player.getLocation().accept(visitor);
        QueueProvider.offer(visitor.toString());
        
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
            String userInput = input.nextLine().toLowerCase();
            System.out.println("User input '" + userInput + "'");
            String[] userInputArray = userInput.split("\\s");
            System.out.println("User input []: " + CommandParser.toString(userInputArray));
            if (userInput.length() > 0) {
                String command = userInputArray[0];
                String[] argArray = removeFirst(userInputArray);
                System.out.println("User input command '" + command + "' args " + CommandParser.toString(argArray));
                continuePrompt = cmdParser.parse(player, command, argArray);
            }
        }
    }

    private String[] removeFirst(String[] array) {
        if (array == null || array.length == 0
                || array.length == 1) {
            return new String[0];
        }

        String[] newArray = new String[array.length - 1];
        for (int index = 0; index < newArray.length; index++) {
            newArray[index] = array[index + 1];
        }
        return newArray;
    }
}
