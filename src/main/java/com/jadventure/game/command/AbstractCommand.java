package com.jadventure.game.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;

public abstract class AbstractCommand implements ICommand {
    private String commandName;
    private List<String> commandAliases;
    private String description;


    public AbstractCommand(String commandName, List<String> commandAliases,
            String description) {
        super();
        this.commandName = commandName;
        this.commandAliases = Collections.unmodifiableList(commandAliases);
        this.description = description;
    }


    public AbstractCommand(String commandName, String aliases,
            String description) {
        this(commandName, convertCommandAliases(aliases), description);
    }


    public abstract void execute(Player player, IGameElementVisitor visitor);
    

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public List<String> getCommandAliases() {
        return commandAliases;
    }

    @Override
    public String getDescription() {
        return description;
    }


    private static List<String> convertCommandAliases(String aliases) {
        Set<String> aliasSet = new TreeSet<>();
        for (String alias : aliases.split(",")) {
            String cleanAlias = alias.trim();
            if (cleanAlias.length() > 0) {
                aliasSet.add(cleanAlias);
            }
        }

        return new ArrayList<>(aliasSet);
    }

}
