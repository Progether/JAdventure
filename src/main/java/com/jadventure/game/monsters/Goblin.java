package com.jadventure.game.monsters;

/**
 * A monster that isn't too bad, but does come with slight armour.
 */
public class Goblin extends Monster {
	public Goblin(int playerLevel){
		this.monsterType = "Goblin";
		this.setHealthMax(55 + playerLevel * 6);
		this.setHealth(55 + playerLevel * 6);
		this.setArmour(playerLevel + 3);
		this.setDamage(12 + playerLevel * 2.5);
		this.setCritChance(2);
		this.setGold(playerLevel * 5);
	}
}
