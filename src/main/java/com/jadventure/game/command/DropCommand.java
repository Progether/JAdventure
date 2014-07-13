package com.jadventure.game.command;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;
import com.jadventure.game.items.Item;

public class DropCommand extends AbstractCommand {


	public DropCommand() {
		super("drop", "d", "Drop an item");
	}

	@Override
	public void execute(Player player, IGameElementVisitor visitor, String[] args) {
		Item item = player.getStorage().getItem(args[0]);
		if (player.dropItem(args[0])) {
			visitor.append("You dropped the " + item.getName());
		}
	}

}
