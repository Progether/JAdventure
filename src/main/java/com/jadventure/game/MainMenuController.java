package com.jadventure.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainMenuController {
    // Reference to the main application.
    private JAdventure jAdventure;

    public MainMenuController() {
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param jAdventure
     */
    public void setMainApp(JAdventure jAdventure) {
        this.jAdventure = jAdventure;
        // TODO init
    }
}
