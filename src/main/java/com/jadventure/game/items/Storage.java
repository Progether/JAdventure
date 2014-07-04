package com.jadventure.game.items;

import java.util.ArrayList;
import java.util.List;

import com.jadventure.game.IGameElement;
import com.jadventure.game.IGameElementVisitor;

/**
 * Defines an interface for any type of storage in this game.
 */
public abstract class Storage implements IGameElement {
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

	@Override
	public void accept(IGameElementVisitor visitor) {
		visitor.visit(this);
	}


    public boolean contains(String arg) {
        List<ItemStack> items = getItems();
        for (ItemStack itemStack : items) {
            if (arg.equalsIgnoreCase(itemStack.getItem().getName())) {
                return true;
            }
        }
        return false;
    }

}