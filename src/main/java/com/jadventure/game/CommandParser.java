package com.jadventure.game;

import com.jadventure.game.menus.DebugMenu;
import com.jadventure.game.entities.Player;

public class CommandParser {

    public boolean parse(Player player, String command, boolean continuePrompt) {
        if (command.equals("stats")) {
            player.getStats();
        }
        else if (command.equals("backpack")) {
            player.printBackPack();
        }
        else if (command.equals("save")) {
            player.save();
        }
        else if (command.equals("debug")) {
            new DebugMenu(player);
        } else if (command.equals("exit")) {
            continuePrompt = false;
        }
        return continuePrompt;
    }

}
