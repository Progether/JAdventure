package com.jadventure.game.prompts;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Command annotates a command method in the CommandCollection
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String command();
    String[] aliases();
    String description();
    boolean debug();
}
