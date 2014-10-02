package com.jadventure.game;

public class DeathException extends Exception {
    private String message;

    public DeathException(String message) {
        super(message);
        this.message = message;
    }

    public String getLocalisedMessage() {
        return message;
    }
}
