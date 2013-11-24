package com.jadventure.game.menus;

import com.jadventure.game.menus.Menus;
import com.jadventure.game.Player;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Hawk554
 * Date: 11/12/13
 * Time: 2:15 PM
 */
public class DebugMenu extends Menus {
    public static Player player;

    DebugMenu(Player p) {
        this.menuID = 90;
        player = p;
        this.menuItems.add(new MenuItem("pAttack", "Modify Player Attack"));
        this.menuItems.add(new MenuItem("pHealth", "Modify Player Health"));
        this.menuItems.add(new MenuItem("pMaxHealth", "Modify Player Max Health"));
        this.menuItems.add(new MenuItem("pArmour", "Modify Player Armour"));
        this.menuItems.add(new MenuItem("pLevel", "Modify Player Level"));
        this.menuItems.add(new MenuItem("pGold", "Modify Player Gold"));
        this.menuItems.add(new MenuItem("pGold", "Modify Player Backpack"));
        this.menuItems.add(new MenuItem("Stats", "Display current player stats"));
        this.menuItems.add(new MenuItem("Exit", "Exits Debug Menu"));

        boolean continueMenu = true;
        // == true not neccessary below
        while (continueMenu) {
            MenuItem selectedItem = displayMenu(this.menuItems);
            testOption(selectedItem);
            
            //if the person didn't select exit, the game continues
            continueMenu = !didSelectExit(selectedItem);
        }
    }
    private static boolean didSelectExit(MenuItem m){
        return (m.getKey().equalsIgnoreCase("exit"));
    }
    private static void testOption(MenuItem m){
        Scanner input = new Scanner(System.in);
        String key = m.getKey();
        //put the options in here, check PlayerMenu for equivelent code
         
    }
}
