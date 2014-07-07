package com.jadventure.game.command;

import java.util.List;

import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Player;
import com.jadventure.game.monsters.Monster;

public final class MonsterCommand extends AbstractCommand {

    
    public MonsterCommand() {
        super("monster", "m", "Shows the monsters around you.");
    }


    @Override
    public void execute(Player player, IGameElementVisitor visitor, String[] args) {
        List<Monster> monsterList = player.getLocation().getMonsters();
        if (monsterList.size() > 0) {
        	visitor.append("Monsters around you:");
            for (Monster monster : monsterList) {
                visitor.visit(monster);
            }
        } else {
        	visitor.append("There are no monsters around you.");
        }
    }

}
