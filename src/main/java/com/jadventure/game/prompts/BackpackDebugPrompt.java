package com.jadventure.game.prompts;

import com.jadventure.game.entities.Player;
import com.jadventure.game.items.Item;
import com.jadventure.game.repository.RepositoryException;
import com.jadventure.game.repository.ItemRepository;
import com.jadventure.game.GameBeans;
import com.jadventure.game.QueueProvider;


/**
 * BackpackDebugPrompt is for editing the backpack contents
 * during debugging
 *
 * Items are added by their names and removed by their display name
 */
public class BackpackDebugPrompt{
    // @Resource
    protected static ItemRepository itemRepo = GameBeans.getItemRepository();

    private static String helpText = "\nlist: Lists the current item the player has\n"+
                                     "add: Add a new item\n"+
                                     "remove: Remove an item\n"+
                                     "help: Prints this info\n"+
                                     "exit: Exits the BackpackDebugMenu\n";

    public BackpackDebugPrompt(Player player){
        boolean continuePrompt = true;
        while(continuePrompt){
            QueueProvider.offer("Edit backpack:");
            String command = QueueProvider.take();
            continuePrompt = parse(player, command.toLowerCase());
        }
    }
    public static boolean parse(Player player, String command){
        boolean continuePrompt = true;
        
        try {
            if (command.startsWith("add")){
                try {
                    Item appendItem = itemRepo.getItem(command.substring(3).trim());
                    if (appendItem.getName() != null)
                        player.addItemToStorage(appendItem);
                } catch (RepositoryException ex) {
                    QueueProvider.offer(ex.getMessage());
                }
            }
            else if (command.startsWith("remove")){
                String removeItemName = command.substring(6).trim();
                player.dropItem(removeItemName);
            }
            else if (command.equals("list")){
                player.printBackPack();
            }
            else if (command.equals("help"))
                QueueProvider.offer(helpText);
            else if (command.equals("exit"))
                continuePrompt = false;
        } catch (NumberFormatException e){
            QueueProvider.offer("Invalid item name");
        }
        
        return continuePrompt;
    }
}
