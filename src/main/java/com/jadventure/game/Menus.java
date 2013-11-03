package com.jadventure.game;

import java.util.Scanner;
import java.io.File;

public class Menus {

    public Scanner input = new Scanner(System.in);

    public void mainMenuSwitch() {
        // The manual menu. Deprecated for development of json menus
        /*
        System.out.println("Welcome to JAdventure! Pick an option: ");
        System.out.println("1. start - Start New Game (Not Implemented Yet)");
        System.out.println("2. save - Save Game (Not Implemented Yet)");
        System.out.println("3. load - Load Game (Not Implemented Yet)");
        System.out.println("3. quit - Quit Game (Not Implemented Yet)");
        */
        while (true) {
            JSONReader json = new JSONReader();
            json.getMenu("Main");
            String userInput = input.next();
            userInput = userInput.toLowerCase();
            
            if(userInput.equals("start")) {
                Game game = new Game("new");
                game.commands();
            }
            else if(userInput.equals("exit")) {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            else if(userInput.equals("save")) {
                System.out.println("Not implemented yet");
            }
            else if(userInput.equals("load")) {
                String test = "false";
                System.out.println("What is the name of the avatar you want to load?");
                while (test.equals("false")) {
                    userInput = input.next();
                    File f = new File("json/profiles/"+userInput+"/"+userInput+"_profile.json");
                    if (f.exists()) {
                        test = "true";
                        Game game = new Game(userInput);
                        game.commands();
                    }
                    System.out.println("That user doesn't exist. Try again");
                }
            }
            else {
                System.out.print("\nI don't know what '" + userInput + "' means");
            }
        }
    }
}
