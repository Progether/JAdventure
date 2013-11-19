package com.jadventure.game.monsters;

import com.jadventure.game.Player;

import java.util.Random;
/**
 * @author Michael North
 * November 16, 2013
 * the MonsterFactory generates random monsters appropriately according
 * to the players level and location
 */
public class MonsterFactory {
    
    public Monster generateMonster() {
        Player player = Player.getInstance();
        Random random = new Random();
        int randomInt;
        switch (player.getLocationType()) {
            case "forest":
                randomInt = random.nextInt(3);
                if (randomInt == 0)
                    return new Troll(player.getLevel());
                else if (randomInt == 1)
                    return new Goblin(player.getLevel());
                else
                    return new Bugbear(player.getLevel());
            case "swamp":
                randomInt = random.nextInt(2);
                return (randomInt == 1) ? new Goblin(player.getLevel()) : new Troll(player.getLevel());
                
            case "mountain":
                randomInt = random.nextInt(4);
                if (randomInt == 0)
                    return new Giant(player.getLevel());
                else if (randomInt == 1)
                    return new Troll(player.getLevel());
                else if (randomInt == 2)
                    return new Wolf(player.getLevel());
                else
                    return new Skeleton(player.getLevel());
                
            case "cave":
                randomInt = random.nextInt(3);
                if (randomInt == 0)
                    return new Troll(player.getLevel());
                else if (randomInt == 1)
                    return new Skeleton(player.getLevel());
                else
                    return new Goblin(player.getLevel());
                
            case "plains":
                randomInt = random.nextInt(2);
                return (randomInt == 1) ? new Bugbear(player.getLevel()) : new Goblin(player.getLevel()) ;
                
            default: // any non-hostile location
                return null;
        }
    }
    
}
