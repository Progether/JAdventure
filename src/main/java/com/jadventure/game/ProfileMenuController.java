package com.jadventure.game;

import com.jadventure.game.menus.MainMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

public class ProfileMenuController {
    
    // Reference to the main application.
    private JAdventure jAdventure;

    public ProfileMenuController() {
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
    private Text exists;
    @FXML
    private GridPane profileGrid;
    @FXML
    private Button menu;
    
    @FXML
    public void saveGame() {
        // Does nothing
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
    
    public void loadProfiles() {
        String[] profiles = MainMenu.listProfiles();
        if (profiles.length == 0) {
            exists.setText("There are no profiles to load. Please start a new game instead.");
        } else {
            
        }
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param jAdventure
     * @throws DeathException 
     */
    public void setMainApp(JAdventure jAdventure) {
        this.jAdventure = jAdventure;
        loadProfiles();
    }
}
