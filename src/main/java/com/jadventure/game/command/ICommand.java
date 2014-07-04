package com.jadventure.game.command;

import java.util.List;

public interface ICommand {

    String getCommandName();
    List<String> getCommandAliases();
    
    String getDescription();
    
}
