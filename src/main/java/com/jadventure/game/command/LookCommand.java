package com.jadventure.game.command;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;

public class LookCommand extends AbstractCommand {

    public LookCommand() {
        super("look", "l", "Look around");
    }


    @Override
    public void execute(Player player, IGameElementVisitor visitor) {
        visitor.visit(player.getLocation());
    }
    
}
