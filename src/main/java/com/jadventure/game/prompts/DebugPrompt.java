package com.jadventure.game.prompts;

import com.jadventure.game.entities.Player;
import com.jadventure.game.prompts.BackpackDebugPrompt;
import com.jadventure.game.QueueProvider;

import java.util.Scanner;

/**
 * Defines the debugging prompt.
 * 
 * This is opened with the 'debug' option in the main game prompt.
 */
public class DebugPrompt{

    private static String helpText = "\nattack: Modify players attack damage\n"+
                              "health: Modify players health\n"+
                              "maxhealth: Modify players maximum health\n"+
                              "armour: Modify players armour\n"+
                              "level: modify players level\n"+
                              "gold: modify players gold\n"+
                              "backpack: Modify the player backpack\n"+
                              "stats: Display the current stats\n"+
                              "help: Prints this info\n"+
                              "exit: Exits the debug prompt\n";
    
    public DebugPrompt(Player player) {
        boolean continuePrompt = true;
        Scanner input = new Scanner(System.in);
        while (continuePrompt) {
            QueueProvider.offer("\nDebugPrompt:");
            String command = input.nextLine();
            continuePrompt = parse(player, command.toLowerCase());
        }
    }
    public static boolean parse(Player player, String command) {
        boolean continuePrompt = true;
        
        try {
            if (command.startsWith("attack")) {
                int newVal = Integer.parseInt(command.substring(6));
                player.setDamage(newVal);
            }
            else if (command.startsWith("maxhealth")) {
                int newVal = Integer.parseInt(command.substring(9));
                if (newVal <= 0) {
                    throw new IllegalArgumentException();
                }
                player.setHealthMax(newVal);
                player.setHealth(newVal < player.getHealth() ? newVal : player.getHealth());
            }
            else if (command.startsWith("health")) {
                int newVal = Integer.parseInt(command.substring(6));
                if (newVal <= 0) {
                    throw new IllegalArgumentException();
            	}
                // we don't want collision values, do we?
                if (newVal > player.getHealthMax()) {
                    player.setHealthMax(newVal);
                }
                player.setHealth(newVal);
            }
            else if (command.startsWith("armour")) {
                int newVal = Integer.parseInt(command.substring(6));
                player.setArmour(newVal);
            }
            else if (command.startsWith("level")) {
                int newVal = Integer.parseInt(command.substring(5));
                if (newVal <= 0) {
                    throw new IllegalArgumentException();
                }
                player.setLevel(newVal);
            }
            else if (command.startsWith("gold")) {
                int newVal = Integer.parseInt(command.substring(4));
                if (newVal < 0) {
                    throw new IllegalArgumentException();
                }
                player.setGold(newVal);
            }
		    else if (command.equals("backpack")) {
                new BackpackDebugPrompt(player);
		    }
		    else if (command.equals("stats")) {
			    player.getStatistics();
		    }
            else if (command.equals("help")) {
                QueueProvider.offer(helpText);
            }
			else if (command.equals("exit")) {
			    continuePrompt = false;
			}
            else {
                QueueProvider.offer("Unknown command. Type help for a list of commands");
            }
        }
        catch(NumberFormatException e){
            QueueProvider.offer("Value not acceptable");
        }
        catch(IllegalArgumentException e){
            QueueProvider.offer("Invalid value");
        }

        return continuePrompt;
    }
}
