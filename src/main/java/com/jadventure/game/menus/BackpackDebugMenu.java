package com.jadventure.game.menus;

import com.jadventure.game.items.Item;
import com.jadventure.game.menus.Menus;
import com.jadventure.game.menus.MenuItem;
import com.jadventure.game.entities.Player;

/**
 * BackpackDebugMenu is for editing the backpack contents
 * during debugging
 */
class BackpackDebugMenu extends Menus{
	public static Player player;
	
	public BackpackDebugMenu(Player p){
		player = p;
		this.menuItems.add(new MenuItem("List", "The list of items the player has"));
		this.menuItems.add(new MenuItem("Add", "Add new item"));
		this.menuItems.add(new MenuItem("Remove", "Remove an item"));
		this.menuItems.add(new MenuItem("Exit", "Exits from the Backpack menu"));
		
		boolean continueMenu = true;
		while(continueMenu){
			MenuItem selectedMenuItem = displayMenu(this.menuItems);
			continueMenu = testOption(selectedMenuItem);
		}
	}
	public static boolean testOption(MenuItem item){
		String key = item.getKey();
		boolean continueMenu = true;
		
		if(key.startsWith("add")){
			Item appendItem = new Item(key.substring(3).trim());
            player.addItemToBackpack(appendItem);
			// TODO : check that the item is real, I mean is a valid game item
		}
		else if(key.startsWith("remove")){
			String removeItem = key.substring(6).trim();
			player.dropItem(removeItem);
		}
		else if(key.trim().equals("list"))
			player.printBackPack();
		else if(key.trim().equals("exit"))
			continueMenu = false;
		
		return continueMenu;
	}
}