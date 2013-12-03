package com.jadventure.game;

import com.jadventure.game.entities.Player;
import com.jadventure.game.menus.DebugMenu;
import com.jadventure.game.navigation.Direction;
import com.jadventure.game.navigation.ILocation;

import java.util.Map;

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
        } else if (command.startsWith("goto")) {
            String message = command.substring(4);
            ILocation location = player.getLocation();

            try {
                Direction direction = Direction.valueOf(message.toUpperCase());
                Map<Direction, ILocation> exits = location.getExits();

                if (exits.containsKey(direction)) {
                    ILocation newLocation = exits.get(Direction.valueOf(message.toUpperCase()));
                    player.setLocation(newLocation);
                    player.getLocation().print();
                } else {
                    System.out.println("The is no exit that way.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("The direction " + message + " does not exist.");
            }
        }
        else if (command.equals("debug")) {
            new DebugMenu(player);
        } else if (command.equals("exit")) {
            continuePrompt = false;
        }
        return continuePrompt;
    }

}
