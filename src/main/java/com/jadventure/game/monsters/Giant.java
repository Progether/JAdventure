package com.jadventure.game.monsters;

/**
 * A monster with high health and damage, but low armour.
 */
public class Giant extends Monster {
	public Giant(int playerLevel){
		this.monsterType = "Giant";
		this.setHealthMax(150 + playerLevel * 8);
		this.setHealth(150 + playerLevel * 8);
		this.setArmour(6 + playerLevel * 3);
		this.setDamage(40 + playerLevel * 3);
		this.setCritChance(3);
		this.setGold(15 + playerLevel * 11);
	}
}
