<<<<<<< HEAD
<<<<<<< HEAD
import java.util.Random;
=======
>>>>>>> 82079993406d08dff905e86034d70e0f98f3e0cf
=======
import java.util.Random;
>>>>>>> origin/Applzor

public class Player extends Entity{

	public Player(){
<<<<<<< HEAD
		//super(name);
<<<<<<< HEAD
=======
>>>>>>> origin/Applzor
		// set maxHealth
		Random randGen = new Random();
		
		int healthMax = 20, defence = 0, rand;
		double damage = 0;
		for (int i = 0; i < 120; i++) {
			rand = randGen.nextInt(3);

			if (rand == 0) {
				healthMax++;
			} else if (rand == 1) {
				defence++;
			} else {
				double damageIncrement = (randGen.nextDouble() * 2.2);
				damage += damageIncrement;
			}
		}
		//	round damage
		damage = Math.round(damage * 4);
		damage /= 4; //	Wasn't working above... (always x.0)
		
		setHealthMax(healthMax);
<<<<<<< HEAD
		setDefence(defence);
=======
		setArmour(defence);
>>>>>>> origin/Applzor
		setDamage(damage);
		setLevel(1);
	}
}
=======

	}
}
>>>>>>> 82079993406d08dff905e86034d70e0f98f3e0cf
