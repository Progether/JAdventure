package com.jadventure.game.menus;

import com.jadventure.game.entities.Player;
import com.jadventure.game.DeathException;
import com.jadventure.game.Game;
import com.jadventure.game.QueueProvider;

/**
 * Called when creating a new Player
 */
public class ChooseClassMenu extends Menus {

    public ChooseClassMenu() throws DeathException {
        this.menuItems.add(new MenuItem("Recruit", "A soldier newly enlisted to guard the city of Silliya"));
        this.menuItems.add(new MenuItem("SewerRat", "A member of the underground of Silliya"));

        while(true) {
            QueueProvider.offer("Choose a class to get started with:");
            MenuItem selectedItem = displayMenu(this.menuItems);
            if(testOption(selectedItem)) {
            	break;
            }
        }
    }

    private static boolean testOption(MenuItem m) throws DeathException {
        String key = m.getKey();
        if(key.equals("recruit")) {
            Player player = Player.getInstance("recruit");
            new Game(player, "new");
            return true;
        } else if(key.equals("sewerrat")) {
            Player player = Player.getInstance("sewerrat");
            new Game(player, "new");
            return true;
        } else {
            return false;
        }
    }
}
