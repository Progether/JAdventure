package com.jadventure.game;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import com.jadventure.game.entities.NPC;
import com.jadventure.game.entities.Player;
import com.jadventure.game.items.Item;
import com.jadventure.game.items.ItemStack;
import com.jadventure.game.items.Storage;
import com.jadventure.game.navigation.Direction;
import com.jadventure.game.navigation.ILocation;

public class TextBuilderVisitor implements IGameElementVisitor {
	private static final String NL = System.getProperty("line.separator");
	private StringBuilder bldr = new StringBuilder();


	@Override
	public void visit(Item item) {
		bldr.append(NL).append("Item '").append(item.getName()).append("'");
		if (item.getDescription() != null) {
			bldr.append(NL).append("  ").append(item.getDescription());
		}
        for (Entry<String, Integer> entry : item.getProperties().entrySet()) {
            bldr.append(NL).append("  ").append(entry.getKey()).append(": ").append(entry.getValue());
        }
	}

	@Override
	public void visit(ILocation location) {
		bldr.append(NL).append("Location '").append(location.getTitle()).append("'");
		bldr.append(NL).append("  Description '").append(location.getDescription()).append("'");
		for (Item item : location.getItems()) {
			visit(item);
		}
		bldr.append(NL).append("Directions you can go from here:");
		for (Map.Entry<Direction, ILocation> direction : location.getExits().entrySet()) {
			bldr.append(NL).append("  ").append(direction.getKey().getDescription()).append(", ");
			bldr.append(direction.getValue().getDescription());
		}
//        ArrayList<Item> publicItems = getItems();
//        if (!publicItems.isEmpty()) {
//            QueueProvider.offer("Items:");
//            for (Item item : publicItems) {
//                QueueProvider.offer("    "+item.getName());
//            }
//        }
//        ArrayList<NPC> npcs = getNPCs();
//        if (!npcs.isEmpty()) {
//            QueueProvider.offer("NPCs:");
//            for (NPC npc : npcs) {
//                QueueProvider.offer("   "+npc.getName());
//            }
//        }
//        QueueProvider.offer("");
//        QueueProvider.offer("");
//        for (Map.Entry<Direction,ILocation> direction : getExits().entrySet()) {
//            System.out.print(direction.getKey().getDescription() + ", ");
//            QueueProvider.offer(direction.getValue().getDescription());
//        }
	}

	@Override
	public void visit(Storage storage) {
		bldr.append(NL).append("Storage:");
		for (ItemStack is : storage.getItems()) {
			this.visit(is.getItem());
		}
	}
	
	@Override
	public void visit(Player player) {
		bldr.append(NL).append("Player '").append(player.getName()).append("'");

		bldr.append(NL).append("  Gold: '").append(player.getName()).append("'");
		bldr.append(NL).append("  Health / Max '").append(player.getHealth()).append("' / '").append(player.getHealthMax()).append("'");
		bldr.append(NL).append("  Damage / Armour '").append(player.getDamage()).append("' / '").append(player.getArmour()).append("'");
		bldr.append(NL).append("  Strength '").append(player.getStrength()).append("'");
		bldr.append(NL).append("  Intelligence '").append(player.getIntelligence()).append("'");
		bldr.append(NL).append("  Dexterity '").append(player.getDexterity()).append("'");
		bldr.append(NL).append("  Luck '").append(player.getLuck()).append("'");
		bldr.append(NL).append("  Players Level '").append(player.getLevel()).append("'");

//                "\nCurrent weapon: " + weaponName +
	}

	@Override
	public String toString() {
		return bldr.toString();
	}

    @Override
    public void append(String msg) {
        bldr.append(NL).append(msg);
    }

}
