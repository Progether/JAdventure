package com.jadventure.game.menus;

import com.jadventure.game.menus.Menus;
import com.jadventure.game.menus.MenuItem;
import com.jadventure.game.entities.Player;
import com.jadventure.game.Game;

import java.util.Scanner;

public class ChooseClassMenu extends Menus {

    public ChooseClassMenu() {
        this.menuItems.add(new MenuItem("Recruit", "A soldier newly enlisted to guard the city of Silliya"));

        while(true) {
            System.out.println("Choose a class to get started with:");
            MenuItem selectedItem = displayMenu(this.menuItems);
            testOption(selectedItem);
        }
    }

    private static void testOption(MenuItem m) {
        Scanner input = new Scanner(System.in);
        String key = m.getKey();
        if(key.equals("recruit")) {
            Player player = Player.getInstance("recruit");
            new Game(player, "new");
        }
    }
}
