package com.jadventure.game.command;

import java.util.HashMap;
import java.util.Map;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;
import com.jadventure.game.monsters.Monster;
import com.jadventure.game.monsters.MonsterFactory;
import com.jadventure.game.navigation.Direction;
import com.jadventure.game.navigation.ILocation;

public final class GotoCommand extends AbstractCommand {
	private static Map<String, String> directionLinks = new HashMap<String, String>();
	
	static {
    	directionLinks.put("n", "north");
    	directionLinks.put("s", "south");
    	directionLinks.put("e", "east");
    	directionLinks.put("w", "west");
    }


	public GotoCommand() {
        super("goto", "g", "Go in a direction.");
    }

    @Override
    public void execute(Player player, IGameElementVisitor visitor, String[] args) {
        try {
            String directionStr = directionLinks.get(args[0].substring(0, 1));
            Direction direction = Direction.valueOf(directionStr.toUpperCase());
            Map<Direction, ILocation> exits = player.getLocation().getExits();

            if (exits.containsKey(direction)) {
                ILocation newLocation = exits.get(direction);
                player.setLocation(newLocation);
                visitor.visit(player.getLocation());
//                player.getLocation().print();
                MonsterFactory monsterFactory = new MonsterFactory();
                Monster monster = monsterFactory.generateMonster(player);
                player.getLocation().setMonsters(monster);
            }
            else {
            	visitor.append("You can't go " + directionLinks.get(directionStr) + " from here.");
            }
        }
        catch (IllegalArgumentException ex) {
        	visitor.append("That direction " + args[0] + " doesn't exist");
        }
        catch (NullPointerException ex) {
        	visitor.append("That direction " + args[0] + " doesn't exist");
        }
    }

}
