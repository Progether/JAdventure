package com.jadventure.game.items;

import java.util.ArrayList;
import java.util.List;

import com.jadventure.game.QueueProvider;

/**
 * Defines an interface for any type of storage in this game.
 */
public class Storage {
    public final static double WEIGHT_UNLIMITED = -1;
    private double maxWeight;
	private List<ItemStack> items = null;

    public Storage() {
        this(WEIGHT_UNLIMITED);
    }
	public Storage(double maxWeight) {
        this(maxWeight, new ArrayList<ItemStack>());
	}
    public Storage(double maxWeight, List<ItemStack> items) {
        this.maxWeight = maxWeight;
        this.items = items;
    }

	public double getMaxWeight() {
		return maxWeight;
	}

    /**
     * Checks if the current Storage contains an ItemStack with the
     * same type of item as the argument.
     */
    private boolean contains(ItemStack item) {
        for (ItemStack itemStack : this.items) {
            if (itemStack.getItem().equals(item.getItem())) {
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
        for (ItemStack itemStack : this.items) {
            if (itemStack.getItem().equals(item.getItem())) {
                return itemStack;
            }
        }
        return null;
    }

    /**
     * Adds an ItemStack to the items list.
     */
    public void addItem(ItemStack itemStack) {
        double totalWeight = itemStack.getItem().getWeight() * itemStack.getAmount();
        if (totalWeight < this.maxWeight) {
            if (contains(itemStack)) {
                ItemStack sameType = this.getSameType(itemStack);
                this.items.remove(sameType);
                this.items.add(new ItemStack(sameType.getAmount()+1, sameType.getItem()));
            } else {
                this.items.add(itemStack);
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
        if (this.contains(item)) {
            ItemStack sameType = this.getSameType(item);
            if (sameType.getAmount()-amount <= 0) {
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

    public List<ItemStack> getItems() {
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
