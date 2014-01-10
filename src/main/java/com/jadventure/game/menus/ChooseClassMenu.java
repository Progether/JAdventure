package com.jadventure.game.menus;

import com.jadventure.game.menus.Menus;
import com.jadventure.game.menus.MenuItem;
import com.jadventure.game.entities.Player;
import com.jadventure.game.Game;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

/**
 * Called when creating a new Player
 */
public class ChooseClassMenu extends Menus {
    public static BlockingQueue queue;

    public ChooseClassMenu(BlockingQueue q) {
        queue = q;
        this.menuItems.add(new MenuItem("Recruit", "A soldier newly enlisted to guard the city of Silliya"));
        this.menuItems.add(new MenuItem("SewerRat", "A member of the underground of Silliya"));

        while(true) {
            queue.offer("Choose a class to get started with:");
            MenuItem selectedItem = displayMenu(this.menuItems);
            if(testOption(selectedItem)) {
            	break;
            }
        }
    }

    private static boolean testOption(MenuItem m) {
        String key = m.getKey();
        if(key.equals("recruit")) {
            Player player = Player.getInstance(queue, "recruit");
            new Game(queue, player, "new");
            return true;
        } else if(key.equals("sewerrat")) {
            Player player = Player.getInstance(queue, "sewerrat");
            new Game(queue, player, "new");
            return true;
        } else {
            return false;
        }
    }
}
