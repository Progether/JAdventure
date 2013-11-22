package com.jadventure.game.monsters;

public class Giant extends Monster {
	public Giant(int playerLevel){
		this.monsterType = "Giant";
		this.setHealthMax(100 + playerLevel * 8);
		this.setHealth(100 + playerLevel * 8);
		this.setArmour(6 + playerLevel * 3);
		this.setDamage(17 + playerLevel * 3);
		this.setCritChance(3);
		this.setGold(15 + playerLevel * 11);
	}
}
