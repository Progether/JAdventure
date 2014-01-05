package com.jadventure.game.prompts;

import com.jadventure.game.entities.Player;

import java.lang.annotation.Annotation;
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

        initCommandMap();
    }

    private void initCommandMap() {
        Method[] methods = CommandCollection.class.getMethods();

        for(Method method: methods){
            if(!method.isAnnotationPresent(Command.class))
                continue;

            Command annotation = method.getAnnotation(Command.class);
            this.commandMap.put(annotation.command(), method);
            for(String alias : annotation.aliases().split(",")){
                this.commandMap.put(alias, method);
            }
        }

        System.out.println(commandMap);
    }

    public boolean parse(Player player, String command, boolean continuePrompt) {
        CommandCollection com = CommandCollection.getInstance();
        com.initPlayer(player);

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
