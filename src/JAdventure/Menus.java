import java.util.Scanner;

public class Menus {

    public Scanner input = new Scanner(System.in);

    public void menuSwitch(String userInput) {
        // The manual menu. Deprecated for development of json menus
        /*
        System.out.println("Welcome to JAdventure! Pick an option: ");
        System.out.println("1. start - Start New Game (Not Implemented Yet)");
        System.out.println("2. save - Save Game (Not Implemented Yet)");
        System.out.println("3. load - Load Game (Not Implemented Yet)");
        System.out.println("3. quit - Quit Game (Not Implemented Yet)");
        String userInput = input.next();
        */
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
             System.out.println("Not implemented yet");
        }
        else {
             System.out.print("\nI don't know what '" + userInput + "' means");
        }
    }
}
