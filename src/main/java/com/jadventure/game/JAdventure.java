package com.jadventure.game;
	
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jadventure.game.menus.MainMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This is the starting point of the game.
 * This class doesn't do much more than create
 * a new MainMenu that will handle the rest of
 * the game.
 */
public class JAdventure extends Application {
    private Stage primaryStage;
    private BorderPane root;
    private static Logger logger = LoggerFactory.getLogger(JAdventure.class);
    
    @Override
    public void init() {
        // TODO
    }
    
    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("JAdventure"); // TODO set stage(window) title
        } catch(Exception e) {
            e.printStackTrace();
        }
        String[] args = getArguments();
        logger.info("Starting JAdventure " + toString(args));
        GameModeType mode = getGameMode(args);
        logger.debug("Starting in mode " + mode.name());
        String serverName = "localhost";
        int port = 4044;
        if (mode == GameModeType.SERVER) {
            port = Integer.parseInt(args[1]);
        } else if (mode == GameModeType.CLIENT) {
            serverName = args[2];
            port = Integer.parseInt(args[1]);
        }
        if (GameModeType.CLIENT == mode) {
            new Client(serverName, port);
        } else if (GameModeType.SERVER == mode) {
            while (true) {
                ServerSocket listener = null;
                try {
                    listener = new ServerSocket(port);
                    while (true) {
                        Socket server = listener.accept();
                        Runnable r = new MainMenu(server, mode, this);
                        new Thread(r).start();
                    }
                } catch (IOException c) {
                    c.printStackTrace();
                } finally {
                    try {
                        listener.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            QueueProvider.startMessenger(GameModeType.STAND_ALONE);
            new MainMenu(this).start();
        }
    }
    
    private static GameModeType getGameMode(String[] args) {
        if (args == null || args.length == 0 || "".equals(args[0].trim())) {
            return GameModeType.STAND_ALONE;
        }

        try {
            return GameModeType.valueOf(args[0].toUpperCase());
        }
        catch (IllegalArgumentException iae) {
            logger.warn("No game mode '" + args[0].toUpperCase() + "' known." +
                    "Terminating application.");
            System.exit(-1);
        }
        return GameModeType.STAND_ALONE;
    }

    private static String toString(String[] args) {
        if (args.length == 0) {
            return "";
        }

        final StringBuilder bldr = new StringBuilder();
        bldr.append("[ ");
        for (int index = 0; index < args.length; index++) {
            if (index > 0) {
                bldr.append(", ");
            }
            bldr.append(args[index]);
        }
        bldr.append(" ]");
        return bldr.toString();
    }
    
    /**
     * Shows MainMenu scene
     */
    public void loadMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JAdventure.class.getResource("/com/jadventure/game/view/MainMenu.fxml")); // TODO Change class name accordingly
            root = (BorderPane) loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            setStageSize();
            primaryStage.show();
            MainMenuController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getArguments() {
        List<String> params = getParameters().getRaw();
        if (params.size() > 0) {
            String[] args = new String[params.size()];
            int index = 0;
            for (String param : params) {
                args[index] = param;
                index++;
            }
            return args;
        } else {
            return new String[0];
        }
	}
	
	public void setStageSize() {
	    // Sets height and width to match screen size
	    Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
	    primaryStage.setX(bounds.getMinX());
	    primaryStage.setY(bounds.getMinY());
	    primaryStage.setWidth(bounds.getWidth());
	    primaryStage.setHeight(bounds.getHeight());
	}
	
	public Stage getPrimaryStage() {
	    return primaryStage;
	}
	
	public void setPrimaryStage(Stage primaryStage) {
	    this.primaryStage = primaryStage;
	}
	
	public BorderPane getRoot() {
	    return root;
	}
	
	public void setRoot(BorderPane root) {
	    this.root = root;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
