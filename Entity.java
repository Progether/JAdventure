import java.util.ArrayList;


public class Entity {
	// All entities can attack, have health, have names...
	int health;
	String name;
	int gold;
	ArrayList<Item> backpack;

	// maybe not all entities start at full health, etc.
	public Entity(String name){
		this.health = 100;
		this.name = name;
		this.gold = 0;

	}
	public int getHealth(){
		return this.health;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getGold(){
		return this.gold;
	}
	
	public ArrayList<Item> die(){
		System.out.println(this.name + " has died. Oh look, he dropped:" );
		for (Item item : this.backpack){
			System.out.println(item.getName());
		}
		return this.backpack;
	}
	
}
