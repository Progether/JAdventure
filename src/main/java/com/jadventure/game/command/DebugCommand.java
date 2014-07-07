package com.jadventure.game.command;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;
import com.jadventure.game.prompts.DebugPrompt;

public class DebugCommand extends AbstractCommand {

	public DebugCommand() {
		super("debug", (String)null, "Start debugging");
	}

	@Override
	public void execute(Player player, IGameElementVisitor visitor, String[] args) {
		new DebugPrompt(player);
	}

}
