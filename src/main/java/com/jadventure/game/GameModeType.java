package com.jadventure.game;

/**
 * There are two modes in which you can run the game.
 * <ul>
 * <li>stand-alone<p>Single computer, Single player (default mode)</p></li> 
 * <li>client server<p>Single or Multiple computer(s), Multiple players possible</p></li>
 * </ul>
 */
public enum GameModeType {
    /** Stand alone architecture */
    STAND_ALONE,
    /** The Client part of the Client / Server architecture */
    CLIENT,
    /** The Server part of the Client / Server architecture */
    SERVER
}
