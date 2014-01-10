package com.jadventure.game.prompts;

import com.jadventure.game.entities.Player;
import com.jadventure.game.items.Item;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

/**
 * BackpackDebugPrompt is for editing the backpack contents
 * during debugging
 *
 * Items are added by their names and removed by their display name
 */
public class BackpackDebugPrompt{
    public static BlockingQueue queue;

    private static String helpText = "\nlist: Lists the current item the player has\n"+
                                     "add: Add a new item\n"+
                                     "remove: Remove an item\n"+
                                     "help: Prints this info\n"+
                                     "exit: Exits the BackpackDebugMenu\n";

    public BackpackDebugPrompt(BlockingQueue q, Player player){
        queue = q;
        boolean continuePrompt = true;
        Scanner input = new Scanner(System.in);
        while(continuePrompt){
            queue.offer("Edit backpack:");
            String command = input.nextLine();
            continuePrompt = parse(player, command.toLowerCase());
        }
    }
    public static boolean parse(Player player, String command){
        boolean continuePrompt = true;
        
        try{
            if(command.startsWith("add")){
                Item appendItem = new Item(command.substring(3).trim());
                if(appendItem.getName() != null)
                    player.addItemToStorage(appendItem);
            }
            else if(command.startsWith("remove")){
                String removeItemName = command.substring(6).trim();
                player.dropItem(removeItemName);
            }
            else if(command.equals("list")){
                player.printBackPack();
            }
            else if(command.equals("help"))
                queue.offer(helpText);
            else if(command.equals("exit"))
                continuePrompt = false;
        } catch(NumberFormatException e){
            queue.offer("Invalid item name");
        }
        
        return continuePrompt;
    }
}
