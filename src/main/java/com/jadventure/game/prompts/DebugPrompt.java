package com.jadventure.game.prompts;

import com.jadventure.game.entities.Player;
import com.jadventure.game.navigation.Coordinate;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.navigation.LocationManager;
import com.jadventure.game.prompts.BackpackDebugPrompt;
import com.jadventure.game.QueueProvider;

/**
 * Defines the debugging prompt.
 * 
 * This is opened with the 'debug' option in the main game prompt.
 */
public class DebugPrompt{

    private static String helpText = "\nattack: Modify player's attack damage\n"+
                              "health: Modify player's health\n"+
                              "maxhealth: Modify player's maximum health\n"+
                              "armour: Modify player's armour\n"+
                              "level: modify player's level\n"+
                              "gold: modify player's gold\n"+
                              "teleport: moves the player to the co-ordinates entered\n"+
                              "backpack: Modify the player backpack\n"+
                              "vs: Display the current stats\n"+
                              "help: Prints this info\n"+
                              "exit: Exits the debug prompt\n";
    
    public DebugPrompt(Player player){
        boolean continuePrompt = true;
        while(continuePrompt){
            QueueProvider.offer("\nDebugPrompt:");
            String command = QueueProvider.take();
            continuePrompt = parse(player, command.toLowerCase());
        }
    }
    public static boolean parse(Player player, String command){
        boolean continuePrompt = true;
        
        try{
            if(command.startsWith("attack")){
                int newVal = Integer.parseInt(command.substring(6));
                player.setDamage(newVal);
            }
            else if(command.startsWith("maxhealth")){
                int newVal = Integer.parseInt(command.substring(9));
                if(newVal <= 0)
                    throw new IllegalArgumentException();
                player.setHealthMax(newVal);
                player.setHealth(newVal < player.getHealth() ? newVal : player.getHealth());
            }
            else if(command.startsWith("health")){
                int newVal = Integer.parseInt(command.substring(6));
                if(newVal <= 0)
                    throw new IllegalArgumentException();
                // we don't want collision values, do we?
                if(newVal > player.getHealthMax())
                    player.setHealthMax(newVal);
                player.setHealth(newVal);
            }
            else if(command.startsWith("armour")){
                int newVal = Integer.parseInt(command.substring(6));
                player.setArmour(newVal);
            }
            else if(command.startsWith("level")){
                int newVal = Integer.parseInt(command.substring(5));
                if(newVal <= 0)
                    throw new IllegalArgumentException();
                player.setLevel(newVal);
            }
            else if(command.startsWith("gold")){
                int newVal = Integer.parseInt(command.substring(4));
                if(newVal < 0)
                    throw new IllegalArgumentException();
                player.setGold(newVal);
            } else if(command.startsWith("teleport")){
                ILocation newLocation = LocationManager.getLocation(new Coordinate(command.substring(8, command.length())));
                ILocation oldLocation = player.getLocation();
                try {
                    player.setLocation(newLocation);
                    player.getLocation().print();
                } catch (NullPointerException e) {
                    player.setLocation(oldLocation);
                    QueueProvider.offer("There is no such location");
                }
            }
		    else if(command.equals("backpack")){
                        new BackpackDebugPrompt(player);
		    }
		    else if(command.equals("vs"))
			    player.getStats();
            else if(command.equals("help"))
                QueueProvider.offer(helpText);
			else if(command.equals("exit"))
			    continuePrompt = false;
            else
                QueueProvider.offer("Unknown command. Type help for a list of commands");
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
