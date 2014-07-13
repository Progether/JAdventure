package com.jadventure.game.command;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;
import com.jadventure.game.items.Item;

public class PickCommand extends AbstractCommand {

	public PickCommand() {
		super("pick", "p", "Pick up an item");
	}

	@Override
	public void execute(Player player, IGameElementVisitor visitor, String[] args) {
		Item item = player.getLocation().getStorage().getItem(args[0]);
		if (player.pickUpItem(args[0])) {
			visitor.append("You picked up the " + item.getName());
		}
	}

}
