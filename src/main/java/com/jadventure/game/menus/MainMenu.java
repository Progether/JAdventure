package com.jadventure.game.menus;

import java.io.File;
import java.net.Socket;

import com.jadventure.game.DeathException;
import com.jadventure.game.Game;
import com.jadventure.game.GameModeType;
import com.jadventure.game.JAdventure;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.entities.Player;

/**
 * The first menu displayed on user screen
 * @see JAdventure
 * This menu lets the player choose whether to load an exiting game,
 * start a new one, or exit to the terminal.
 */
public class MainMenu extends Menus implements Runnable {
     
    public MainMenu(Socket server, GameModeType mode){
        QueueProvider.startMessenger(mode, server);
    }

    public MainMenu() {
        start();
    }
    
    public void run() {
        start();
    }

    public void start() {
        menuItems.add(new MenuItem("Start", "Starts a new Game", "new"));
        menuItems.add(new MenuItem("Load", "Loads an existing Game"));
        menuItems.add(new MenuItem("Delete", "Deletes an existing Game"));
        menuItems.add(new MenuItem("Exit", null, "quit"));

        boolean continuing = true;
        do {
            MenuItem selectedItem = displayMenu(menuItems);
            try {
                continuing = testOption(selectedItem);
            } catch (DeathException e) {
                if (e.getLocalisedMessage().equals("close")) {
                    continuing = false;
                }
            }
        } while(continuing);
        QueueProvider.offer("EXIT");
    }

    private static boolean testOption(MenuItem m) throws DeathException {
        String key = m.getKey();
        switch (key){
            case "start":
                new ChooseClassMenu();
                break;
            case "load":
                loadProfileFromMenu();
                break;
            case "delete":
                deleteProfileFromMenu();
                break;
            case "exit":
                QueueProvider.offer("Goodbye!");
                return false;
        }
        return true;
    }

    private static void loadProfileFromMenu() throws DeathException {
        String key;
        if (isProfileDirEmpty()) {
            QueueProvider.offer("\nThere are no profiles to load. Please start a new game instead.");
            return;
        }
        Player player = null;
        do {
            listProfiles();
            QueueProvider.offer("\nSelect a profile to load. Type 'back' to go back.");
            key = QueueProvider.take();
            if (key.equals("exit") || key.equals("back")) {
                return;
            } else if (Player.profileExists(key)) {
                player = Player.load(key);
            } else {
                QueueProvider.offer("That user doesn't exist. Try again.");
            }
        } while (player == null);
        new Game(player, "old");
    }

    private static void deleteProfileFromMenu() {
        String key;
        while (true) {
            if (isProfileDirEmpty()) {
                QueueProvider.offer("\nThere are no profiles to delete.");
                return;
            }
            listProfiles();
            QueueProvider.offer("\nWhich profile do you want to delete? Type 'back' to go back.");
            key = QueueProvider.take();
            if ((key.equals("exit") || key.equals("back"))) {
                return;
            }
            if (Player.profileExists(key)) {
                String profileName = key;
                QueueProvider.offer("Are you sure you want to delete " + profileName + "? y/n");
                key = QueueProvider.take();
                if ((key.equals("exit") || key.equals("back"))) {
                    return;
                } else if (key.equals("y")) {
                    File profile = new File("json/profiles/" + profileName);
                    deleteDirectory(profile);
                    QueueProvider.offer(profileName + " has been deleted.");
                    return;
                } else {
                    QueueProvider.offer(profileName + " will NOT be deleted.");
                }
            } else {
                QueueProvider.offer("That user doesn't exist. Try again.");
            }
        }
    }

    private static boolean deleteDirectory(File directory) {
        if(directory.exists()){
            File[] files = directory.listFiles();
            for (File file : files) {
                if(file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return directory.delete();
    }

    private static boolean isProfileDirEmpty() {
        int numProfiles = new File("json/profiles").list().length;
        return (numProfiles == 0);
    }

    private static void listProfiles() {
        if (isProfileDirEmpty()) {
            QueueProvider.offer("No profiles found.");
            return;
        }
        File file = new File("json/profiles");
        String[] profiles = file.list();
        QueueProvider.offer("Profiles:");
        for (String name : profiles) {
            if (new File("json/profiles/" + name).isDirectory()) {
                QueueProvider.offer("  " + name);
            }
        }
    }
}
