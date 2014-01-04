package com.jadventure.game.classes;

import com.jadventure.game.entities.Player;

public class Recruit extends Player {

    public Recruit() {
        this.setClassName("Recruit");
        this.setHealth(100);
        this.setHealthMax(100);
        this.setDamage(50);
        this.setArmour(1);
        this.setLevel(1);
        this.setIntro("Hey... you alive?\n*You let out a groan...*\nHey mate, you need to wake up. The guards will be coming round soon and they put a spear through the last guy they found still asleep.\n*Slowly you sit up.*\nThat's the way! I'm going to head back up. Follow me as soon as you can.\nBy the way, I'm Thorall, what's your name?");
    }

}
