import java.util.ArrayList;

<<<<<<< HEAD
// superclass for all entities (inludes player, monsters...)
=======

<<<<<<< HEAD
>>>>>>> 82079993406d08dff905e86034d70e0f98f3e0cf
=======
// superclass for all entities (includes player, monsters...)

>>>>>>> origin/Applzor
public class Entity {
	// All entities can attack, have health, have names...?
	private int healthMax;
	private int healthCurrent;
	private String name;
	private int level;
	private int gold;
<<<<<<< HEAD
<<<<<<< HEAD
	private double damage;
	private int defence;
=======
	private float damage;
>>>>>>> 82079993406d08dff905e86034d70e0f98f3e0cf
=======
	private double damage;
	private int defence;

>>>>>>> origin/Applzor
	private ArrayList<Item> backpack;

	// maybe not all entities start at full health, etc.
	public Entity(){
		this.healthMax = 100;
		this.healthCurrent = this.healthMax;
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
	
<<<<<<< HEAD
<<<<<<< HEAD
	public double getDamage(){
		return this.damage;
	}
	public void setDamage(double newDamage){
		damage = newDamage;
	}
	
	public int getDefence() {
		return this.defence;
	}
	public void setDefence(int newDefence){
		defence = newDefence;
	}
	
=======
	public float getDamage(){
=======

	public double getDamage(){
>>>>>>> origin/Applzor
		return this.damage;
	}
	public void setDamage(double newDamage){
		damage = newDamage;
	}
	
<<<<<<< HEAD
>>>>>>> 82079993406d08dff905e86034d70e0f98f3e0cf
=======
	public int getArmour() {
		return this.defence;
	}
	public void setArmour(int newArmour){
		defence = newArmour;
	}
	
	
>>>>>>> origin/Applzor
	public ArrayList<Item> getBackpack(){			// may be useless.. shall see
		return this.backpack;
	}
	public void addBackpack(Item item){				// add an item to the backpack
		backpack.add(item);
	}
	public void removeBackpack(Item item){			// remove an item from the backpack
		backpack.remove(item);
	}
	
	public void attack(Entity attacker, Entity defender){
		double a_Damage = attacker.getDamage();
		int d_Armour = defender.getArmour();
		int d_Health = defender.getHealthCurrent();
		int baseArmour = 100;
		double armourFactor;
		
		armourFactor = 0.12 * (baseArmour + d_Armour) / 100;
		d_Health = d_Health - (int)(a_Damage * (1 - armourFactor));		
		
		// set defender health to new health
		defender.setHealthCurrent(d_Health);
		
	}
	
	public ArrayList<Item> die(){
		System.out.println(this.name + " has died. Oh look, he dropped:" );
		for (Item item : this.backpack){
			System.out.println(item.getName());
		}
		return this.backpack;
	}
	
}
