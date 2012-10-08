import java.util.ArrayList;
import java.util.Scanner;


public class TextAdventure {
	public static void main(String[] args){
		
		// Initialize the game
		Game game = new Game();
		ArrayList<Monster> monsterList = new ArrayList<Monster>();
		Player player = new Player();
		game.init();
		MonsterCreator createMonsters = new MonsterCreator();
		
		createMonsters.Generate(player, monsterList);
		
		
		// Read user's first input
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to {Applzor, add7, geniuus, Malfunction, bdong_ and Qasaur}'s text adventure game!");
		
		System.out.print("Please type in your name (this will be used as your character name): ");
		String userInputString = scanner.next();
		
		for (Monster monster : monsterList){
			if (monsterList.isEmpty()){
				System.out.println("- " + "Empty");
			}
			else {
				System.out.println("- " + monster.getName());
			}
			
		}
		
		
		// Read and evaluates user input in the loop
		while(!userInputString.toLowerCase().equals("quit")){
			game.input(userInputString);
			userInputString = scanner.next();
		}
		
		// Quits the game
		game.quit();
		
		
	}
}
