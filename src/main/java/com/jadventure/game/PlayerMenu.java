package com.jadventure.game;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Hawk554
 * Date: 11/12/13
 * Time: 12:16 PM
 * This menu is to be called from Game.java once it has started.
 */
public class PlayerMenu extends Menus {
    public static Player player;

    PlayerMenu(Player p) {
        this.menuID = 20;
        player = p;

        this.menuItems.add(new MenuItem("Stats", "Displays player stats"));
        this.menuItems.add(new MenuItem("Backpack", "Displays current items in backpack"));
        this.menuItems.add(new MenuItem("Save", "Save Game"));
        this.menuItems.add(new MenuItem("Exit", "Exits back to Main Menu"));

        boolean continueGame = true;
        while (continueGame == true) {
            MenuItem selectedItem = displayMenu(this.menuItems);
            if(!playerMenuSelected(selectedItem)) {
                continueGame = false;
            }
        }
    }

    public static boolean playerMenuSelected(MenuItem m){
        Scanner input = new Scanner(System.in);
        String key = m.getKey();

        if (key.equals("stats")){
            player.getStats();
        }
        else if (key.equals("backpack")){
            player.getBackPack();
        }
        else if (key.equals("save")){
            player.save();
        }
        else if (key.equals("debug")){
            new DebugMenu(player);
        }
        else if (key.equals("exit")){
            return false;
        }
        return true;
    };
    
}
