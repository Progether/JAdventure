package com.jadventure.game.classes;

import com.jadventure.game.entities.Player;

import java.util.Random;

/*
 * A recruit has a pretty strong character in terms of combat
 * but low technical skill. He has access to the training camp
 * but none to the underground unless he deserts his position.
 */
public class Recruit extends Player {

    public Recruit() {
        this.setClassName("Recruit");
        this.setHealth(100);
        this.setHealthMax(100);
        this.setDamage(50);
        this.setArmour(1);
        this.setLevel(1);
        this.setStrength(2);
        this.setIntelligence(1);
        this.setDexterity(2);
        Random rand = new Random();
        int luck = rand.nextInt(3) + 1;
        this.setLuck(luck);
        this.setStealth(1);
        this.setWeapon("hands");
        this.setHead("none");
        this.setChest("shirt");
        this.setLegs("pants");
        this.setArms("none");
        this.setFeet("none");
        this.setShield("none");
        this.setIntro("Hey... you alive?\n*You let out a groan...*\nHey mate, you need to wake up. The guards will be coming round soon and they put a spear through the last guy they found still asleep.\n*Slowly you sit up.*\nThat's the way! I'm going to head back up. Follow me as soon as you can.\nBy the way, I'm Thorall, what's your name?");
    }

}
