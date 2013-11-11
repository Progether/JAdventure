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
    protected List<MenuItem> menuItems = new ArrayList<MenuItem>();
    protected Map<String, MenuItem> commandMap = new HashMap<String, MenuItem>();

    public void mainMenu() {
        this.menuItems.add(new MenuItem("Start", "Starts a new Game", "new"));
        this.menuItems.add(new MenuItem("Load", "Loads an existing Game"));
        this.menuItems.add(new MenuItem("Exit", null, "quit"));
        displayMenu();
    }

    public void displayMenu() {
        int i = 1;
        for (MenuItem menuItem: menuItems) {
            commandMap.put(String.valueOf(i), menuItem);
            commandMap.put(menuItem.getKey(), menuItem);
            for (String command: menuItem.getAltCommands()) {
                commandMap.put(command.toLowerCase(), menuItem);
            }

            i ++;
        }
        render();
    }

    public void render() {
        while (true) {
           MenuItem menuItem = selectMenu();
            if (menuItem != null)
                menuSelected(menuItem);
        }
    }

    protected MenuItem selectMenu() {
        // Print Menu Items
        int i = 1;
        for (MenuItem menuItem: menuItems) {
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

    public void menuSelected(MenuItem menuItem) {
        Scanner input = new Scanner(System.in);

        String key = menuItem.getKey();
        if(key.equals("start")) {
            Game game = new Game(null);
            game.commands();
        }
        else if(key.equals("exit")) {
            System.out.println("Goodbye!");
            System.exit(0);
        }
        else if(key.equals("load")) {
            System.out.println("What is the name of the avatar you want to load?");
            Player player = null;

            while (player == null) {
                key = input.next();
                if (Player.profileExists(key)) {
                    player = Player.load(key);
                } else {
                    System.out.println("That user doesn't exist. Try again.");
                }
            }

            Game game = new Game(player);
            game.commands();
        }
    }
}
