package com.jadventure.game.monsters;

import com.jadventure.game.IGameElement;
import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Entity;
import com.jadventure.game.repository.MonsterBuilder;

/**
 * This class just holds a type of monster that is 
 * further outlined in its respective file. For now it
 * just holds the monster's name.
 */
public class Monster extends Entity implements IGameElement {
	public String monsterType;


	public Monster(MonsterBuilder bldr) {
		super(bldr);
	}



	@Override
	public void accept(IGameElementVisitor visitor) {
		visitor.visit(this);
	}

}
