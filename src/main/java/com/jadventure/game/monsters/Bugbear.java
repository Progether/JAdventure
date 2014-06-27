package com.jadventure.game.monsters;

import com.jadventure.game.items.ItemStack;
import com.jadventure.game.repository.ItemRepository;

/**
 * A hybrid animal with the matching armour and high damage, but low health.
 */
public class Bugbear extends Monster {
    private ItemRepository itemRepo;

    public Bugbear(int playerLevel){
		this.monsterType = "Bugbear";
		this.setHealthMax(50 + playerLevel * 5);
		this.setHealth(50 + playerLevel * 5);
		this.setArmour(playerLevel);
		this.setDamage(10 + playerLevel * 2);
		this.setCritChance(2);
		this.setGold(playerLevel * 3);
	    this.getStorage().addItem(new ItemStack(1, itemRepo.getItem("fmil1")));
	}
}
