package com.jadventure.game.menus;

import com.jadventure.game.menus.Menus;
import com.jadventure.game.entities.Player;
import java.util.Scanner;

/**
 * Debug menu, it's not implemented completely.
 */
public class DebugMenu extends Menus {
    public static Player player;

    public DebugMenu(Player p) {
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
        while (continueMenu) {
            MenuItem selectedItem = displayMenu(this.menuItems);
            testOption(selectedItem);

            continueMenu = !didSelectExit(selectedItem);
        }
    }
    private static boolean didSelectExit(MenuItem m){
        return (m.getKey().equalsIgnoreCase("exit"));
    }
    private static void testOption(MenuItem m){
        Scanner input = new Scanner(System.in);
        String key = m.getKey();
    }
}
