package com.jadventure.game.command;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;

public class DequipCommand extends AbstractCommand {

	public DequipCommand() {
		super("dequip", "de", "Dequip an item");
	}

	@Override
	public void execute(Player player, IGameElementVisitor visitor, String[] args) {
		player.dequipItem(args[0]);
	}

}
