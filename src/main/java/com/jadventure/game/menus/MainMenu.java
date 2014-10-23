package com.jadventure.game.menus;

import com.jadventure.game.menus.Menus;

import com.jadventure.game.Game;
import com.jadventure.game.entities.Player;
import com.jadventure.game.menus.ChooseClassMenu;
import com.jadventure.game.DeathException;
import com.jadventure.game.QueueProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.net.Socket;

/**
 * The first menu diplayed on user screen
 * @see JAdventure
 * This menu lets the player choose whether to load an exiting game,
 * start a new one, or exit to the terminal.
 */
public class MainMenu extends Menus implements Runnable {
     
    public MainMenu(Socket server,String mode){
        new QueueProvider().startMessenger(server,mode);
    }

    public MainMenu() {
        start();
    }
    
    public void run() {
        start();
    }

    public void start() {
        this.menuItems.add(new MenuItem("Start", "Starts a new Game", "new"));
        this.menuItems.add(new MenuItem("Load", "Loads an existing Game"));
        this.menuItems.add(new MenuItem("Delete", "Deletes an existing Game"));
        this.menuItems.add(new MenuItem("Exit", null, "quit"));
        
        while(true) {
            try {
                MenuItem selectedItem = displayMenu(this.menuItems);
                boolean exit = testOption(selectedItem);
                if (!exit) {
                    break;
                }
            } catch (DeathException e) {
                if (e.getLocalisedMessage().equals("close")) {
                    break;
                }
            }
        }
        QueueProvider.offer("EXIT");
    
    }

    private static boolean testOption(MenuItem m) throws DeathException {
        String key = m.getKey();
        if(key.equals("start")) {
            try {
                Path orig = Paths.get("json/original_data/locations.json");
                Path dest = Paths.get("json/locations.json");
                Files.copy(orig, dest, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                QueueProvider.offer("Unable to load new locations file.");
                ex.printStackTrace();
            }
            new ChooseClassMenu();
        }
        else if(key.equals("exit")) {
            QueueProvider.offer("Goodbye!");
            return false;
        }
        else if(key.equals("load")) {
            listProfiles();
            QueueProvider.offer("\nWhat is the name of the avatar you want to load? Type 'back' to go back");
            Player player = null;

            boolean exit = false;
            while (player == null) {
                key = QueueProvider.take();
                if (Player.profileExists(key)) {
                    player = Player.load(key);
                } else if (key.equals("exit") || key.equals("back")) {
                    exit = true;
                    break;
                } else {
                    QueueProvider.offer("That user doesn't exist. Try again.");
                }
            }

            if (exit) {
                return true;
            }

            Game game = new Game(player, "old");
        } else if (key.equals("delete")) {
            listProfiles();
            QueueProvider.offer("\nWhich profile do you want to delete? Type 'back' to go back");
            boolean exit = false;
            while (!exit) {
                key = QueueProvider.take();
                if (Player.profileExists(key)) {
                    String profileName = key;
                    QueueProvider.offer("Are you sure you want to delete " + profileName + "? y/n");
                    key = QueueProvider.take();
                    if (key.equals("y")) {
                        File profile = new File("json/profiles/" + profileName);
                        deleteDirectory(profile);
                        QueueProvider.offer("Profile Deleted");
                        return true;
                    } else {
                        listProfiles();
                        QueueProvider.offer("\nWhich profile do you want to delete?");
                    }
                } else if (key.equals("exit") || key.equals("back")) {
                    exit = true;
                    break;
                } else {
                    QueueProvider.offer("That user doesn't exist. Try again.");
                }
            }
        }
        return true;
    }

    private static boolean deleteDirectory(File directory) {
        if(directory.exists()){
            File[] files = directory.listFiles();
            if(null!=files){
                for(int i=0; i<files.length; i++) {
                    if(files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    }
                    else {
                        files[i].delete();
                    }
                }
            }
        }
        return(directory.delete());
    }

    private static void listProfiles() {
        QueueProvider.offer("Profiles:");
        File file = new File("json/profiles");
        String[] profiles = file.list();
        int i = 1;
        for (String name : profiles) {
            if (new File("json/profiles/" + name).isDirectory()) {
                QueueProvider.offer("  " + i + ". " + name);
            }
           i += 1;
        }
    }
}
