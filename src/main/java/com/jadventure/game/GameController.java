package com.jadventure.game;

import com.jadventure.game.entities.Player;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.WindowEvent;

public class GameController {
    
    // Reference to the main application.
    private JAdventure jAdventure;
    private Player player;

    public GameController() {
    }
    
    @FXML
    private MenuItem save;
    @FXML
    private MenuItem back;
    @FXML
    private MenuItem close;
    @FXML
    private MenuItem github;

    @FXML
    public void saveGame() {
        player.save();
    }
    
    @FXML
    public void goToMainMenu() {
        jAdventure.loadMainMenu();
    }
    
    @FXML
    public void goToGithub() {
        // Goes to https://github.com/Progether/JAdventure
    }
    
    @FXML
    public void quitGame() throws Exception {
        jAdventure.getPrimaryStage().fireEvent(
                new WindowEvent(jAdventure.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param jAdventure
     */
    public void setMainApp(JAdventure jAdventure) {
        this.jAdventure = jAdventure;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
}
