package src;
import java.util.Random;


public class Player extends Entity{

	public Player(){
		//super(name);
		// set maxHealth
		Random randGen = new Random();
		
		int healthMax = 20, armour = 0, rand;
		double damage = 0;
		for (int i = 0; i < 120; i++) {
			rand = randGen.nextInt(3);

			if (rand == 0) {
				healthMax++;
			} else if (rand == 1) {
				armour++;
			} else {
				double damageIncrement = (randGen.nextDouble() * 2.2);
				damage += damageIncrement;
			}
		}
		//	round damage
		damage = Math.round(damage * 4);
		damage /= 4; //	Wasn't working above... (always x.0)
		
		setHealthMax(healthMax);
		setArmour(armour);
		setDamage(damage);
		setLevel(1);
	}
}
