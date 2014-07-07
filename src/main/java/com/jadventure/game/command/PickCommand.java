package com.jadventure.game.command;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;

public class PickCommand extends AbstractCommand {

	public PickCommand() {
		super("pick", "p", "Pick up an item");
	}

	@Override
	public void execute(Player player, IGameElementVisitor visitor, String[] args) {
		player.dropItem(args[0]);
	}

}
