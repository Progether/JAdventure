package com.jadventure.game.repository;

import com.jadventure.game.items.Item;
import com.jadventure.game.items.Storage;

public class EntityBuilder {
	private String id;
	private String type;
	private String name;
	private String description;
	private int health;
	private int healthMax;
	private double damage;
	private int armour;
	private Item weapon;
	private Storage storage = new Storage();

	public EntityBuilder() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealthMax() {
		return healthMax;
	}

	public void setHealthMax(int healthMax) {
		this.healthMax = healthMax;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public int getArmour() {
		return armour;
	}

	public void setArmour(int armour) {
		this.armour = armour;
	}

	public Item getWeapon() {
		return weapon;
	}

	public void setWeapon(Item weapon) {
		if (weapon == null) {
			return;
		}
		
		if (weapon.getType().equalsIgnoreCase("weapon")) {
			this.weapon = weapon;
		}
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

}