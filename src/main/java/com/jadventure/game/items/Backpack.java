package com.jadventure.game.items;

import java.util.ArrayList;

/**
 * Represents a players backpack.
 */
public class Backpack extends Storage {
	public Backpack(double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public Backpack(double maxWeight, ArrayList<ItemStack> initialItems) {
		this.maxWeight = maxWeight;
		this.items = initialItems;
	}

	/**
	 * Checks if the current Storage contains an ItemStack with the
	 * same type of item as the argument.
	 */
	private boolean contains(ItemStack item) {
		for(ItemStack i : this.items) {
			if(i.getItem().equals(item.getItem())) {
				return true;
			}
		}
		return false;
	}

	private ItemStack getSameType(ItemStack item) {
		for(ItemStack i : this.items) {
			if(i.getItem().equals(item.getItem())) {
				return i;
			}
		}
	}

	/**
	 * Adds an ItemStack to the items list.
	 */
	public void addItem(ItemStack item) {
		double totalWeight = item.getItem().getWeight() * item.getAmount();
		if(totalWeight < this.maxWeight) {
			if(this.contains(item)) {
				ItemStack sameType = this.getSameType(item);
				this.items.remove(sameType);
				this.items.add(new ItemStack(sameType.getAmount()+1, sameType.getItem()));
			} else {
				this.items.add(item);
			}
		}
	}

	/**
	 * Removes one Item from the ItemStack and replaces the old ItemStack
	 * in this.items with the new one.
	 */
	public void removeItem(ItemStack item) {
		this.items.remove(item);
		this.items.add(new ItemStack(item.getAmount()-1, item.getItem()));
	}

	/**
	 * Removes amount of Item's from the ItemStack and replaces the old ItemStack
	 * in this.items with the new one.
	 */
	public void removeItem(ItemStack item, int amount) {
		this.items.remove(item);
		this.items.add(new ItemStack(item.getAmount()-amount, item.getItem()));
	}

	public String display() {
		System.out.println("\n--------------------------------------------------------------------");
        System.out.println("Backpack: ");
        System.out.println(this.toString());
        System.out.println("\n--------------------------------------------------------------------");
	}

	public boolean isEmpty() {
		return this.items.isEmpty();
	}

	public ArrayList<ItemStack> getItems() {
		return this.items;
	}

	public String toString() {
		if (this.items.isEmpty()) {
            return "--Empty--";
        } else {
        	String content = "";
            for (ItemStack item : this.items) {
                content += "- " + item.getItem().getName() + " : " + item.getAmount();
            }
            return content;
        }
	}
}