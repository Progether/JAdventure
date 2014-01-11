package com.jadventure.game.classes;

import com.jadventure.game.entities.Player;

import java.util.Random;

/*
 * A Sewer Rat has low combat skills, but
 * high technical skills. He has access to the
 * underground and can sneak around the recruit camp.
 */
public class SewerRat extends Player {

    public SewerRat() {
        this.setClassName("Sewer Rat");
        this.setHealth(100);
        this.setHealthMax(100);
        this.setDamage(40);
        this.setArmour(0);
        this.setLevel(1);
        this.setStrength(1);
        this.setIntelligence(2);
        this.setDexterity(1);
        Random rand = new Random();
        int luck = rand.nextInt(3) + 1;
        this.setLuck(luck);
        this.setStealth(2);
        this.setWeapon("hands");
        this.setHead("none");
        this.setChest("shirt");
        this.setLegs("pants");
        this.setArms("none");
        this.setFeet("none");
        this.setShield("none");
        this.setIntro("Hey, rat. . . you dead??\n*You let out a groan...*\nWhat are you doing?!? Don't you know it's crazy to sleep so close to the recruits' camp? A guard will come down those stairs soon enough and catch you! You'll end up being drafted! You better come back in.\nBy the way, what's your name?");
    }

}
