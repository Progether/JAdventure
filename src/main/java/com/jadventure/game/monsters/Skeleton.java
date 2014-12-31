package com.jadventure.game.monsters;

/*
 * A monster that is pretty easy to deal with.
 */
public class Skeleton extends Monster {
	public Skeleton(int playerLevel){
		this.monsterType = "Skeleton";
		this.setHealthMax(50 + (int) Math.pow(playerLevel, 3));
		this.setHealth(50 + (int) Math.pow(playerLevel, 3));
		this.setArmour(0);
		this.setDamage(8 + Math.pow(playerLevel, 1.5));
		this.setCritChance(0.02);
                this.setIntelligence(3);
                this.setStealth(1);
                this.setDexterity(3);        
                this.setXPGain(10 + playerLevel * 3);
		this.setGold(playerLevel * 3);
                addRandomItems(playerLevel, "arhl1");
	}
}
