package com.jadventure.game;

import com.jadventure.game.entities.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TreeMap;

/**
 * CommandParser parses the game commands
 *
 * To add a new command, add the <String , Method> pair in constructor to the commandMap
 * and declare the method inside class @see Command
 * If you include parameters other than a single String, make sure you send the parameters too
 * in your new inline methods like those declared below.
 */
public class CommandParser {
    Player player;
    private TreeMap<String, Method> commandMap;

    public CommandParser(Player player){
        this.player = player;
        commandMap = new TreeMap<String, Method>();

        this.commandMap.put("st", getCommandMethod("command_st"));
        this.commandMap.put("help", getCommandMethod(("command_help")));
        this.commandMap.put("m", getCommandMethod("command_m"));
        this.commandMap.put("debug", getCommandMethod("command_debug"));
        this.commandMap.put("b", getCommandMethod("command_b"));
        this.commandMap.put("save", getCommandMethod("command_save"));
        this.commandMap.put("g", getCommandMethodWithParams("command_g"));
        this.commandMap.put("e", getCommandMethodWithParams("command_e"));
        this.commandMap.put("de", getCommandMethodWithParams("command_de"));
        this.commandMap.put("p", getCommandMethodWithParams("command_p"));
        this.commandMap.put("d", getCommandMethodWithParams("command_d"));
    }

    // getCommand is just an inline method
    private Method getCommandMethod(String name){
        try {
            return Command.class.getDeclaredMethod(name);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    // getCommand too is an inline method
    private Method getCommandMethodWithParams(String name) {
        try {
            return Command.class.getDeclaredMethod(name, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean parse(Player player, String command, boolean continuePrompt) {
        Command com = Command.getInstance(player);

        if(command.equals("exit"))
            return false;

        // descendingKeySet otherwise startsWith will return correspond to longer command
        // e.g. 'de' will match startWith('d')
        for(String key : commandMap.descendingKeySet()) {
            if(command.startsWith(key)) {
                Method method = commandMap.get(key);
                if(method.getParameterTypes().length == 0){
                    try {
                        method.invoke(com);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                else if(method.getParameterTypes()[0] == String.class) {
                    String arg = command.substring(key.length());
                    try {
                        method.invoke(com, arg);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return continuePrompt;
    }

}
