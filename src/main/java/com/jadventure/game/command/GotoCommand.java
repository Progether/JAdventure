package com.jadventure.game.command;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;

public final class GotoCommand extends AbstractCommand {

    public GotoCommand() {
        super("goto", "g", "Go in a direction.");
    }

    @Override
    public void execute(Player player, IGameElementVisitor visitor, String[] args) {
        // TODO Auto-generated method stub

    }

}
