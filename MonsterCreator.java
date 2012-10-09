import java.util.ArrayList;
import java.util.Random;

public class MonsterCreator {
	
	Random random = new Random();
	
	public void Generate(Player player, ArrayList<Monster> monsterList) {
			Monster monster = new Monster();
			int level = player.getLevel();
			
			monster.setHealthMax((int)GenerateHealth(level));
			monster.setHealthCurrent(monster.getHealthMax());
			monster.setDamage(GenerateDamage(level));
			monster.setName(GenerateName(level));
			
			monsterList.add(monster);		
	}
	
	private float GenerateHealth(int level){
		float health = 100;
		random = new Random();
		
		//health = (level * 10) + random.nextInt(level * 25);
		health = random.nextInt(100);
		
		return health;
	}
	
	private float GenerateDamage(int level){
		float damage = 1;
		random = new Random();
		
		//damage = level + random.nextInt(level * 5);
		damage = random.nextInt(10);
		
		return damage;
	}
	
	private String GenerateName(int level){
		String name = "unassigned";
		level = random.nextInt(15);
		
		if (level <= 5){
            name = "Bugbear";
        }
        else if (level > 5 && level <= 10){
            name = "Goblin";
        }
        else if (level > 10){
            name = "Troll";
        }
		
		return name;
	}

}
