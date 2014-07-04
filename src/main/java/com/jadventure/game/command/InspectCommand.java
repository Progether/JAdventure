package com.jadventure.game.command;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;

public final class InspectCommand extends AbstractCommand {


    public InspectCommand() {
        super("inspect", "i", "Inspect an item");
    }

    @Override
    public void execute(Player player, IGameElementVisitor visitor, String[] args) {
        for (String arg : args) {
//            player.inspectItem(arg);
            boolean isItemFound = false;
            if (player.getStorage().contains(arg)) {
                visitor.visit(player.getStorage().getItem(arg));
            }
            if (player.getLocation().getStorage().contains(arg)) {
                visitor.visit(player.getLocation().getStorage().getItem(arg));
            }
            
            if (! isItemFound) {
                visitor.append("No '" + arg + "' is seen here.");
            }
        }
    }

}
