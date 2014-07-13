package com.jadventure.game.repository;

import com.jadventure.game.monsters.Monster;

public class MonsterBuilder extends EntityBuilder {

	public Monster create() {
		setType("monster");
		return new Monster(this);
	}

}
