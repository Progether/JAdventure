import java.util.List;
import java.util.Random;

public class MonsterCreator {
	
	Random random = new Random();
	
	public void Generate(Player player, List<Monster> monsterList) {
		for (int i = 0; i < 5; i++) {
			Monster monster = new Monster("monster");
			
			monster.setHealthMax((int)GenerateHealth(player.getLevel()));
			monster.setHealthCurrent(monster.getHealthMax());
			monster.setDamage(GenerateDamage(player.getLevel()));
			
			monsterList.add(monster);
			
		}
		
	}
	
	private float GenerateHealth(int level){
		float health = 100;
		
		health = (level * 10) + random.nextInt(level * 25);
		
		return health;
	}
	
	private float GenerateDamage(int level){
		float damage = 1;
		
		damage = level + random.nextInt(level * 5);
		
		return level;
	}

}
