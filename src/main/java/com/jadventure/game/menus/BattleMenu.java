 package com.jadventure.game.menus;

 import com.jadventure.game.entities.Entity;
 import com.jadventure.game.entities.Player;
 import com.jadventure.game.monsters.Monster;
 import com.jadventure.game.QueueProvider;
 
 import java.util.Random;

 public class BattleMenu extends Menus {

     private Monster opponent;
     private Player player;
     private Random random;

     public BattleMenu(Monster opponent, Player player) {
      
         this.random = new Random();
         this.opponent = opponent;
         this.player = player;
         this.menuItems.add(new MenuItem("Attack", "Attack " + opponent.monsterType + "."));
         this.menuItems.add(new MenuItem("Defend", "Defend against " + opponent.monsterType + "'s attack."));
         this.menuItems.add(new MenuItem("Use", "Use item"));

         while (opponent.getHealth() > 0 && player.getHealth() > 0) {
             QueueProvider.offer("What is your choice?");
             MenuItem selectedItem = displayMenu(this.menuItems);
             testSelected(selectedItem);       
         } 
	    if (player.getHealth() == 0) {
	         QueueProvider.offer("You died... Start again? (y/n)");
	         String reply = QueueProvider.take();
	         if (reply.equals("y")) {
	            //TODO: restart game
	         } else if (reply.equals("n")) {
		        //TODO: Exit game
              }
	    }  else if (opponent.getHealth() == 0) {
	         int xp = calculateXPGain();
	         QueueProvider.offer("You killed " + opponent.monsterType + "! You have gained " + xp + " XP");
      }
     }

     private void testSelected(MenuItem m) {
          String key = m.getKey();
          switch (m.getKey()) {
               case "attack": {
                    attack(player, opponent);
		    attack(opponent, player);
                    break;
               }
               case "defend": {
                    defend(true);
                    break;
               }
               case "use": {
                    use();
                    break;
               }
               default: {
                    break;
               }
          }
     }

     private void attack(Entity attacker, Entity defender) {
          //TODO:
          //if (isPlayer) {
               double damage = attacker.getDamage();
               double critCalc = random.nextDouble();
               if (critCalc < attacker.getCritChance()) {
                    damage += damage;
                    QueueProvider.offer("Crit hit! Damage has been doubled!");
               }
               int healthReduction = (int) ((((3 * attacker.getLevel() / 60 + 0.5) * damage * damage / defender.getArmour() / 100) + 2) * random.nextDouble());
               defender.setHealth((defender.getHealth() - healthReduction));
               if (defender.getHealth() < 0) {
                    defender.setHealth(0);
               }
               QueueProvider.offer(healthReduction + " damage dealt!");
	       if (attacker instanceof Player) {
		    Monster m = (Monster) defender;
                    QueueProvider.offer(m.monsterType + "' health is " + m.getHealth());
	       } else {
		    QueueProvider.offer("Your health is " + defender.getHealth());
	       }
          //}
     }

     private void defend(boolean isPlayer) {
          //TODO:
     }

     private void use() {
          new UseMenu(player.getStorage().getItems(), player);
     }

     private int calculateXPGain() {
	     return 1;
     }  
 }

