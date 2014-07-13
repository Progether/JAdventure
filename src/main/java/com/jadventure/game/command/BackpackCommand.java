package com.jadventure.game.command;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;

public final class BackpackCommand extends AbstractCommand {

    public BackpackCommand() {
        super("backpack", "b", "Prints out the contents of your backpack.");
    }


    @Override
    public void execute(Player player, IGameElementVisitor visitor, String[] args) {
    	if (player.getStorage().isEmpty()) {
    		visitor.append("Your backpack is empty.");
    		return;
    	}

        visitor.append("Backpack:");
        visitor.visit(player.getStorage());
    }

}
