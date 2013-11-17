package com.jadventure.game;

import java.util.Random;
/**
 * @author Michael North
 * November 16, 2013
 * the MonsterFactory generates random monsters appropriately according
 * to the players level and location
 */
public class MonsterFactory {
    
    public Monster generateMonster(Player player) {
        Random random = new Random();
        int randomInt;
        switch (player.getLocationType()) {
            case "forest":
                randomInt = random.nextInt(3);
                if (randomInt == 0)
                    return new Troll(player.level);
                else if (randomInt == 1)
                    return new Goblin(player.level);
                else
                    return new Bugbear(player.level);
            case "swamp":
                randomInt = random.nextInt(2);
                return (randomInt == 1) ? new Goblin(player.level) : new Troll(player.level);
                
            case "mountain":
                randomInt = random.nextInt(4);
                if (randomInt == 0)
                    return new Giant(player.level);
                else if (randomInt == 1)
                    return new Troll(player.level);
                else if (randomInt == 2)
                    return new Wolf(player.level);
                else
                    return new Skeleton(player.level);
                
            case "cave":
                randomInt = random.nextInt(3);
                if (randomInt == 0)
                    return new Troll(player.level);
                else if (randomInt == 1)
                    return new Skeleton(player.level);
                else
                    return new Goblin(player.level);
                
            case "plains":
                randomInt = random.nextInt(2);
                return (randomInt == 1) ? new Bugbear(player.level) : new Goblin(player.level) ;
                
            default: // any non-hostile location
                return null;
        }
    }
    
}
