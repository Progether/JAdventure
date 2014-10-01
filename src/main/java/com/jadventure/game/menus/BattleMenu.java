 package com.jadventure.game.menus;

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
     }

     private void testSelected(MenuItem m) {
          String key = m.getKey();
          switch (m.getKey()) {
               case "attack": {
                    attack(true);
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

     private void attack(boolean isPlayer) {
          //TODO:
          if (isPlayer) {
               double damage = player.getDamage();
               double critCalc = random.nextDouble();
               if (critCalc < player.getCritChance()) {
                    damage += damage;
                    QueueProvider.offer("Crit hit! Your damage has been doubled!");
               }
               int healthReduction = (int) ((((2 * player.getLevel() / 70 + 0.25) * damage * damage / player.getArmour() / 100) + 2));
               opponent.setHealth(opponent.getHealth() - healthReduction);
               if (opponent.getHealth() < 0) {
                    opponent.setHealth(0);
               }
               QueueProvider.offer(healthReduction + " damage dealt!");
               QueueProvider.offer(opponent.monsterType + "' health is " + opponent.getHealth());
          }
     }

     private void defend(boolean isPlayer) {
          //TODO:
     }

     private void use() {
          new UseMenu(player.getStorage().getItems(), player);
     }
 }

