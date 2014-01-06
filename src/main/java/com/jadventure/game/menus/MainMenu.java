package com.jadventure.game.menus;

import com.jadventure.game.menus.Menus;

import java.util.Scanner;
import com.jadventure.game.Game;
import com.jadventure.game.entities.Player;
import com.jadventure.game.menus.ChooseClassMenu;

/**
 * The first menu diplayed on user screen
 * @see JAdventure
 * This menu lets the player choose whether to load an exiting game,
 * start a new one, or exit to the terminal.
 */
public class MainMenu extends Menus {

     public MainMenu(){
         this.menuItems.add(new MenuItem("Start", "Starts a new Game", "new"));
         this.menuItems.add(new MenuItem("Load", "Loads an existing Game"));
         this.menuItems.add(new MenuItem("Exit", null, "quit"));

         while(true) {
             MenuItem selectedItem = displayMenu(this.menuItems);
             testOption(selectedItem);
         }
     }
    private static void testOption(MenuItem m) {
        Scanner input = new Scanner(System.in);
        String key = m.getKey();
        if(key.equals("start")) {
            new ChooseClassMenu();
        }
        else if(key.equals("exit")) {
            System.out.println("Goodbye!");
            System.exit(0);
        }
        else if(key.equals("load")) {
            System.out.println("What is the name of the avatar you want to load?");
            Player player = null;

            while (player == null) {
                key = input.next();
                if (Player.profileExists(key)) {
                    player = Player.load(key);
                } else {
                    System.out.println("That user doesn't exist. Try again.");
                }
            }

            Game game = new Game(player, "old");
        }
    }
}
