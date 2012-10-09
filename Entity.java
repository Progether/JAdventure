import java.util.ArrayList;


public class Entity {
	// All entities can attack, have health, have names...?
	private int healthMax;
	private int healthCurrent;
	private String name;
	private int level;
	private int gold;
	private float damage;
	private ArrayList<Item> backpack;

	// maybe not all entities start at full health, etc.
	public Entity(){
		this.healthMax = 100;
		this.name = "default";
		this.gold = 0;
		this.backpack = new ArrayList<Item>();

	}
	
	// get and set functions to change entity's health, damage, name, gold...etc
	public int getHealthMax(){
		return this.healthMax;
	}
	public void setHealthMax(int newHealth){
		healthMax = newHealth;
	}
	
	public int getHealthCurrent(){
		return this.healthCurrent;
	}
	public void setHealthCurrent(int newHealth){
		healthCurrent = newHealth;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName(String newName){
		name = newName;
	}
	
	public int getLevel(){
		return this.level;
	}
	public void setLevel(int newLevel){
		level = newLevel;
	}
	
	public int getGold(){
		return this.gold;
	}
	public void setGold(int newGold){
		gold = newGold;
	}
	
	public float getDamage(){
		return this.damage;
	}
	public void setDamage(float newDamage){
		damage = newDamage;
	}
	
	public ArrayList<Item> getBackpack(){			// may be useless.. shall see
		return this.backpack;
	}
	public void addBackpack(Item item){				// add an item to the backpack
		backpack.add(item);
	}
	public void removeBackpack(Item item){			// remove an item from the backpack
		backpack.remove(item);
	}
	
	public ArrayList<Item> die(){
		System.out.println(this.name + " has died. Oh look, he dropped:" );
		for (Item item : this.backpack){
			System.out.println(item.getName());
		}
		return this.backpack;
	}
	
}
