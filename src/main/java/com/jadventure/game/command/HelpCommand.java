package com.jadventure.game.command;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;

public final class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super("help", "h, ?", "Prints help");
    }


    @Override
    public void execute(Player player, IGameElementVisitor visitor, String[] args) {
        
    }

}
