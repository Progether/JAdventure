package com.jadventure.game;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Cage
 * Date: 04/11/13
 * Time: 11:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Menu {
    protected String command;
    protected String description;
    protected Set<String> altCommands;
    protected List<Menu> menuItems;
    protected MenuListener menuListener;

    public static Menu loadFromFile(String fileName) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(new FileReader(fileName), Menu.class);
        } catch (FileNotFoundException ex) {
            System.out.println( "Unable to open file '" + fileName + "'.");
            return null;
        }
    }

    public Menu() {
        this.altCommands = new HashSet<String>();
        this.menuItems = new ArrayList<Menu>();
    }

    public Menu(String command, String description) {
        this();
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getAltCommands() {
        return altCommands;
    }

    public void setAltCommands(Set<String> altCommands) {
        this.altCommands = altCommands;
    }

    public List<Menu> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<Menu> menuItems) {
        this.menuItems = menuItems;
    }

    public MenuListener getMenuListener() {
        return menuListener;
    }

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    // Used to determine action in the MenuListener
    public String getKey() {
        return getCommand().toLowerCase();
    }

    public boolean hasMenuItems() {
        return getMenuItems().size() > 0;
    }

    public void render() {
        while (true) {
            Menu menu = selectMenu();
            if (menu != null && getMenuListener() != null)
                getMenuListener().menuSelected(menu);
        }
    }

    protected Menu selectMenu() {
        // Map of valid commands to select a Menu
        Map<String, Menu> commandMap = new HashMap<String, Menu>();

        // Print Menu Items
        int i = 1;
        for (Menu menu: menuItems) {
            System.out.print("[" + i + "] " + menu.getCommand());
            if (menu.getDescription() != null) {
                System.out.print(" - " + menu.getDescription());
            }
            System.out.println();

            // Fill valid commands to select a Menu
            commandMap.put(String.valueOf(i), menu);
            commandMap.put(menu.getKey(), menu);
            for (String command: menu.getAltCommands()) {
                commandMap.put(command.toLowerCase(), menu);
            }

            i ++;
        }

        Scanner input = new Scanner(System.in);
        String command = input.next();
        if (commandMap.containsKey(command)) {
            Menu menu = commandMap.get(command);
            return menu.hasMenuItems() ? menu.selectMenu() : menu;
        } else {
            System.out.println("I don't know what '" + command + "' means.");
            return null;
        }
    }
}
