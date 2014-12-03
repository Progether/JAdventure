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
     private int armour;
     private double damage;

     public BattleMenu(Monster opponent, Player player) throws DeathException {
        this.random = new Random();
        this.opponent = opponent;
        this.player = player;
        this.menuItems.add(new MenuItem("Attack", "Attack " + opponent.monsterType + "."));
        this.menuItems.add(new MenuItem("Defend", "Defend against " + opponent.monsterType + "'s attack."));
        this.menuItems.add(new MenuItem("Equip", "Equip an item"));
        this.menuItems.add(new MenuItem("Unequip", "Unequip an item"));
        this.menuItems.add(new MenuItem("View", "View details about your character"));
        this.armour = player.getArmour();
        this.damage = player.getDamage();
        while (opponent.getHealth() > 0 && player.getHealth() > 0) {
            QueueProvider.offer("What is your choice?");
            MenuItem selectedItem = displayMenu(this.menuItems);
            testSelected(selectedItem);       
        } 
	    if (player.getHealth() == 0) {
		    QueueProvider.offer("You died... Start again? (y/n)");
		    String reply = QueueProvider.take().toLowerCase();
            while (!reply.startsWith("y") && !reply.startsWith("n")) {
		        QueueProvider.offer("You died... Start again? (y/n)");
		        reply = QueueProvider.take().toLowerCase();
            }
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
            this.player.setGold(this.player.getGold() + opponent.getGold());
		    QueueProvider.offer("You killed a " + opponent.monsterType + "\nYou have gained " + xp + " XP and " + opponent.getGold() + " gold");
            if (oldLevel < newLevel) {
                QueueProvider.offer("You've are now level " + newLevel + "!");
            }
        }
     }

     private void testSelected(MenuItem m) {
        String key = m.getKey();
        switch (m.getKey()) {
            case "attack": {
                mutateStats(1, 0.5);
                attack(player, opponent);
		        attack(opponent, player);
                resetStats();
                break;
            }
            case "defend": {
                QueueProvider.offer("You get ready to defend against the " + opponent.monsterType + ".");
                mutateStats(0.5, 1);
                attack(opponent, player);
                attack(player, opponent);
                resetStats();
                break;
            }
            case "equip": {
                equip();
                break;
            }
            case "unequip": {
                unequip();
                break;
            }
            case "view": {
                viewStats();
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

     private void mutateStats(double damageMult, double armourMult) {
        armour = player.getArmour();
        damage = player.getDamage();
        player.setArmour((int) (armour * armourMult));
        player.setDamage(damage * damageMult);
     }

     private void resetStats() {
         player.setArmour(armour);
         player.setDamage(damage);
     }

     private void equip() {
        player.printStorage();
		QueueProvider.offer("What item do you want to use?");
		String itemName = QueueProvider.take();
		if (!itemName.equalsIgnoreCase("back")) {
			player.equipItem(itemName);
		}
     }

     private void unequip() {
        player.printEquipment();
        QueueProvider.offer("What item do you want to unequip?");
        String itemName = QueueProvider.take();
        if (!itemName.equalsIgnoreCase("back")) {
            player.dequipItem(itemName);
        }
     }

     private void viewStats() {
        QueueProvider.offer("What is your command?");
        String input = QueueProvider.take();
        switch (input) {
            case "vs":
            case "viewstats":
                player.getStats();
                break;
            case "ve":
            case "viewequipped":
                player.printEquipment();
                break;
            case "vb":
            case "viewbackpack":
                player.printStorage();
                break;
            case "back":
            case "exit":
                break;
            default:
                viewStats();
                break;
        }
     }
 }

