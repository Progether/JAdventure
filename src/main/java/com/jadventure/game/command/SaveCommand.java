package com.jadventure.game.command;

import com.jadventure.game.GameBeans;
import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;

public final class SaveCommand extends AbstractCommand {


    public SaveCommand() {
        super("save", (String)null, "Save your progress.");
    }

    @Override
    public void execute(Player player, IGameElementVisitor visitor, String[] args) {
    	GameBeans.getPlayerRepository().save(player);
    }

}
