package com.jadventure.game.items;

/**
 * This is just an Item with a certain amount of it.
 */
public class ItemStack {
	int amount;
	Item item;

	public ItemStack(int amount, Item item) {
		this.amount = amount;
		this.item = item;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Item getItem() {
		return this.item;
	}
}