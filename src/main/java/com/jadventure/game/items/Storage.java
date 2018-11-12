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
	private List<ItemStack> itemStacks = null;

    public Storage() {
        this(WEIGHT_UNLIMITED);
    }
	public Storage(double maxWeight) {
        this(maxWeight, new ArrayList<ItemStack>());
	}
    public Storage(double maxWeight, List<ItemStack> items) {
        this.maxWeight = maxWeight;
        this.itemStacks = items;
    }

	public double getMaxWeight() {
		return maxWeight;
	}

    /**
     * Checks if the current Storage contains an ItemStack with the
     * same type of item as the argument.
     */
    private boolean contains(ItemStack item) {
        for (ItemStack itemStack : this.itemStacks) {
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
        for (ItemStack itemStack : this.itemStacks) {
            if (itemStack.getItem().equals(item.getItem())) {
                return itemStack;
            }
        }
        return null;
    }

    public void add(Item item) {
        addItem(new ItemStack(1, item));
    }

    /**
     * Adds an ItemStack to the items list.
     */
    public void addItem(ItemStack itemStack) {
        boolean canBeAdded = true;
        if (hasMaxWeight()) {
            double totalWeight = itemStack.getItem().getWeight() * itemStack.getAmount();
            if (totalWeight > maxWeight) {
                canBeAdded = false;
            }
        }
        if (canBeAdded) {
            if (contains(itemStack)) {
                ItemStack sameType = this.getSameType(itemStack);
                this.itemStacks.remove(sameType);
                this.itemStacks.add(new ItemStack(sameType.getAmount()+1, sameType.getItem()));
            } else {
                this.itemStacks.add(itemStack);
            }
        }
    }

    private boolean hasMaxWeight() {
        return !(maxWeight == -1);
    }

    /**
     * Removes one Item from the ItemStack and replaces the old ItemStack
     * in this.items with the new one.
     */
    public Item removeItem(ItemStack item) {
        return removeItem(item, 1);
    }
    public Item remove(Item item) {
        return removeItem(new ItemStack(0, item), 1);
    }

    /**
     * Removes amount of Items from the ItemStack and replaces the old ItemStack
     * in this.items with the new one.
     */
    public Item removeItem(ItemStack itemStack, int amount) {
        if (contains(itemStack)) {
            ItemStack sameType = getSameType(itemStack);
            if (sameType.getAmount() - amount <= 0) {
                itemStacks.remove(sameType);
            } else {
                itemStacks.remove(sameType);
                itemStacks.add(new ItemStack(sameType.getAmount() - amount, sameType.getItem()));
            }
            return itemStack.getItem();
        }
        return null;
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
        return this.itemStacks.isEmpty();
    }

    public List<Item> search(String name) {
        List<Item> items = new ArrayList<>();
        for (ItemStack itemStack : itemStacks) {
            if (itemStack.getItem().getName().equalsIgnoreCase(name)) {
                items.add(itemStack.getItem());
            }
        }
        return items;
    }
    
    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        for (ItemStack itemStack : itemStacks) {
            items.add(itemStack.getItem());
        }
        return items;
    }

    public String toString() {
        if (this.itemStacks.isEmpty()) {
            return "--Empty--";
        } else {
            String content = "";
            for (ItemStack item : this.itemStacks) {
                content += "- " + item.getItem().getName() + " : " + item.getAmount() + "\n";
            }
            return content;
        }
    }
    public List<ItemStack> getItemStack() {
        return itemStacks;
    }
    public Integer calculateWeight() {
        int weight = 0;
        for (ItemStack itemStask : itemStacks) {
            weight += itemStask.getAmount() * itemStask.getItem().getProperty("weight");
        }
        return Integer.valueOf(weight);
    }

    public String displayWithValue(int playerLuck, int playerInt) {
        if (itemStacks.isEmpty()) {
            return "--Empty--";
        } else {
            String content = "";
            if(playerLuck > 0){
                for (ItemStack itemStack : itemStacks) {
                    int value = (int)((0.5+0.02*(playerInt+playerLuck))*(itemStack.getItem().getProperties().get("value")));
                    content += "- " + itemStack.getItem().getName() + " : " + itemStack.getAmount() + " at " + value + " gold coins each\n";
                }
            } else {
                for (ItemStack itemStack : itemStacks) {
                    int value = itemStack.getItem().getProperties().get("value");
                    content += "- " + itemStack.getItem().getName() + " : " + itemStack.getAmount() + " at " + value + " gold coins each\n";
                }
            }
            return content;
        }
    }

}
