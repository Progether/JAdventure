package com.jadventure.game.prompts;

import com.jadventure.game.entities.Player;
import com.jadventure.game.items.Item;
import com.jadventure.game.repository.ItemRepository;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.TextBuilderVisitor;

import java.util.Scanner;

/**
 * BackpackDebugPrompt is for editing the backpack contents
 * during debugging
 *
 * Items are added by their names and removed by their display name
 */
public class BackpackDebugPrompt{
    // FIXME remove static
    private static ItemRepository itemRepo = null; 

    private static String helpText = "\nlist: Lists the current item the player has\n"+
                                     "add: Add a new item\n"+
                                     "remove: Remove an item\n"+
                                     "help: Prints this info\n"+
                                     "exit: Exits the BackpackDebugMenu\n";

    public BackpackDebugPrompt(Player player){
        boolean continuePrompt = true;
        Scanner input = new Scanner(System.in);
        while(continuePrompt){
            QueueProvider.offer("Edit backpack:");
            String command = input.nextLine();
            continuePrompt = parse(player, command.toLowerCase());
        }
    }
    public static boolean parse(Player player, String command){
        boolean continuePrompt = true;
        
        try{
            if(command.startsWith("add")){
                Item appendItem = itemRepo.getItem(command.substring(3).trim());
                if(appendItem.getName() != null)
                    player.addItemToStorage(appendItem);
            }
            else if(command.startsWith("remove")){
                String removeItemName = command.substring(6).trim();
                player.dropItem(removeItemName);
            }
            else if(command.equals("list")){
            	TextBuilderVisitor textBldr = new TextBuilderVisitor();
            	player.getStorage().accept(textBldr);
            	QueueProvider.offer(textBldr.toString());
//                player.printBackPack();
            }
            else if(command.equals("help"))
                QueueProvider.offer(helpText);
            else if(command.equals("exit"))
                continuePrompt = false;
        } catch(NumberFormatException e){
            QueueProvider.offer("Invalid item name");
        }
        
        return continuePrompt;
    }

}
