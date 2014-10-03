package com.jadventure.game.items;

import java.util.ArrayList;

/**
 * Defines an interface for any type of storage in this game.
 */
public abstract class Storage {
	ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	double maxWeight;

	public double getMaxWeight() {
		return this.maxWeight;
	}

	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public abstract void addItem(ItemStack item);
	public abstract void removeItem(ItemStack item);
	public abstract void removeItem(ItemStack item, int amount);
	public abstract void display();
	public abstract boolean isEmpty();
	public abstract ArrayList<ItemStack> getItems();
}
