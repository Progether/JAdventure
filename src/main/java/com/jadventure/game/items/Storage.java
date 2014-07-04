package com.jadventure.game.items;

import java.util.ArrayList;
import java.util.List;

import com.jadventure.game.IGameElement;
import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.QueueProvider;

/**
 * Defines an interface for any type of storage in this game.
 */
public class Storage implements IGameElement {
	protected List<ItemStack> items = new ArrayList<ItemStack>();
	protected double maxWeight;

	
	public Storage() {
	    this(null, 0);
	}
	public Storage(List<ItemStack> items) {
	    this(items, 0);
	}
	public Storage(List<ItemStack> items, double maxWeight) {
		if (items != null) {
			this.items.addAll(items);
		}
		this.maxWeight = maxWeight;
	}


	public double getMaxWeight() {
		return this.maxWeight;
	}

	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}

    /**
     * Adds an ItemStack to the items list.
     */
    public void addItem(ItemStack item) {
        double totalWeight = item.getItem().getWeight() * item.getAmount();
        if (totalWeight < maxWeight) {
            if(this.contains(item)) {
                ItemStack sameType = this.getSameType(item);
                items.remove(sameType);
                items.add(new ItemStack(sameType.getAmount() + 1, sameType.getItem()));
            }
            else {
                items.add(item);
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
            if (sameType.getAmount() - amount <= 0) {
                items.remove(sameType);
            }
            else {
                items.remove(sameType);
                items.add(new ItemStack(sameType.getAmount() - amount, sameType.getItem()));
            }
        }
    }

    /**
     * Prints out the content of the storage to the console.
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
        return items;
    }

	@Override
	public void accept(IGameElementVisitor visitor) {
		visitor.visit(this);
	}

    public Item getItem(String itemName) {
        List<ItemStack> items = getItems();
        for (ItemStack itemStack : items) {
            if (itemName.equalsIgnoreCase(itemStack.getItem().getName())) {
                return itemStack.getItem();
            }
        }
        return null;
    }

    public boolean contains(String itemName) {
        List<ItemStack> items = getItems();
        for (ItemStack itemStack : items) {
            if (itemName.equalsIgnoreCase(itemStack.getItem().getName())) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        if (this.items.isEmpty()) {
            return "--Empty--";
        }
        else {
            String content = "";
            for (ItemStack item : this.items) {
                content += "- " + item.getItem().getName() + " : "
                        + item.getAmount() + "\n";
            }
            return content;
        }
    }

    /**
     * Checks if the current Storage contains an ItemStack with the
     * same type of item as the argument.
     */
    private boolean contains(ItemStack item) {
        for (ItemStack i : this.items) {
            if (i.getItem().equals(item.getItem())) {
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
        for (ItemStack i : this.items) {
            if (i.getItem().equals(item.getItem())) {
                return i;
            }
        }
        return null;
    }

}
