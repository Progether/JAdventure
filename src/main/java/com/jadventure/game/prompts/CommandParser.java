package com.jadventure.game.prompts;

import java.util.TreeMap;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.command.BackpackCommand;
import com.jadventure.game.command.DequipCommand;
import com.jadventure.game.command.DropCommand;
import com.jadventure.game.command.EquipCommand;
import com.jadventure.game.command.GotoCommand;
import com.jadventure.game.command.HelpCommand;
import com.jadventure.game.command.ICommand;
import com.jadventure.game.command.InspectCommand;
import com.jadventure.game.command.LookCommand;
import com.jadventure.game.command.MonsterCommand;
import com.jadventure.game.command.PickCommand;
import com.jadventure.game.command.SaveCommand;
import com.jadventure.game.command.StatisticsCommand;
import com.jadventure.game.entities.Player;

/**
 */
public class CommandParser {
	private TreeMap<String, ICommand> commands = new TreeMap<>();
	
	public CommandParser() {
		// TODO Improve auto adding of ICommand instance, by reflection, search on ICommand
		add(new BackpackCommand());
		add(new DequipCommand());
		add(new DropCommand());
		add(new EquipCommand());
		add(new GotoCommand());
		add(new HelpCommand());
		add(new InspectCommand());
		add(new LookCommand());
		add(new MonsterCommand());
		add(new PickCommand());
		add(new SaveCommand());
		add(new StatisticsCommand());
	}

	private void add(ICommand command) {
		commands.put(command.getCommandName(), command);
		for (String alias : command.getCommandAliases()) {
			commands.put(alias, command);
		}
	}


	public void parse(Player player, String commandStr, String[] args, IGameElementVisitor visitor) {
        for (String key : commands.descendingKeySet()) {
            if (commandStr.startsWith(key)) {
            	commands.get(key).execute(player, visitor, args);
            }
        }
    }

}
