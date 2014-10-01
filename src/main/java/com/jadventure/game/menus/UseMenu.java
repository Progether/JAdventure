package com.jadventure.game.menus;

import com.jadventure.game.entities.Player;
import com.jadventure.game.items.Storage;
import com.jadventure.game.items.Item;
import com.jadventure.game.items.ItemStack;
import com.jadventure.game.QueueProvider;

import java.util.ArrayList;

public class UseMenu extends Menus {

	private ArrayList<ItemStack> items;

	public UseMenu(ArrayList<ItemStack> items, Player player) {
		this.items = items;
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i).getItem();
			if (item.getItemID().startsWith("f") || item.getItemID().startsWith("p")) {
				QueueProvider.offer(item.getName() + " - " + item.getDescription());
			}
		}
		QueueProvider.offer("What item do you want to use?");
		String itemName = QueueProvider.take();
		if (!itemName.contains("back")) {
			player.equipItem(itemName);
		}
	}
}
