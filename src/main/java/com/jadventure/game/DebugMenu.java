package com.jadventure.game;

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
        while (continueMenu == true) {
            MenuItem selectedItem = displayMenu(this.menuItems);
            if(!debugMenuSelected(selectedItem)) {
                continueMenu = false;
            }
        }
    }

    public static boolean debugMenuSelected(MenuItem m){
        Scanner input = new Scanner(System.in);
        String key = m.getKey();
         if (key.equals("exit")) {
             return false;
         }
         return true;
    }
}
