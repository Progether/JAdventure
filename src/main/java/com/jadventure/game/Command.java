package com.jadventure.game;

import com.jadventure.game.entities.Player;
import com.jadventure.game.monsters.Monster;
import com.jadventure.game.monsters.MonsterFactory;
import com.jadventure.game.navigation.Direction;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.prompts.DebugPrompt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Command contains the declaration of the methods mapped to game commands
 *
 * The declared command methods are accessed by reflection and not directly accessed
 */
public enum Command {
    INSTANCE;

    public Player player;

    private String helpText = "\nstats: Prints your statistics.\n" +
            "backpack: Prints out the contents of your backpack.\n" +
            "save: Save your progress.\n" +
            "goto: Go in a direction.\n" +
            "exit: Exit the game and return to the main menu.\n";

    public static Command getInstance() {
        return INSTANCE;
    }

    public void initPlayer(Player player){
        this.player = player;
    }

    // command methods here
    @SuppressWarnings("UnusedDeclaration")
    public void command_st(){
        player.getStats();
    }

    @SuppressWarnings("UnusedDeclaration")
    public void command_help(){
        System.out.println(helpText);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void command_b(){
        player.printBackPack();
    }

    @SuppressWarnings("UnusedDeclaration")
    public void command_save(){
        player.save();
    }

    @SuppressWarnings("UnusedDeclaration")
    public void command_m(){
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

    @SuppressWarnings("UnusedDeclaration")
    public void command_debug(){
        new DebugPrompt(player);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void command_g(String arg){
        HashMap<String, String> directionLinks = new HashMap<String,String>()
        {{
                put("n", "north");
                put("s", "south");
                put("e", "east");
                put("w", "west");
            }};
        ILocation location = player.getLocation();

        try {
            arg = directionLinks.get(arg);
            Direction direction = Direction.valueOf(arg.toUpperCase());
            Map<Direction, ILocation> exits = location.getExits();

            if (exits.containsKey(direction)) {
                ILocation newLocation = exits.get(Direction.valueOf(arg.toUpperCase()));
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

    @SuppressWarnings("UnusedDeclaration")
    public void command_e(String arg){
        player.equipItem(arg.trim());
    }

    @SuppressWarnings("UnusedDeclaration")
    public void command_de(String arg){
        player.dequipItem(arg.trim());
    }

    @SuppressWarnings("UnusedDeclaration")
    public void command_p(String arg){
        player.pickUpItem(arg.trim());
    }

    @SuppressWarnings("UnusedDeclaration")
    public void command_d(String arg){
        player.dropItem(arg.trim());
    }
}
