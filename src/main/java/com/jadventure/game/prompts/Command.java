package com.jadventure.game.prompts;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String command();
    String aliases();
    String description();
}
