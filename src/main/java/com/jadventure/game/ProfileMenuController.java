package com.jadventure.game;

import java.io.File;
import java.util.List;
import java.util.Optional;
import com.jadventure.game.entities.Player;
import com.jadventure.game.menus.MainMenu;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
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
        List<String> profiles = MainMenu.listProfiles();
        if (profiles.size() == 0) {
            exists.setText("There are no profiles to load. Please start a new game instead.");
        } else {
            for (int i=0; i<profiles.size(); i++) {
                Text profName = new Text(profiles.get(i));
                profName.setFont(Font.font(20));
                profileGrid.add(profName, 0, i);
                
                ImageView load = new ImageView(new Image("/com/jadventure/game/images/load.png"));
                load.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        ImageView img = (ImageView) e.getSource();
                        int profileIndex = GridPane.getRowIndex(img);
                        Text profileNameT = (Text) profileGrid.getChildren().get(profileIndex * 3);
                        String profileName = profileNameT.getText();
                        Player player = null;
                        player = Player.load(profileName);
                        try {
                            new Game(player, "old");
                        } catch (DeathException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                profileGrid.add(load, 1, i);
                
                ImageView del = new ImageView(new Image("/com/jadventure/game/images/delete.png"));
                del.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        ImageView img = (ImageView) e.getSource();
                        int profileIndex = GridPane.getRowIndex(img);
                        Text profileNameT = (Text) profileGrid.getChildren().get(profileIndex * 3);
                        String profileName = profileNameT.getText();
                        
                        ButtonType yes = new ButtonType("Yes");
                        ButtonType cancel = new ButtonType("Cancel");
                        Alert alert = new Alert(AlertType.CONFIRMATION,
                                "",
                                yes, cancel);
                        alert.initOwner(jAdventure.getPrimaryStage());
                        alert.setTitle("Delete profile");
                        alert.setHeaderText("Are you sure you want to delete " + profileName + "?");
                        alert.setContentText("The profile will be deleted permanently.");
                        alert.setGraphic(null);
                        Optional<ButtonType> clicked = alert.showAndWait();
                        String selection = clicked.get().getText();
                        if (selection.equals("Yes")) {
                            File profile = new File("json/profiles/" + profileName);
                            boolean deleted = MainMenu.deleteDirectory(profile);
                            System.out.println(deleted);
                        }
                        jAdventure.loadProfileMenu();
                    }
                });
                profileGrid.add(del, 2, i);
                
            }
            profileGrid.getRowConstraints().add(new RowConstraints(55, 55, Region.USE_COMPUTED_SIZE));
            profileGrid.getColumnConstraints().add(new ColumnConstraints(55, 55, Region.USE_COMPUTED_SIZE));
            profileGrid.setVgap(20);
            profileGrid.setHgap(20);
            profileGrid.setPadding(new Insets(50, 100, 0, 100));
            profileGrid.setAlignment(Pos.CENTER);
        }
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param jAdventure
     */
    public void setMainApp(JAdventure jAdventure) {
        this.jAdventure = jAdventure;
        loadProfiles();
    }
}
