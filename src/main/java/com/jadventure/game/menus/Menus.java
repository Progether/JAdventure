package com.jadventure.game.menus;

import com.jadventure.game.QueueProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * All menus in JAdventure extend this class
 * Add MenuItems to menuItems, call displayMenu and you're happy
 */
public class Menus {
    protected List<MenuItem> menuItems = new ArrayList<>();
    protected Map<String, MenuItem> commandMap = new HashMap<String, MenuItem>();

    public MenuItem displayMenu(List<MenuItem> m) {
        int i = 1;
        for (MenuItem menuItem: m) {
            commandMap.put(String.valueOf(i), menuItem);
            commandMap.put(menuItem.getKey(), menuItem);
            for (String command: menuItem.getAltCommands()) {
                commandMap.put(command.toLowerCase(), menuItem);
            }
            i ++;
        }
        MenuItem selectedItem = selectMenu(m);
        return selectedItem;
    }

    // calls for user input from command line
    protected MenuItem selectMenu(List<MenuItem> m) {
        this.printMenuItems(m);
        String command = QueueProvider.take();
        if (commandMap.containsKey(command.toLowerCase())) {
            return commandMap.get(command.toLowerCase());
        } else {
            QueueProvider.offer("I don't know what '" + command + "' means.");
            return this.displayMenu(m);
        }
    }

    private void printMenuItems(List<MenuItem> m) {
        int i = 1;
        for (MenuItem menuItem: m) {
            if (menuItem.getDescription() != null) {
                QueueProvider.offer("[" + i + "] " + menuItem.getCommand() + " - " + menuItem.getDescription());
            } else {
                QueueProvider.offer("[" + i + "] " + menuItem.getCommand());
            }
            i++;
        }
    }
}

