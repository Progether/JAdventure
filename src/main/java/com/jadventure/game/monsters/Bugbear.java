package com.jadventure.game.monsters;

import com.jadventure.game.items.Item;

public class Bugbear extends Monster {
	public Bugbear(int playerLevel){
		this.monsterType = "Bugbear";
		this.setHealthMax(50 + playerLevel * 5);
		this.setHealth(50 + playerLevel * 5);
		this.setArmour(playerLevel);
		this.setDamage(10 + playerLevel * 2);
		this.setCritChance(2);
		this.setGold(playerLevel * 3);
	    this.getBackpack().add(new Item(1));
	}
}