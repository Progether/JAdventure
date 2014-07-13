package com.jadventure.game.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jadventure.game.IGameElement;
import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.QueueProvider;

/**
 * Defines an interface for any type of storage in this game.
 */
public class Storage implements IGameElement {
	private Map<String, List<Item>> items = new HashMap<>();
	private double maxWeight;

	
	public Storage() {
	    this(0);
	}
	public Storage(double maxWeight) {
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
//    public void addItem(ItemStack item) {
//        double totalWeight = item.getItem().getWeight() * item.getAmount();
//        if (totalWeight < maxWeight) {
//            if(this.contains(item)) {
//                ItemStack sameType = this.getSameType(item);
//                itemsOld.remove(sameType);
//                itemsOld.add(new ItemStack(sameType.getAmount() + 1, sameType.getItem()));
//            }
//            else {
//                itemsOld.add(item);
//            }
//        }
//    }

    /**
     * Removes one Item from the ItemStack and replaces the old ItemStack
     * in this.items with the new one.
     */
//    public void removeItem(ItemStack item) {
//        this.removeItem(item, 1);
//    }

    /**
     * Removes amount of Item's from the ItemStack and replaces the old ItemStack
     * in this.items with the new one.
     */
//    public void removeItem(ItemStack item, int amount) {
//        if (this.contains(item)) {
//            ItemStack sameType = this.getSameType(item);
//            if (sameType.getAmount() - amount <= 0) {
//                itemsOld.remove(sameType);
//            }
//            else {
//                itemsOld.remove(sameType);
//                itemsOld.add(new ItemStack(sameType.getAmount() - amount, sameType.getItem()));
//            }
//        }
//    }

    /**
     * Prints out the content of the storage to the console.
     */
    public void display() {
            QueueProvider.offer("\n--------------------------------------------------------------------");
            QueueProvider.offer("Backpack: ");
            QueueProvider.offer(this.toString());
            QueueProvider.offer("--------------------------------------------------------------------");
    }

//    public boolean isEmpty() {
//        return this.itemsOld.isEmpty();
//    }

    public Map<String, List<Item>> getItems() {
        return Collections.unmodifiableMap(items);
    }

	@Override
	public void accept(IGameElementVisitor visitor) {
		visitor.visit(this);
	}

	/** Returns the Item, without removing it from the Storage. */
    public Item getItem(String itemName) {
    	if (! contains(itemName)) {
    		return null;
    	}
    	
    	return items.get(itemName).get(0);
//        List<ItemStack> items = getItems();
//        for (ItemStack itemStack : items) {
//            if (itemName.equalsIgnoreCase(itemStack.getItem().getName())) {
//                return itemStack.getItem();
//            }
//        }
//        return null;
    }

    public boolean contains(String itemName) {
    	if (! items.containsKey(itemName)) {
    		return false;
    	}
    	
    	return items.get(itemName).size() > 0;
//        List<ItemStack> items = getItems();
//        for (ItemStack itemStack : items) {
//            if (itemName.equalsIgnoreCase(itemStack.getItem().getName())) {
//                return true;
//            }
//        }
//        return false;
    }

//    public String toString() {
//        if (this.itemsOld.isEmpty()) {
//            return "--Empty--";
//        }
//        else {
//            String content = "";
//            for (ItemStack item : this.itemsOld) {
//                content += "- " + item.getItem().getName() + " : "
//                        + item.getAmount() + "\n";
//            }
//            return content;
//        }
//    }

    /**
     * Checks if the current Storage contains an ItemStack with the
     * same type of item as the argument.
     */
//    private boolean contains(ItemStack item) {
//        for (ItemStack i : this.itemsOld) {
//            if (i.getItem().equals(item.getItem())) {
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * Finds the only item of the same type as the input,
     * and returns that ItemStack.
     * This prevents duplicate items in your backpack.
     */
//    private ItemStack getSameType(ItemStack item) {
//        for (ItemStack i : this.itemsOld) {
//            if (i.getItem().equals(item.getItem())) {
//                return i;
//            }
//        }
//        return null;
//    }
	public void add(Item item) {
		if (item == null) {
			return;
		}
		if (! items.containsKey(item.getName())) {
			items.put(item.getName(), new ArrayList<Item>());
		}
		items.get(item.getName()).add(item);
		
	}
//	public Item remove(Item item) {
//		if (! items.containsKey(item.getId())) {
//			return null;
//		}
//		if (items.get(item).size() == 0) {
//			return null;
//		}
//		items.get(item.getId()).remove(item);
//		return item;
//	}
	public Item remove(String itemName) {
		if (! items.containsKey(itemName)) {
			return null;
		}
		if (items.get(itemName).isEmpty()) {
			return null;
		}
		return items.get(itemName).remove(0);
	}
	
	public Integer calculateWeight() {
		int weight = 0;
		for (List<Item> list : items.values()) {
			for (Item item : list) {
				weight += item.getWeight();
			}
		}
		return Integer.valueOf(weight);
	}

	public boolean isEmpty() {
		for (List<Item> itemList : items.values()) {
			if (itemList.size() > 0) {
				return false;
			}
		}
		return true;
	}

}
