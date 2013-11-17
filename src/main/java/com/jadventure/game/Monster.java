package com.jadventure.game;

public abstract class Monster extends Entity {
	public String monsterType;
}

class Bugbear extends Monster {
	public Bugbear(int playerLevel){
		this.monsterType = "Bugbear";
	}
}

class Goblin extends Monster {
	public Goblin(int playerLevel){
		this.monsterType = "Goblin";
	}
}

class Troll extends Monster {
	public Troll(int playerLevel){
		this.monsterType = "Troll";
	}
}

class Skeleton extends Monster {
	public Skeleton(int playerLevel){
		this.monsterType = "Skeleton";
	}
}

class Wolf extends Monster {
	public Wolf(int playerLevel){
		this.monsterType = "Wolf";
	}
}

class Giant extends Monster {
	public Giant(int playerLevel){
		this.monsterType = "Giant";
	}
}