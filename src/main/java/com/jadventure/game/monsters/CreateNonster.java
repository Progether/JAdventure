package com.jadventure.game.monsters;

/**
 * The interface for two methods for realise the all monsters inside this class
 */
public interface CreateNonster {
    String monsterType(String type);
     void addRandomItems(int playerLevel, String... children);
}
