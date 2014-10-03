 package com.jadventure.game.menus;

 import com.jadventure.game.DeathException;
 import com.jadventure.game.entities.Entity;
 import com.jadventure.game.entities.Player;
 import com.jadventure.game.monsters.Monster;
 import com.jadventure.game.Game;
 import com.jadventure.game.QueueProvider;
 
 import java.util.Random;

 public class BattleMenu extends Menus {

     private Monster opponent;
     private Player player;
     private Random random;

     public BattleMenu(Monster opponent, Player player) throws DeathException {
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
		    if (reply.startsWith("y")) {
                throw new DeathException("restart");
            } else if (reply.startsWith("n")) {
                throw new DeathException("close");
            }
        }  else if (opponent.getHealth() == 0) {
		    int xp = opponent.getXPGain();
            this.player.setXP(this.player.getXP() + xp);
            int oldLevel = this.player.getLevel();
            int newLevel = (int) (0.075 * Math.sqrt(this.player.getXP()) + 1);
            this.player.setLevel(newLevel);
            this.player.getLocation().removeMonster(opponent);
		    QueueProvider.offer("You killed a " + opponent.monsterType + "! You have gained " + xp + " XP");
            if (oldLevel < newLevel) {
                QueueProvider.offer("You've are now level " + newLevel + "!");
            }
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
                defend();
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
        if (attacker.getHealth() == 0) {
            return;
        }
        double damage = attacker.getDamage();
        double critCalc = random.nextDouble();
        if (critCalc < attacker.getCritChance()) {
            damage += damage;
            QueueProvider.offer("Crit hit! Damage has been doubled!");
        }
        int healthReduction = (int) ((((3 * attacker.getLevel() / 50 + 2) * damage * damage / (defender.getArmour() + 1)/ 100) + 2) * (random.nextDouble() + 1));
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
     }

     private void defend() {
        QueueProvider.offer("You get ready to defend against the " + opponent.monsterType + ".");
        int armour = player.getArmour();
        player.setArmour(armour * 2);
        attack(opponent, player);
        player.setArmour(armour);
     }

     private void use() {
        new UseMenu(player.getStorage().getItems(), player);
     }
 }

