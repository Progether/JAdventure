package com.jadventure.game.menus;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a single Menu Option in Menus
 * @see Menus
 */
public class MenuItem {
    protected String command;
    protected String description;
    protected Set<String> altCommands;

    public MenuItem(String command, String description) {
        this.command = command;
        this.description = description;
        this.altCommands = new HashSet<String>();
    }

    public MenuItem(String command, String description, String... altCommands) {
        this(command, description);
        for (String altCommand: altCommands) {
            this.altCommands.add(altCommand);
        }
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

    // Used for switch in menus
    // Used in place of getCommand method for comparison against user input
    public String getKey() {
        return getCommand().toLowerCase();
    }
}
