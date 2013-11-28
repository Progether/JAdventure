package com.jadventure.game;

import com.jadventure.game.menus.DebugMenu;
import com.jadventure.game.entities.Player;
import com.jadventure.game.navigation.LocationManager;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.navigation.Direction;
import com.jadventure.game.navigation.Exit;
import com.jadventure.game.navigation.Coordinate;

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
            Map<Direction, Exit> exits = location.getExits();
            Exit newExit = exits.get(Direction.valueOf(message));
            Coordinate newCoordinate = newExit.getCoordinate();
            player.setLocation(LocationManager.INSTANCE.getLocation(newCoordinate));
            player.getLocation().print();
        }
        else if (command.equals("debug")) {
            new DebugMenu(player);
        } else if (command.equals("exit")) {
            continuePrompt = false;
        }
        return continuePrompt;
    }

}
