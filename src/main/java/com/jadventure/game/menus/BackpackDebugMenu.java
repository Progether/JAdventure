package com.jadventure.game.menus;

import com.jadventure.game.items.Item;
import com.jadventure.game.menus.Menus;
import com.jadventure.game.menus.MenuItem;
import com.jadventure.game.entities.Player;

import java.util.Scanner;

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
        Scanner input = new Scanner(System.in);
		boolean continueMenu = true;
		
		if(key.equals("add")){
            System.out.println("Enter item name :");
			Item appendItem = new Item(input.next());
            player.addItemToBackpack(appendItem);
			// TODO : check that the item is real, I mean is a valid game item
		}
		else if(key.equals("remove")){
            System.out.println("Enter item name :");
			String removeItem = input.next();
			player.dropItem(removeItem);
		}
		else if(key.equals("list"))
			player.printBackPack();
		else if(key.equals("exit"))
			continueMenu = false;
		
		return continueMenu;
	}
}