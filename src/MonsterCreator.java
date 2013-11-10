import java.util.ArrayList;
import java.util.Random;

public class MonsterCreator {

	Random random = new Random();

	public void Generate(ArrayList<Monster> monsterList) {
			Monster monster = new Monster();

			monster.setHealthMax(GenerateHealth());
			monster.setHealthCurrent(monster.getHealthMax());

			monster.setArmour(GenerateArmour());
			monster.setDamage(GenerateDamage());
			monster.setName(GenerateName());

			monsterList.add(monster);		
	}

	private int GenerateArmour(){
		return random.nextInt(10);
	}

	private int GenerateHealth(){
        return random.nextInt(100);
	}

	private double GenerateDamage(){
		return random.nextInt(10);
	}

	private String GenerateName(){
		String name = "unassigned";
		int temp = random.nextInt(15);

		if (temp <= 5){
            name = "Bugbear";
        }
        else if (temp > 5 && temp <= 10){
            name = "Goblin";
        }
        else if (temp > 10){
            name = "Troll";
        }

		return name;
	}

}
