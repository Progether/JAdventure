package com.jadventure.game.monsters;

import com.jadventure.game.entities.Entity;

/*
 * This class just holds a type of monster that is 
 * further outlined in its respective file. For now it
 * just holds the monster's name.
 */
public abstract class Monster extends Entity {
	public String monsterType;
    private int xpGain;

    public int getXPGain() {
        return xpGain;
    }

    public void setXPGain(int xpGain) {
        this.xpGain = xpGain;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Monster) {
            Monster m = (Monster) obj;
            return m.monsterType.equals(this.monsterType);
        }
        return false;
    }
}
