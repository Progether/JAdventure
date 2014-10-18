package com.jadventure.game.prompts;

import com.jadventure.game.entities.Player;
import com.jadventure.game.monsters.Monster;
import com.jadventure.game.monsters.MonsterFactory;
import com.jadventure.game.navigation.Direction;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.navigation.LocationType;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.DeathException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
"attack (a)<monster>: Attacks an monster.\n" +
"lookaround (la):     Prints out what is around you.\n" +
"monster (m):         Prints out the monsters around you.\n\n" +
"Player\n" +
"-------------------------------------------------------------\n" + 
"view (v)<s/e/b>:     Views your stats, equipped items or backpack respectively.\n" +
"Game\n" +
"-------------------------------------------------------------\n" + 
"save (s):            Save your progress.\n" +
"exit:                Exit the game and return to the main menu.";

    private HashMap<String, String> directionLinks = new HashMap<String,String>()
    {{
         put("n", "north");
         put("s", "south");
         put("e", "east");
         put("w", "west");
         put("u", "up");
         put("d", "down");
    }};

    public static CommandCollection getInstance() {
        return INSTANCE;
    }

    public void initPlayer(Player player) {
        this.player = player;
    }

    // command methods here

    @Command(command="help", aliases="h", description="Prints help")
    @SuppressWarnings("UnusedDeclaration")
    public void command_help() {
        QueueProvider.offer(helpText);
        if (player.getName().equals("test")) {
            QueueProvider.offer("debug:               Starts Debugging.\n");
        } else {
            QueueProvider.offer("\n");
        }
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
        if (player.getName().equals("test")) {
            new DebugPrompt(player);
        } else {
            QueueProvider.offer("You don't have access to this function");
        }
    }

    @Command(command="goto", aliases="g", description="Goto a direction")
    @SuppressWarnings("UnusedDeclaration")
    public void command_g(String arg) throws DeathException {
        ILocation location = player.getLocation();

        try {
            arg = directionLinks.get(arg);
            Direction direction = Direction.valueOf(arg.toUpperCase());
            Map<Direction, ILocation> exits = location.getExits();
            if (exits.containsKey(direction)) {
                ILocation newLocation = exits.get(Direction.valueOf(arg.toUpperCase()));
                if (!newLocation.getLocationType().equals(LocationType.WALL)) {
                    player.setLocation(newLocation);
                    player.getLocation().print();
                    Random random = new Random();
                    if (player.getLocation().getMonsters().size() == 0) {
                        MonsterFactory monsterFactory = new MonsterFactory();
                        int upperBound = random.nextInt(player.getLocation().getDangerRating() + 1);
                        for (int i = 0; i < upperBound; i++) {
                            Monster monster = monsterFactory.generateMonster(player);
                            player.getLocation().addMonster(monster);
                        }
                    }
                    if (random.nextDouble() < 0.5) {
                        ArrayList<Monster> monsters = player.getLocation().getMonsters();
                        if (monsters.size() > 0) {
                            int posMonster = random.nextInt(monsters.size());
                            String monster = monsters.get(posMonster).monsterType;
                            QueueProvider.offer("A " + monster + " is attacking you!");
                            player.attack(monster);
                        }
                    }
                } else {
                    QueueProvider.offer("You cannot walk through walls.");
                }
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

    @Command(command="view", aliases="v", description="View details")
    @SuppressWarnings("UnusedDeclaration")
    public void command_v(String arg) {
        arg = arg.trim();
        switch (arg) {
            case "s":
            case "stats":
                player.getStats();
                break;
            case "e":
            case "equipped":
                player.printEquipment();
                break;
            case "b":
            case "backpack":
                player.printStorage();
                break;
            default:
                QueueProvider.offer("That is not a valid display");
                break;
        }
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
