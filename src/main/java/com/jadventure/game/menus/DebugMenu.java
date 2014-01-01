package com.jadventure.game.menus;

import com.jadventure.game.menus.Menus;
import com.jadventure.game.entities.Player;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Hawk554
 * Date: 11/12/13
 * Time: 2:15 PM
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
        this.menuItems.add(new MenuItem("pBackpack", "Modify Player Backpack"));
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

        try{
            if(key.equals("pattack")){
                System.out.println("Current attack : " + player.getDamage());
                System.out.print("Enter new attack : ");
                int newVal = input.nextInt();
                player.setDamage(newVal);
            }
            else if(key.equals("pmaxhealth")){
                System.out.println("Current maximum health : " + player.getHealthMax());
                System.out.print("Enter new maximum health : ");
                int newVal = input.nextInt();
                player.setHealthMax(newVal);
                player.setHealth(newVal < player.getHealth() ? newVal : player.getHealth());
            }
            else if(key.equals("phealth")){
                System.out.println("Current health : " + player.getHealth());
                System.out.print("Enter new health : ");
                int newVal = input.nextInt();
                // we don't want collision values, do we?
                if(newVal > player.getHealthMax())
                    player.setHealthMax(newVal);
                player.setHealth(newVal);
            }
            else if(key.equals("parmour")){
                System.out.println("Current armour : " + player.getArmour());
                System.out.print("New armour value : ");
                int newVal = input.nextInt();
                player.setArmour(newVal);
            }
            else if(key.equals("plevel")){
                System.out.println("Current level : " + player.getArmour());
                System.out.print("New level : ");
                int newVal = input.nextInt();
                player.setLevel(newVal);
            }
            else if(key.equals("pgold")){
                System.out.println("Current gold : " + player.getArmour());
                System.out.print("New gold amount : ");
                int newVal = input.nextInt();
                player.setGold(newVal);
            }
		    else if(key.equals("pbackpack")){
                new BackpackDebugMenu(player);
		    }
		    else if(key.equals("stats"))
			    player.getStats();
        } catch(NumberFormatException e){
            System.out.println("Value not acceptable");
        }
    }
}
