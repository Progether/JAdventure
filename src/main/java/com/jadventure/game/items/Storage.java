package com.jadventure.game.items;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines an interface for any type of storage in this game.
 */
public abstract class Storage {
	protected List<ItemStack> items = new ArrayList<ItemStack>();
	protected double maxWeight;

	
	public Storage(List<ItemStack> items, double maxWeight) {
		if (items != null) {
			this.items = items;
		}
		this.maxWeight = maxWeight;
	}


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
	public abstract List<ItemStack> getItems();

}