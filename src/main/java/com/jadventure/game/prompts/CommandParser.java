package com.jadventure.game.prompts;

import com.jadventure.game.entities.Player;
import com.jadventure.game.QueueProvider;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TreeMap;

/**
 * CommandParser parses the game commands
 *
 * It parses all the commands automatically.
 * To add a new command, you just need to make addition in the CommandCollection.
 */
public class CommandParser {
    Player player;
    private TreeMap<String, Method> commandMap;

    public CommandParser(Player player){
        this.player = player;
        commandMap = new TreeMap<String, Method>();

        initCommandMap();
    }

    // adds the command to the commandMap
    private void initCommandMap() {
        Method[] methods = CommandCollection.class.getMethods();

        for(Method method: methods){
            if(!method.isAnnotationPresent(Command.class))
                continue;

            Command annotation = method.getAnnotation(Command.class);
            this.commandMap.put(annotation.command(), method);
            for(String alias : annotation.aliases().split(",")){
                if(alias.length() == 0)
                    break;
                this.commandMap.put(alias, method);
            }
        }
    }

    public boolean parse(Player player, String command, String[] args) {
        CommandCollection com = CommandCollection.getInstance();
        com.initPlayer(player);
        System.out.println("Command '" + command + "' " + toString(args));

        if(command.equalsIgnoreCase("exit"))
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
                    String arg = args[0];
                    System.out.println("Argument '" + arg + "'");
                    try {
                        method.invoke(com, arg);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        }

        return true;
    }

    public static String toString(String[] args) {
        boolean isFirstItem = true;
        StringBuilder bldr = new StringBuilder("[");
        for (String arg : args) {
            if (isFirstItem) {
                bldr.append(" ");
            }
            else {
                bldr.append(", ");
            }
            bldr.append(arg);
            isFirstItem = false;
        }
        if (! isFirstItem) {
            bldr.append(" ");
        }
        bldr.append("]");
        return bldr.toString();
    }

}
