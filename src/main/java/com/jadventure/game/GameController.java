package com.jadventure.game;

import com.jadventure.game.entities.Player;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
    private Text locationTitle;
    @FXML
    private Text locationDesc;
    @FXML
    private ProgressBar health;
    @FXML
    private Text hpString;
    
    public void loadState() {
        loadCurrentLocation();
        loadHealthBar();
    }
    
    public void loadCurrentLocation() {
        locationTitle.setText(player.getLocation().getTitle());
        locationDesc.setText(player.getLocation().getDescription());
    }
    
    public void loadHealthBar() {
        int currentHealth = player.getHealth();
        int maxHealth = player.getHealthMax();
        double healthPercentage = (double) currentHealth / (double) maxHealth;
        health.setProgress(healthPercentage);
        hpString.setText(currentHealth + "/" + maxHealth);
    }
    
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
        loadState();
    }
}
