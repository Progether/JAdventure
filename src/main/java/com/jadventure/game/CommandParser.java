package com.jadventure.game;

import com.jadventure.game.entities.Player;
import com.jadventure.game.menus.DebugMenu;
import com.jadventure.game.navigation.Direction;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.monsters.MonsterFactory;
import com.jadventure.game.monsters.Monster;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

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
        } else if (command.startsWith("g")) {
            String message = command.substring(1);
            HashMap<String, String> directionLinks = new HashMap<String,String>()
            {{
                 put("n", "north");
                 put("s", "south");
                 put("e", "east");
                 put("w", "west");
            }};
            ILocation location = player.getLocation();

            try {
                message = directionLinks.get(message);
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
                System.out.println("That direction doesn't exist");
            } catch (NullPointerException ex) {
                System.out.println("That direction doesn't exist");
            }
        }
        else if (command.startsWith("p")) {
            String itemName = command.substring(1);
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
            String itemName = command.substring(1);
            player.dropItem(itemName);
        }
        else if (command.equals("exit")) {
            continuePrompt = false;
        }
        else if (command.startsWith("e")) {
            String itemName = command.substring(1);
            player.equipItem(itemName);
        }
        else if (command.startsWith("de")) {
            String itemName = command.substring(2);
            player.dequipItem(itemName);
        }
        else if (command.equals("debug")) {
            new DebugMenu(player);
        }
        return continuePrompt;
    }

}
