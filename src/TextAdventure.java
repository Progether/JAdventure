import java.util.Scanner;
public class TextAdventure {

	
	/*
	 * This class will be done away with and
	 * JAdventure will hold the main() method
	 * 
	 * Using this class for now to debug methods.
	 */
    public static void main(String[] args) {
        // Initialize the game
        // Game game = new Game();
        // game.initialize();
    	// game.commands();
    	Menus menu = new Menus();
    	Scanner input = new Scanner(System.in);
    	
    	while (true){
    		JSONReader json = new JSONReader();
    		json.getMenu(input.next());
    	}
    }

}
