package com.jadventure.game;

import com.jadventure.game.menus.MainMenu;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
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
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param jAdventure
     * @throws DeathException 
     */
    public void setMainApp(JAdventure jAdventure) throws DeathException {
        this.jAdventure = jAdventure;
        //MainMenu.testOption("load");
    }
}
