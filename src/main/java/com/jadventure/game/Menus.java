package com.jadventure.game;

import java.util.Scanner;

public class Menus {

    public Scanner input = new Scanner(System.in);

    public void menuSwitch(String userInput) {
        // The manual menu. Deprecated for development of json menus
        /*
        System.out.println("Welcome to com.jadventure.game! Pick an option: ");
        System.out.println("1. start - Start New Game (Not Implemented Yet)");
        System.out.println("2. save - Save Game (Not Implemented Yet)");
        System.out.println("3. load - Load Game (Not Implemented Yet)");
        System.out.println("3. quit - Quit Game (Not Implemented Yet)");
        String userInput = input.next();
        */
        userInput = userInput.toLowerCase();
        switch (userInput) {
            case "start":
                Game game = new Game("new");
                game.commands();
                break;
            case "exit":
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            case "save":
                System.out.println("Not implemented yet");
                break;
            case "load":
                System.out.println("Not implemented yet");
                break;
            default:
                System.out.print("\nI don't know what '" + userInput + "' means");
                break;
        }
    }
}
