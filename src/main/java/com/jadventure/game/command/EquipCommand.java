package com.jadventure.game.command;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;

public class EquipCommand extends AbstractCommand {

	public EquipCommand() {
		super("equip", "e", "Equip an item");
	}

	@Override
	public void execute(Player player, IGameElementVisitor visitor, String[] args) {
		player.equipItem(args[0]);
	}

}
