package com.jadventure.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Cage
 * Date: 04/11/13
 * Time: 11:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Menus {
    protected int menuID;
    protected List<MenuItem> menuItems = new ArrayList<MenuItem>();
    protected Map<String, MenuItem> commandMap = new HashMap<String, MenuItem>();

    public void displayMenu(List<MenuItem> m) {
        int i = 1;
        for (MenuItem menuItem: m) {
            commandMap.put(String.valueOf(i), menuItem);
            commandMap.put(menuItem.getKey(), menuItem);
            for (String command: menuItem.getAltCommands()) {
                commandMap.put(command.toLowerCase(), menuItem);
            }

            i ++;
        }
        render(m);
    }

    public void render(List<MenuItem> m) {
        while (true) {
           MenuItem menuItem = selectMenu(m);
            if (menuItem != null)
                determineMenu(menuItem);
        }
    }

    protected MenuItem selectMenu(List<MenuItem> m) {
        // Print Menu Items
        int i = 1;
        for (MenuItem menuItem: m) {
            System.out.print("[" + i + "] " + menuItem.getCommand());
            if (menuItem.getDescription() != null) {
                System.out.print(" - " + menuItem.getDescription());
            }
            System.out.println();

            i ++;
        }

        Scanner input = new Scanner(System.in);
        String command = input.next();
        if (commandMap.containsKey(command.toLowerCase())) {
            return commandMap.get(command.toLowerCase());
        } else {
            System.out.println("I don't know what '" + command + "' means.");
            return null;
        }
    }

    public void determineMenu(MenuItem m){
        if (menuID == 1) {
            MainMenu.mainMenuSelected(m);
        }
        else if (menuID == 2) {
            PlayerMenu.playerMenuSelected(m);
        }
    }
}

