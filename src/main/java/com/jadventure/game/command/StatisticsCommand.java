package com.jadventure.game.command;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;

public class StatisticsCommand extends AbstractCommand {
    

    public StatisticsCommand() {
        super("stats", "st", "Prints your statistics.");
    }


    public void execute(Player player, IGameElementVisitor visitor, String[] args) {
        player.accept(visitor);
    }

}
