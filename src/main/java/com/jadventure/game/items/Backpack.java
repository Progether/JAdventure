package com.jadventure.game.items;

import com.jadventure.game.QueueProvider;

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

	/**
	 * Finds the only item of the same type as the input,
	 * and returns that ItemStack.
	 * This prevents duplicate items in your backpack.
	 */
	private ItemStack getSameType(ItemStack item) {
		for(ItemStack i : this.items) {
			if(i.getItem().equals(item.getItem())) {
				return i;
			}
		}
		return null;
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
		this.removeItem(item, 1);
	}

	/**
	 * Removes amount of Item's from the ItemStack and replaces the old ItemStack
	 * in this.items with the new one.
	 */
	public void removeItem(ItemStack item, int amount) {
		if(this.contains(item)) {
			ItemStack sameType = this.getSameType(item);
			if(sameType.getAmount()-amount <= 0) {
				this.items.remove(sameType);
			} else {
				this.items.remove(sameType);
				this.items.add(new ItemStack(sameType.getAmount()-amount, sameType.getItem()));
			}
		}
	}

	/**
	 * Prints out the content of the backpack to the console.
	 */
	public void display() {
            QueueProvider.offer("\n--------------------------------------------------------------------");
            QueueProvider.offer("Backpack: ");
            QueueProvider.offer(this.toString());
            QueueProvider.offer("--------------------------------------------------------------------");
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
                content += "- " + item.getItem().getName() + " : " + item.getAmount() + "\n";
            }
            return content;
        }
	}
}
