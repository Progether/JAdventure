package com.jadventure.game;

import com.jadventure.game.entities.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

public class WelcomeController {
    
    // Reference to the main application.
    private JAdventure jAdventure;
    private Player player;
    private String type;

    public WelcomeController() {
    }
    
    @FXML
    private MenuItem back;
    @FXML
    private MenuItem close;
    @FXML
    private MenuItem github;
    @FXML
    private Text welcome;
    @FXML
    private Button cont;
    
    @FXML
    public void play() {
        jAdventure.loadGame(player);
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
    
    public void showWelcome() {
        if (type.equals("new")) {
            welcome.setText("Welcome to Silliya, " + player.getName() + "!");
        } else if (type.equals("old")) {
            welcome.setText("Welcome back, " + player.getName() + "!");
        }
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

    public void setType(String type) {
        this.type = type;
        showWelcome();
    }
}
