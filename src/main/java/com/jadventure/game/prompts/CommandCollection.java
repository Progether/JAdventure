package com.jadventure.game.prompts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jadventure.game.GameBeans;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.TextBuilderVisitor;
import com.jadventure.game.command.BackpackCommand;
import com.jadventure.game.command.DequipCommand;
import com.jadventure.game.command.DropCommand;
import com.jadventure.game.command.EquipCommand;
import com.jadventure.game.command.HelpCommand;
import com.jadventure.game.command.InspectCommand;
import com.jadventure.game.command.MonsterCommand;
import com.jadventure.game.command.PickCommand;
import com.jadventure.game.command.SaveCommand;
import com.jadventure.game.command.StatisticsCommand;
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

    @Deprecated
    private String helpText = "\nstats (st): Prints your statistics.\n"
            + "backpack (b): Prints out the contents of your backpack.\n"
            + "monster (m): Shows the monsters around you."
            + "save: Save your progress.\n"
            + "goto (g): Go in a direction.\n"
            + "look (l): Look at current location.\n"
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
    public void command_st() {
//        Exists as ICommand
//        player.getStatistics();
    	TextBuilderVisitor textBuilder = new TextBuilderVisitor();
    	new StatisticsCommand().execute(player, textBuilder, null);
    	QueueProvider.offer(textBuilder.toString());
    }

    @Command(command="help", aliases="?", description="Prints help")
    public void command_help() {
//        Exists as ICommand
    	TextBuilderVisitor textBuilder = new TextBuilderVisitor();
    	new HelpCommand().execute(player, textBuilder, null);
        QueueProvider.offer(textBuilder.toString());
    }

    @Command(command="backpack", aliases="b", description="Backpack contents")
    public void command_b() {
//        Exists as ICommand
//        player.printBackPack();
        TextBuilderVisitor visitor = new TextBuilderVisitor();
        new BackpackCommand().execute(player, visitor, null);
        QueueProvider.offer(visitor.toString());
    }

    @Command(command="save", aliases="", description="Save the game")
    public void command_save() {
//        Exists as ICommand
//        player.save();
    	new SaveCommand().execute(player, null, null);
    }

    @Command(command="monster", aliases="m", description="Monsters around you")
    public void command_m() {
//      Exists as ICommand
        TextBuilderVisitor visitor = new TextBuilderVisitor();
        new MonsterCommand().execute(player, visitor, null);
        QueueProvider.offer(visitor.toString());
    }

    @Command(command="debug", aliases="", description="Start debugging")
    public void command_debug() {
        new DebugPrompt(player);
    }


    @Command(command="look", aliases="l", description="Look around")
    public void command_look() {
//      Exists as ICommand
//        player.getLocation().print();
    	TextBuilderVisitor textBuilder = new TextBuilderVisitor();
    	textBuilder.visit(player.getLocation());
    	System.out.println(textBuilder);
    }
    
    
    @Command(command="goto", aliases="g", description="Goto a direction")
    public void command_g(String arg) {
        ILocation location = player.getLocation();

        try {
            arg = directionLinks.get(arg.substring(0, 1));
            Direction direction = Direction.valueOf(arg.toUpperCase());
            Map<Direction, ILocation> exits = location.getExits();

            if (exits.containsKey(direction)) {
                ILocation newLocation = exits.get(Direction.valueOf(arg.toUpperCase()));
                player.setLocation(newLocation);
                player.getLocation().print();
                MonsterFactory monsterFactory = new MonsterFactory();
                Monster monster = monsterFactory.generateMonster(player);
                player.getLocation().setMonsters(monster);
            }
            else {
                QueueProvider.offer("You can't go that way.");
            }
        }
        catch (IllegalArgumentException ex) {
            QueueProvider.offer("That direction doesn't exist");
        }
        catch (NullPointerException ex) {
            QueueProvider.offer("That direction doesn't exist");
        }
    }
    @Command(command="inspect", aliases="i", description="Inspect an item")
    public void command_i(String arg) {
        // Exists as ICommand
//        System.out.println("Trying to inspecting '" + arg + "'");
//        player.inspectItem(arg.trim());
    	TextBuilderVisitor textBuilder = new TextBuilderVisitor();
        new InspectCommand().execute(player, textBuilder, new String[] {arg});
        QueueProvider.offer(textBuilder.toString());
    }

    @Command(command="equip", aliases="e", description="Equip an item")
    public void command_e(String arg) {
        // Exists as ICommand
//        player.equipItem(arg.trim());
    	TextBuilderVisitor textBuilder = new TextBuilderVisitor();
        new EquipCommand().execute(player, textBuilder, new String[] {arg});
        QueueProvider.offer(textBuilder.toString());
    }

    @Command(command="dequip", aliases="de", description="Dequip an item")
    public void command_de(String arg) {
        // Exists as ICommand
//        player.dequipItem(arg.trim());
    	TextBuilderVisitor textBuilder = new TextBuilderVisitor();
        new DequipCommand().execute(player, textBuilder, new String[] {arg});
        QueueProvider.offer(textBuilder.toString());
    }

    @Command(command="pick", aliases="p", description="Pick up an item")
    public void command_p(String arg) {
        // Exists as ICommand
//        player.pickUpItem(arg.trim());
    	TextBuilderVisitor textBuilder = new TextBuilderVisitor();
        new PickCommand().execute(player, textBuilder, new String[] {arg});
        QueueProvider.offer(textBuilder.toString());
    }

    @Command(command="drop", aliases="d", description="Drop an item")
    public void command_d(String arg) {
        // Exists as ICommand
//        player.dropItem(arg.trim());
    	TextBuilderVisitor textBuilder = new TextBuilderVisitor();
        new DropCommand().execute(player, textBuilder, new String[] {arg});
        QueueProvider.offer(textBuilder.toString());
    }
}
