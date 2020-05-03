package com.jadventure.game.monsters;

/* 
 * A large rat with lots of health, damage, crit, and dexterity, but low armor and intelligence.
 */
public class BossRat extends Monster {
	public BossRat(int playerLevel){
		this.monsterType = "Wolf";
		this.setHealthMax(200 + playerLevel * 3);
		this.setHealth(200 + playerLevel * 3);
		this.setArmour(0);
		this.setDamage(35 + playerLevel * 2.5);
		this.setCritChance(0.25);
                this.setIntelligence(1);
                this.setStealth(2);
                this.setDexterity(4);
                this.setXPGain(60 + playerLevel * 3);
		this.setGold(20 + playerLevel * 2);
                addRandomItems(playerLevel, "fram1");
	}
}
