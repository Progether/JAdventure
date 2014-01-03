package com.jadventure.game.classes;

import com.jadventure.game.entities.Player;

public class SewerRat extends Player {

    public SewerRat() {
        this.setClassName("Sewer Rat");
        this.setHealth(100);
        this.setHealthMax(100);
        this.setDamage(40);
        this.setArmour(0);
        this.setLevel(1);
        this.setWeapon("hands");
        this.setIntro("Hey, rat. . . you dead??\n*You let out a groan...*\nWhat are you doing?!? Don't you know it's crazy to sleep so close to the recruits' camp? A guard will come down those stairs soon enough and catch you! You'll end up being drafted! You better come back in.\nBy the way, what's your name?");
    }

}
