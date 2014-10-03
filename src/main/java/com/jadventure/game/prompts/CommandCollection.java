package com.jadventure.game.prompts;

import com.jadventure.game.entities.Player;
import com.jadventure.game.monsters.Monster;
import com.jadventure.game.monsters.MonsterFactory;
import com.jadventure.game.navigation.Direction;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.DeathException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private String helpText = "\nActions\n" +
"-------------------------------------------------------------\n" + 
"goto (g)<direction>: Go in a direction.\n" +
"inspect (i)<item>:   Inspects an item.\n" +
"pick (p)<item>:      Picks up an item.\n" +
"drop (d)<item>:      Drops an item.\n" +
"equip (e)<item>:     Equips an item.\n" +
"unequip (ue)<item>:  Unequips an item.\n" +
"attack (a)<entity>:  Attacks an entity.\n" +
"lookaround (la):     Prints out what is around you.\n" +
"monster (m):         Prints out the monsters around you.\n\n" +
"Player\n" +
"-------------------------------------------------------------\n" + 
"stats (st):          Prints your statistics.\n" +
"backpack (b):        Prints out the contents of your backpack.\n\n" +
"Game\n" +
"-------------------------------------------------------------\n" + 
"save (s):            Save your progress.\n" +
"exit:                Exit the game and return to the main menu.\n" + 
"debug: starts debuggung.\n";

    private HashMap<String, String> directionLinks = new HashMap<String,String>()
    {{
         put("n", "north");
         put("s", "south");
         put("e", "east");
         put("w", "west");
    }};

    public static CommandCollection getInstance() {
        return INSTANCE;
    }

    public void initPlayer(Player player) {
        this.player = player;
    }

    // command methods here

    @Command(command="status", aliases="st", description="Returns player's status")
    @SuppressWarnings("UnusedDeclaration")
    public void command_st() {
        player.getStats();
    }

    @Command(command="help", aliases="h", description="Prints help")
    @SuppressWarnings("UnusedDeclaration")
    public void command_help() {
        QueueProvider.offer(helpText);
    }

    @Command(command="backpack", aliases="b", description="Backpack contents")
    @SuppressWarnings("UnusedDeclaration")
    public void command_b() {
        player.printBackPack();
    }

    @Command(command="save", aliases="s", description="Save the game")
    @SuppressWarnings("UnusedDeclaration")
    public void command_save() {
        player.save();
    }

    @Command(command="monster", aliases="m", description="Monsters around you")
    @SuppressWarnings("UnusedDeclaration")
    public void command_m() {
        ArrayList<Monster> monsterList = player.getLocation().getMonsters();
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
    public void command_debug() {
        new DebugPrompt(player);
    }

    @Command(command="goto", aliases="g", description="Goto a direction")
    @SuppressWarnings("UnusedDeclaration")
    public void command_g(String arg) {
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
                player.getLocation().addMonster(monster);
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
    public void command_i(String arg) {
        player.inspectItem(arg.trim());
    }

    @Command(command="equip", aliases="e", description="Equip an item")
    @SuppressWarnings("UnusedDeclaration")
    public void command_e(String arg) {
        player.equipItem(arg.trim());
    }

    @Command(command="unequip", aliases="ue", description="Unequip an item")
    @SuppressWarnings("UnusedDeclaration")
    public void command_ue(String arg) {
        player.dequipItem(arg.trim());
    }

    @Command(command="pick", aliases="p", description="Pick up an item")
    @SuppressWarnings("UnusedDeclaration")
    public void command_p(String arg) {
        player.pickUpItem(arg.trim());
    }

    @Command(command="drop", aliases="d", description="Drop an item")
    @SuppressWarnings("UnusedDeclaration")
    public void command_d(String arg) {
        player.dropItem(arg.trim());
    }

    @Command(command="attack", aliases="a", description="Attacks an entity")
    @SuppressWarnings("UnusedDeclaration")
    public void command_a(String arg) throws DeathException {
       player.attack(arg.trim());
    }

    @Command(command="lookaround", aliases="la", description="Displays the description of the room you are in.")
    @SuppressWarnings("UnusedDeclaration")
    public void command_la() {
       player.getLocation().print(); 
    }
}
