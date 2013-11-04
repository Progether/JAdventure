package com.jadventure.game;

import java.util.HashMap;

public class Player extends Entity {

    public Player(String typeOfPlayer){
     // this needs to be worked on, these values should have a default value
     // set for the begining of the game.
     /*
     Random randGen = new Random();
     int healthMax = 20, armour = 1, rand;
     double damage = 0;
     for (int i = 0; i < 120; i++) {
         rand = randGen.nextInt(3);
         if (rand == 0) {
             healthMax++;
         } else if (rand == 1) {
             armour++;
         } else {
             double damageIncrement = (randGen.nextDouble() * 2.2);
             damage += damageIncrement;
         }
     }
     //	round damage
     damage = Math.round(damage * 4);
     damage /= 4; //	Wasn't working above... (always x.0)
     */
     if (typeOfPlayer.equals("new")) {
         this.healthMax = 100;
         this.armour = 1;
         this.damage = 50;
         this.level = 8;
     } else {
         @SuppressWarnings("unchecked")
         HashMap<String,String> playerData = new JSONReader().getProfileData(typeOfPlayer);
         this.name = playerData.get("name");
         this.healthMax = Integer.parseInt(playerData.get("healthMax"));
         this.armour = Integer.parseInt(playerData.get("armour"));
         this.damage = Integer.parseInt(playerData.get("damage"));
         this.level = Integer.parseInt(playerData.get("level"));
     }
    }

}
