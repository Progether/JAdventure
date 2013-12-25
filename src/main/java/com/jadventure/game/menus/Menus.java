package com.jadventure.game.menus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * All menus in JAdventure extend this class
 * 
 * Add MenuItems to menuItems, call displayMenu method and you're happy
 */
public class Menus {
    /**
     * menuID has currently not much of a purpose
     * only DebugMenu uses it for now
     * 
     * Use these values in future
     * System-level should be 1 - 19
     * Player-level should be 20-50
     * Debug-level should be 90-100
     */
    protected int menuID;
    
    /**
     * The collection of menuItems to be presented on screen
     * Add your MenuItems to this List
     * 
     * @see MenuItem
     */ 
    protected List<MenuItem> menuItems = new ArrayList<MenuItem>();
    
    /**
     * Map of the command strings to be accepted from user to the MenuItem
     */
    protected Map<String, MenuItem> commandMap = new HashMap<String, MenuItem>();

    /**
     * Maps the MenuItems and calls other methods in this class
     * to return a option selected by user
     * 
     * MenuItems are mapped both by their integer option
     * number(starting from 1) and their command strings
     * 
     * @param m The List to be displayed
     * 
     * @return The MenuItem selected ny user
     */
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

    /**
     * @param m The List to be displayed
     * 
     * @return The user selected MenuItem
     */
    protected MenuItem selectMenu(List<MenuItem> m) {
        this.printMenuItems(m);

        Scanner input = new Scanner(System.in);
        String command = input.next();
        if (commandMap.containsKey(command.toLowerCase())) {
            return commandMap.get(command.toLowerCase());
        } else {
            System.out.println("I don't know what '" + command + "' means.");
            return this.displayMenu(m);
        }
    }

<<<<<<< HEAD
    /**
     * You won't be calling this
     * 
     * @param m The List to be displayed
     */
=======
>>>>>>> origin/master
    private void printMenuItems(List<MenuItem> m) {
        int i = 1;
        for (MenuItem menuItem: m) {
            System.out.print("[" + i + "] " + menuItem.getCommand());
            if (menuItem.getDescription() != null) {
                System.out.print(" - " + menuItem.getDescription());
            }
            System.out.println();

            i++;
        }
    }
}

