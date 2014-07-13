package com.jadventure.game.command;

import java.util.List;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;

public interface ICommand {

    String getCommandName();
    
    List<String> getCommandAliases();
    
    String getDescription();
    
    void execute(Player player, IGameElementVisitor visitor, String[] args);

}
