package com.jadventure.game.command;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;

public class DropCommand extends AbstractCommand {


	public DropCommand() {
		super("drop", "d", "Drop an item");
	}

	@Override
	public void execute(Player player, IGameElementVisitor visitor, String[] args) {
		player.dropItem(args[0]);
	}

}
