import java.util.Random;
import java.util.ArrayList;

// superclass for all entities (includes player, monsters...)

public class Entity {
	// All entities can attack, have health, have names...?
	public int healthMax;
	public int health;
	public String name;
	//levelMult is used to add a multiplier to the attack damage
	public int level;
	public int gold;
	public double damage = 30;
	public int critChance = 0;
	//Every point in armour reduces an attackers attack by .33
	public int armour;
	public ArrayList<Item> backpack;
	Random globalRand = new Random();

	// maybe not all entities start at full health, etc.
	public Entity(){
		this.healthMax = 100;
		this.health = this.healthMax;
		this.name = "default";
		this.gold = 0;
		this.backpack = new ArrayList<Item>();

	}

	//Since all entities can have a level, we need to make a multiplier
	//to increase or decrease damage to monsters based on level and vice versa
	public double levelMult(int attackL, int defenderL){
		double m = 1;
		if (attackL > defenderL){
			m = (attackL - defenderL) * 1.25;
			//Need to modify this to get better values defenders that are 1 level ahead
		} else if (attackL < defenderL) {
			m = (defenderL - attackL) * 0.2;
			return m;
		} else if (attackL == defenderL){
			return m;
		}
		return m;
	}
	//Will implement critical striking that will double attack
	public boolean criticalStrike(){
		
		
		return true;
	}
	
	//Basic attack method this can be used by both entities
	//Modifes damage based on entity.level and defending entity.armor
	public double basicAttack(Entity defender){
		double damageDone = 0;
		//Calculate damage multiplier and reduce attack by armor
		damageDone = ((this.damage * levelMult(this.level, defender.level)) - (defender.armour * .333));
		
		//Start Debug
		System.out.println("Player attack damage: " + this.damage);
		System.out.println("Player attack multiplier: " + levelMult(this.level, defender.level));
		System.out.println("Defender armour: " + defender.armour);
		System.out.println("Defender armour redcued damage by: " + (defender.armour * .25));
		System.out.println("Player modified attack damage: " + damageDone);
		//End Debug
		return damageDone;
	}
	


	public ArrayList<Item> die(){
		System.out.println(this.name + " has died. Oh look, he dropped:" );
		for (Item item : this.backpack){
			System.out.println(item.getName());
		}
		return this.backpack;
	}

}
