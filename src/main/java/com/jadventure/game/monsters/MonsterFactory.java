package com.jadventure.game.monsters;

import com.jadventure.game.entities.Player;

import java.util.Random;

/**
 * The MonsterFactory generates random monsters appropriately according
 * to the player's level and location
 */
public class MonsterFactory {
    Random random = new Random();
 
    public Monster generateMonster(Player player) {
        int randomInt = random.nextInt(5) + 1;
        if (randomInt <= player.getLocation().getDangerRating()) {
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
        } else {
            return null;
        }
    }

    private Monster getForestMonster(int playerLevel) {
        int randomInt = random.nextInt(4);
        if (randomInt == 0)
        	return new Bugbear(playerLevel);
        else if (randomInt == 1)
        	return new Troll(playerLevel);
        else
        	return new Goblin(playerLevel);
    }
    
    private Monster getSwampMonster(int playerLevel) {
    	int randomInt = random.nextInt(2);
        return (randomInt == 1) ? new Goblin(playerLevel) : new Troll(playerLevel);
    }
    
    private Monster getMountainMonster(int playerLevel) {
    	int randomInt = random.nextInt(4);
        if (randomInt == 0)
            return new Giant(playerLevel);
        else if (randomInt == 1)
            return new Troll(playerLevel);
        else if (randomInt == 2)
            return new Wolf(playerLevel);
        else
            return new Skeleton(playerLevel);
    }

    private Monster getCaveMonster(int playerLevel) {
    	int randomInt = random.nextInt(4);
        if (randomInt == 0)
            return new Troll(playerLevel);
        else if (randomInt == 1)
            return new Skeleton(playerLevel);
        else
            return new Goblin(playerLevel);
    }

    private Monster getPlainsMonster(int playerLevel) {
    	int randomInt = random.nextInt(2);
    	return (randomInt == 1) ? new Bugbear(playerLevel) : new Goblin(playerLevel);
    }
}
