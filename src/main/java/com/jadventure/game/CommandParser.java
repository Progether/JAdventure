package com.jadventure.game;

import com.jadventure.game.entities.Player;
import com.jadventure.game.menus.DebugMenu;
import com.jadventure.game.navigation.Direction;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.monsters.MonsterFactory;
import com.jadventure.game.monsters.Monster;

import java.util.Map;
import java.util.ArrayList;

public class CommandParser {

    private String helpText = "\nstats: Prints your statistics.\n" +
                              "backpack: Prints out the contents of your backpack.\n" +
                              "save: Save your progress.\n" +
                              "goto: Go in a direction.\n" +
                              "exit: Exit the game and return to the main menu.\n";

    public boolean parse(Player player, String command, boolean continuePrompt) {
        if (command.equals("st")) {
            player.getStats();
        } else if (command.equals("help")) {
            System.out.print(helpText);
        }
        else if (command.equals("b")) {
            player.printBackPack();
        }
        else if (command.equals("s")) {
            player.save();
        } else if (command.startsWith("goto")) {
            String message = command.substring(4);
            ILocation location = player.getLocation();

            try {
                Direction direction = Direction.valueOf(message.toUpperCase());
                Map<Direction, ILocation> exits = location.getExits();

                if (exits.containsKey(direction)) {
                    ILocation newLocation = exits.get(Direction.valueOf(message.toUpperCase()));
                    player.setLocation(newLocation);
                    player.getLocation().print();
                    MonsterFactory monsterFactory = new MonsterFactory();
                    Monster monster = monsterFactory.generateMonster(player);
                    player.getLocation().setMonsters(monster);
                } else {
                    System.out.println("The is no exit that way.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("The direction " + message + " does not exist.");
            }
        }
        else if (command.startsWith("p")) {
            String itemName = command.substring(6);
            player.pickUpItem(itemName);
        }
        else if (command.startsWith("m")){
            ArrayList<Monster> monsterList = player.getLocation().getMonsters();
            if (monsterList.size() > 0) {
                System.out.println("Monsters around you:");
                System.out.println("----------------------------");
                for (Monster monster : monsterList) {
                    System.out.println(monster.monsterType);
                }
                System.out.println("----------------------------");
            } else {
                System.out.println("There are no monsters around you");
            }
        }
        else if (command.startsWith("d")){
            String itemName = command.substring(4);
            player.dropItem(itemName);
        }
        else if (command.startsWith("e")) {
            String itemName = command.substring(5);
            player.equipItem(itemName);
        }
        else if (command.startsWith("de")) {
            String itemName = command.substring(6);
            player.dequipItem(itemName);
        }
        else if (command.equals("debug")) {
            new DebugMenu(player);
        } else if (command.equals("exit")) {
            continuePrompt = false;
        }
        return continuePrompt;
    }

}
