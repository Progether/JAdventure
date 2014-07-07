package com.jadventure.game.command;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;

public final class HelpCommand extends AbstractCommand {
	@Deprecated
    private String helpText = "\nstats (st): Prints your statistics.\n"
            + "backpack (b): Prints out the contents of your backpack.\n"
            + "monster (m): Shows the monsters around you."
            + "save: Save your progress.\n"
            + "goto (g): Go in a direction.\n"
            + "look (l): Look at current location.\n"
            + "inspect (i): Inspect an item.\n"
            + "dequip (e): ...\n"
            + "dequip (de): ...\n"
            + "pick (p): Pick up an item.\n"
            + "drop (d): Drop an item.\n"
            + "exit: Exit the game and return to the main menu.\n"
            + "debug: ...\n";

    public HelpCommand() {
        super("help", "h, ?", "Prints help");
    }


    @Override
    public void execute(Player player, IGameElementVisitor visitor, String[] args) {
        visitor.append(helpText);
    }

}
