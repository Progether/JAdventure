package com.jadventure.game.monsters;

/**
 * A classic animal that is not too dangerous.
 */
public class Wolf extends Monster {
	public Wolf(int playerLevel){
		this.monsterType = "Wolf";
		this.setHealthMax(35 + playerLevel * 3);
		this.setHealth(35 + playerLevel * 3);
		this.setArmour(0);
		this.setDamage(15 + playerLevel * 2.5);
		this.setCritChance(4);
		this.setGold(playerLevel * 2);
	}
}
