package com.jadventure.game.monsters;

/*
 * A monster that isn't too bad, but does come with slight armour.
 */
public class Goblin extends Monster {
	public Goblin(int playerLevel){
		this.monsterType = "Goblin";
		this.setHealthMax(55 + playerLevel * 6);
		this.setHealth(55 + playerLevel * 6);
		this.setArmour(playerLevel + 3);
		this.setDamage(12 + playerLevel * 2.5);
                this.setIntelligence(1);
                this.setStealth(2);
                this.setDexterity(1);
		this.setCritChance(0.02);
                this.setXPGain(10 + playerLevel * 3);
		this.setGold(playerLevel * 5);
                addRandomItems(playerLevel, "wdag1", "agre1", "albt1", "algt1");
	}
}
