package com.jadventure.game.prompts;

import com.jadventure.game.entities.Player;
import com.jadventure.game.items.Item;

import java.util.Scanner;

/**
 * BackpackDebugPrompt is for editing the backpack contents
 * during debugging
 *
 * Items are added by their names and removed by their display name
 */
public class BackpackDebugPrompt{
    public BackpackDebugPrompt(Player player){
        boolean continuePrompt = true;
        Scanner input = new Scanner(System.in);
        while(continuePrompt){
            System.out.println("Edit backpack:");
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
                    player.addItemToBackpack(appendItem);
            }
            else if(command.startsWith("remove")){
                String removeItemName = command.substring(3).trim();
                player.dropItem(removeItemName);
            }
            else if(command.equals("list")){
                player.printBackPack();
            }
            else if(command.equals("exit"))
                continuePrompt = false;
        } catch(NumberFormatException e){
            System.out.println("Invalid item name");
        }
        
        return continuePrompt;
    }
}