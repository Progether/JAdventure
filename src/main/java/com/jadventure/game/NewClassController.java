package com.jadventure.game;

import com.jadventure.game.entities.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.WindowEvent;

public class NewClassController {
 // Reference to the main application.
    private JAdventure jAdventure;

    public NewClassController() {
    }
    
    @FXML
    private MenuItem back;
    @FXML
    private MenuItem close;
    @FXML
    private MenuItem github;
    @FXML
    private Button recruit;
    @FXML
    private Button sewerRat;
    
    @FXML
    public void newRecruit() throws DeathException {
        Player player = Player.getInstance("recruit");
        new Game(player, "new", jAdventure);
    }
    
    @FXML
    public void newSewerRat() throws DeathException {
        Player player = Player.getInstance("sewerrat");
        new Game(player, "new", jAdventure);
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
}
