package com.jadventure.game;

import com.jadventure.game.menus.ChooseClassMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.WindowEvent;

public class MainMenuController {
    
    // Reference to the main application.
    private JAdventure jAdventure;

    public MainMenuController() {
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
    private Button start;
    @FXML
    private Button load;
    @FXML
    private Button quit;
    
    @FXML
    public void startGame() throws DeathException {
        new ChooseClassMenu();
    }
    
    @FXML
    public void loadProfile() {
        jAdventure.loadProfileMenu();
    }
    
    @FXML
    public void saveGame() {
        // Does nothing
    }
    
    @FXML
    public void goToMainMenu() {
        // Does nothing
    }
    
    @FXML
    public void goToGithub() {
        
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
        // TODO init
    }
}
