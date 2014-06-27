package com.jadventure.game.prompts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jadventure.game.GameBeans;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.entities.Player;
import com.jadventure.game.monsters.Monster;
import com.jadventure.game.monsters.MonsterFactory;
import com.jadventure.game.navigation.Direction;
import com.jadventure.game.navigation.ILocation;

/**
 * CommandCollection contains the declaration of the methods mapped to game commands
 *
 * The declared command methods are accessed only by reflection.
 * To declare a new command, add an appropriate method to this class and Annotate it with
 * Command(command, aliases, description)
 */
public enum CommandCollection {
    INSTANCE;

    public Player player;

    private String helpText = "\nstats (st): Prints your statistics.\n"
            + "backpack (b): Prints out the contents of your backpack.\n"
            + "monster (m): Shows the monsters around you."
            + "save: Save your progress.\n"
            + "goto (g): Go in a direction.\n"
            + "inspect (i): Inspect an item.\n"
            + "dequip (e): ...\n"
            + "dequip (de): ...\n"
            + "pick (p): Pick up an item.\n"
            + "drop (d): Drop an item.\n"
            + "exit: Exit the game and return to the main menu.\n"
            + "debug: ...\n";

    private Map<String, String> directionLinks = new HashMap<String, String>() {
        {
            put("n", "north");
            put("s", "south");
            put("e", "east");
            put("w", "west");
        }
    };

    public static CommandCollection getInstance() {
        return INSTANCE;
    }

    public void initPlayer(Player player){
        this.player = player;
    }

    // command methods here

    @Command(command="stats", aliases="st", description="Returns players statistics")
    @SuppressWarnings("UnusedDeclaration")
    public void command_st(){
        player.getStatistics();
    }

    @Command(command="help", aliases="", description="Prints help")
    @SuppressWarnings("UnusedDeclaration")
    public void command_help(){
        QueueProvider.offer(helpText);
    }

    @Command(command="backpack", aliases="b", description="Backpack contents")
    @SuppressWarnings("UnusedDeclaration")
    public void command_b(){
        player.printBackPack();
    }

    @Command(command="save", aliases="", description="Save the game")
    @SuppressWarnings("UnusedDeclaration")
    public void command_save(){
//        player.save();
        GameBeans.getPlayerRepository().save(player);
    }

    @Command(command="monster", aliases="m", description="Monsters around you")
    @SuppressWarnings("UnusedDeclaration")
    public void command_m(){
        List<Monster> monsterList = player.getLocation().getMonsters();
        if (monsterList.size() > 0) {
            QueueProvider.offer("Monsters around you:");
            QueueProvider.offer("----------------------------");
            for (Monster monster : monsterList) {
                QueueProvider.offer(monster.monsterType);
            }
            QueueProvider.offer("----------------------------");
        } else {
            QueueProvider.offer("There are no monsters around you");
        }
    }

    @Command(command="debug", aliases="", description="Start debugging")
    @SuppressWarnings("UnusedDeclaration")
    public void command_debug(){
        new DebugPrompt(player);
    }


    @Command(command="look", aliases="l", description="Look around")
    @SuppressWarnings("UnusedDeclaration")
    public void command_look(){
        player.getLocation().print();
    }
    
    
    @Command(command="goto", aliases="g", description="Goto a direction")
    @SuppressWarnings("UnusedDeclaration")
    public void command_g(String arg){
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
                QueueProvider.offer("The is no exit that way.");
            }
        } catch (IllegalArgumentException ex) {
            QueueProvider.offer("That direction doesn't exist");
        } catch (NullPointerException ex) {
            QueueProvider.offer("That direction doesn't exist");
        }
    }
    @Command(command="inspect", aliases="i", description="Inspect an item")
    @SuppressWarnings("UnusedDeclaration")
    public void command_i(String arg){
        player.inspectItem(arg.trim());
    }

    @Command(command="equip", aliases="e", description="Equip an item")
    @SuppressWarnings("UnusedDeclaration")
    public void command_e(String arg){
        player.equipItem(arg.trim());
    }

    @Command(command="dequip", aliases="de", description="Dequip an item")
    @SuppressWarnings("UnusedDeclaration")
    public void command_de(String arg){
        player.dequipItem(arg.trim());
    }

    @Command(command="pick", aliases="p", description="Pick up an item")
    @SuppressWarnings("UnusedDeclaration")
    public void command_p(String arg){
        player.pickUpItem(arg.trim());
    }

    @Command(command="drop", aliases="d", description="Drop an item")
    @SuppressWarnings("UnusedDeclaration")
    public void command_d(String arg){
        player.dropItem(arg.trim());
    }
}
