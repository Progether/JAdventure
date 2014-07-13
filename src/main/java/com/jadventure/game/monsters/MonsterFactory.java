package com.jadventure.game.monsters;

import com.jadventure.game.GameBeans;
import com.jadventure.game.entities.Player;
import com.jadventure.game.repository.MonsterRepository;

import java.util.Random;

/**
 * the MonsterFactory generates random monsters appropriately according
 * to the players level and location
 */
public class MonsterFactory {
	private MonsterRepository monsterRepo = GameBeans.getMonsterRepository();
	
    Random random = new Random();
    public Monster generateMonster(Player player) {
        switch (player.getLocationType()) {
            case FOREST:
                return getForestMonster(player.getLevel());
            case SWAMP:
                return getSwampMonster(player.getLevel());
            case MOUNTAIN:
                return getMountainMonster(player.getLevel());   
            case CAVE:
                return getCaveMonster(player.getLevel());
            case PLAINS:
                return getPlainsMonster(player.getLevel());
            default: // any non-hostile location
                return null;
        }
    }

    private Monster getForestMonster(int playerLevel) {
        int randomInt = random.nextInt(4);
        if (randomInt == 0)
        	return createBugbear(playerLevel);
        else if (randomInt == 1)
        	return createTroll(playerLevel);
        else
        	return createGoblin(playerLevel);
    }
    

	private Monster getSwampMonster(int playerLevel) {
    	int randomInt = random.nextInt(2);
        return (randomInt == 1) ? createGoblin(playerLevel) : createTroll(playerLevel);
    }
    
    private Monster getMountainMonster(int playerLevel) {
    	int randomInt = random.nextInt(4);
        if (randomInt == 0)
            return createGiant(playerLevel);
        else if (randomInt == 1)
            return createTroll(playerLevel);
        else if (randomInt == 2)
            return createWolf(playerLevel);
        else
            return createSkeleton(playerLevel);
    }

	private Monster getCaveMonster(int playerLevel) {
    	int randomInt = random.nextInt(3);
        if (randomInt == 0)
            return createTroll(playerLevel);
        else if (randomInt == 1)
            return createSkeleton(playerLevel);
        else
            return createGoblin(playerLevel);
    }

	private Monster getPlainsMonster(int playerLevel) {
    	int randomInt = random.nextInt(2);
    	return (randomInt == 1) ? createBugbear(playerLevel) : createGoblin(playerLevel);
    }


    private Monster createGoblin(int playerLevel) {
		return monsterRepo.getMonster("goblin");
	}

	private Monster createTroll(int playerLevel) {
		return monsterRepo.getMonster("troll");
	}

    private Monster createSkeleton(int playerLevel) {
		return monsterRepo.getMonster("skeleton");
	}

	private Monster createBugbear(int playerLevel) {
		return monsterRepo.getMonster("bugbear");
	}

    private Monster createGiant(int playerLevel) {
		return monsterRepo.getMonster("gaint");
	}

	private Monster createWolf(int playerLevel) {
		return monsterRepo.getMonster("wolf");
	}
}
