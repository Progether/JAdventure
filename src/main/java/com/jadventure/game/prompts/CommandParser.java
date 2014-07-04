package com.jadventure.game.prompts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TreeMap;

import com.jadventure.game.entities.Player;

/**
 * CommandParser parses the game commands
 *
 * It parses all the commands automatically.
 * To add a new command, you just need to make addition in the CommandCollection.
 */
public class CommandParser {
    /** Command and alias names to  */
    private TreeMap<String, Method> commandMap = new TreeMap<String, Method>();

    public CommandParser() {
        initCommandMap();
    }

    // adds the command to the commandMap
    private void initCommandMap() {
        Method[] methods = CommandCollection.class.getMethods();

        for (Method method: methods) {
            if (! method.isAnnotationPresent(Command.class)) {
                continue;
            }

            Command annotation = method.getAnnotation(Command.class);
            this.commandMap.put(annotation.command(), method);
            for (String alias : annotation.aliases().split(",")) {
                System.out.println("Command alias: '" + alias + "'");
                if (alias.length() == 0) {
                    break;
                }
                this.commandMap.put(alias, method);
            }
        }
    }

    public boolean parse(Player player, String command, boolean continuePrompt) {
        CommandCollection com = CommandCollection.getInstance();
        com.initPlayer(player);

        if (command.equals("exit"))
            return false;

        // descendingKeySet otherwise startsWith will return correspond to longer command
        // e.g. 'de' will match startWith('d')
        for (String key : commandMap.descendingKeySet()) {
            if (command.startsWith(key)) {
                Method method = commandMap.get(key);
                if (method.getParameterTypes().length == 0) {
                    try {
                        method.invoke(com);
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                else if (method.getParameterTypes()[0] == String.class) {
                    String arg = command.substring(key.length());
                    try {
                        method.invoke(com, arg);
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                return continuePrompt;
            }
        }

        return continuePrompt;
    }

}
