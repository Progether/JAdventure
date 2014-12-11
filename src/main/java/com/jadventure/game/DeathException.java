package com.jadventure.game;

public class DeathException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;

    public DeathException(String message) {
        super(message);
        this.message = message;
    }

    public String getLocalisedMessage() {
        return message;
    }
}
