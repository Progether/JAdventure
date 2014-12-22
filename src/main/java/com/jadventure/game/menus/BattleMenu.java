package com.jadventure.game.menus;

import com.jadventure.game.DeathException;
import com.jadventure.game.entities.Entity;
import com.jadventure.game.entities.Player;
import com.jadventure.game.entities.NPC;
import com.jadventure.game.monsters.Monster;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.CharacterChange;

import java.util.Random;

public class BattleMenu extends Menus {

    private Monster monsterOpponent;
    private NPC npcOpponent;
    private Player player;
    private Random random;
    private int armour;
    private double damage;

    public BattleMenu(NPC npcOpponent, Player player) throws DeathException {
        this.random = new Random();
        this.npcOpponent = npcOpponent;
        this.player = player;
        this.menuItems.add(new MenuItem("Attack", "Attack " + npcOpponent.getName() + "."));
        this.menuItems.add(new MenuItem("Defend", "Defend against " + npcOpponent.getName() + "'s attack."));
        this.menuItems.add(new MenuItem("Equip", "Equip an item"));
        this.menuItems.add(new MenuItem("Unequip", "Unequip an item"));
        this.menuItems.add(new MenuItem("View", "View details about your character"));
        this.armour = player.getArmour();
        this.damage = player.getDamage();
        while (opponent.getHealth() > 0 && player.getHealth() > 0) {
            QueueProvider.offer("\nWhat is your choice?");
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
        }  else if (npcOpponent.getHealth() == 0) {
            int xp = npcOpponent.getXPGain();
            this.player.setXP(this.player.getXP() + xp);
            int oldLevel = this.player.getLevel();
            int newLevel = (int) (0.075 * Math.sqrt(this.player.getXP()) + 1);
            this.player.setLevel(newLevel);
            //this.player.getLocation().removeMonster(npcOpponent);
            this.player.setGold(this.player.getGold() + npcOpponent.getGold());
            QueueProvider.offer("You killed a " + npcOpponent.getName() + "\nYou have gained " + xp + " XP and " + npcOpponent.getGold() + " gold");
            if (oldLevel < newLevel) {
                QueueProvider.offer("You've are now level " + newLevel + "!");
            }
            CharacterChange cc = new CharacterChange();
            cc.trigger(this.player, "kill", npcOpponent.getName());
        }
    }

    public BattleMenu(Monster monsterOpponent, Player player) throws DeathException {
        this.random = new Random();
        this.monsterOpponent = monsterOpponent;
        this.player = player;
        this.menuItems.add(new MenuItem("Attack", "Attack " + monsterOpponent.monsterType + "."));
        this.menuItems.add(new MenuItem("Defend", "Defend against " + monsterOpponent.monsterType + "'s attack."));
        this.menuItems.add(new MenuItem("Equip", "Equip an item"));
        this.menuItems.add(new MenuItem("Unequip", "Unequip an item"));
        this.menuItems.add(new MenuItem("View", "View details about your character"));
        this.armour = player.getArmour();
        this.damage = player.getDamage();
        while (monsterOpponent.getHealth() > 0 && player.getHealth() > 0) {
            QueueProvider.offer("\nWhat is your choice?");
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
        }  else if (monsterOpponent.getHealth() == 0) {
            int xp = monsterOpponent.getXPGain();
            this.player.setXP(this.player.getXP() + xp);
            int oldLevel = this.player.getLevel();
            int newLevel = (int) (0.075 * Math.sqrt(this.player.getXP()) + 1);
            this.player.setLevel(newLevel);
            this.player.getLocation().removeMonster(monsterOpponent);
            this.player.setGold(this.player.getGold() + monsterOpponent.getGold());
            QueueProvider.offer("You killed a " + monsterOpponent.monsterType + "\nYou have gained " + xp + " XP and " + monsterOpponent.getGold() + " gold");
            if (oldLevel < newLevel) {
                QueueProvider.offer("You've are now level " + newLevel + "!");
            }
            CharacterChange cc = new CharacterChange();
            cc.trigger(this.player, "kill", monsterOpponent.getName());
        }
    }

    private void testSelected(MenuItem m) {
        switch (m.getKey()) {
            case "attack": {
                mutateStats(1, 0.5);
                if (npcOpponent == null) {
                    attack(player, monsterOpponent);
                    attack(monsterOpponent, player);
                } else {
                    attack(player, npcOpponent);
                    attack(npcOpponent, player);
                }
                resetStats();
                break;
            }
            case "defend": {
                QueueProvider.offer("\nYou get ready to defend against the " + opponent.monsterType + ".");
                mutateStats(0.5, 1);
                if (npcOpponent == null) {
                    attack(player, monsterOpponent);
                    attack(monsterOpponent, player);
                } else {
                    attack(player, npcOpponent);
                    attack(npcOpponent, player);
                }
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
            QueueProvider.offer(defender.getName() + "' health is " + defender.getHealth());
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
        QueueProvider.offer("\nWhat is your command?");
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

