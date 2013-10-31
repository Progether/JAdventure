import java.util.Scanner;

public class Menus {

    public Scanner input = new Scanner(System.in);

    public void mainMenu() {
        /*
	 *  Possibly parsed from XMLParser so menu items
	 *  can be adjusted without re-compile.
	*/
        System.out.println("Welcome to JAdventure! Pick an option: ");
        System.out.println("1. start - Start New Game (Not Implemented Yet)");
        System.out.println("2. save - Save Game (Not Implemented Yet)");
        System.out.println("3. load - Load Game (Not Implemented Yet)");
        System.out.println("3. quit - Quit Game (Not Implemented Yet)");
        String userInput;
        while (true) {
            userInput = input.next();
            userInput = userInput.toLowerCase();
            switch (userInput) {
                case "start":
                    Game game = new Game("new");
                    game.commands();
                    break;
                case "quit":
                    System.exit(0);
                    break;
                default:
                    System.out.print("\nI don't know what '" + userInput + "' means");
                    break;
            }
        }
    }
    
}
