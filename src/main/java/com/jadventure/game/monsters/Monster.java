package com.jadventure.game.monsters;

import com.jadventure.game.IGameElement;
import com.jadventure.game.IGameElementVisitor;
import com.jadventure.game.entities.Entity;

/**
 * This class just holds a type of monster that is 
 * further outlined in its respective file. For now it
 * just holds the monster's name.
 */
public abstract class Monster extends Entity implements IGameElement {
	public String monsterType;


	@Override
	public void accept(IGameElementVisitor visitor) {
		visitor.visit(this);
	}

}
