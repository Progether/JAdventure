package com.jadventure.game.command;

import java.util.List;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;

public final class MonsterCommand extends AbstractCommand {

    
    public MonsterCommand() {
        super("monster", "m", "Shows the monsters around you.");
    }


    @Override
    public void execute(Player player, IGameElementVisitor visitor) {
        // TODO Auto-generated method stub

    }

}
