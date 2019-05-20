package com.jadventure.game.menus;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import com.jadventure.game.GameModeType;
import com.jadventure.game.JAdventure;
import com.jadventure.game.QueueProvider;

/**
 * The first menu displayed on user screen
 * @see JAdventure
 * This menu lets the player choose whether to load an exiting game,
 * start a new one, or exit to the terminal.
 */
public class MainMenu extends Menus implements Runnable {
    
    private JAdventure jAdventure;
    
    public MainMenu(Socket server, GameModeType mode, JAdventure jAdventure){
        this.jAdventure = jAdventure;
        QueueProvider.startMessenger(mode, server);
    }

    public MainMenu(JAdventure jAdventure) {
        this.jAdventure = jAdventure;
    }
    
    public void run() {
        start();
    }
    
    public void start() {
        jAdventure.loadMainMenu();
    }

    public static boolean deleteDirectory(File directory) {
        System.out.println(directory);
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

    public static List<String> listProfiles() {
        if (isProfileDirEmpty()) {
            //QueueProvider.offer("No profiles found.");
            return new ArrayList<String>();
        } else {
            File file = new File("json/profiles");
            String[] possibleProfiles = file.list();
            List<String> profiles = new ArrayList<String>();
            //QueueProvider.offer("Profiles:");
            for (String name : possibleProfiles) {
                if (new File("json/profiles/" + name).isDirectory()) {
                    //QueueProvider.offer("  " + name);
                    profiles.add(name);
                }
            }
            return profiles;
        }
    }
}
