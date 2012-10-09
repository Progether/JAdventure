import java.util.ArrayList;
import java.util.Scanner;


public class TextAdventure {
	public static void main(String[] args){
		
		// Initialize the game
		Game game = new Game();
		ArrayList<Monster> monsterList = new ArrayList<Monster>();
		Player player = new Player();
		game.init();
		MonsterCreator createMonster = new MonsterCreator();
		
		// generates monsters
		for (int i = 0; i < 5; i++){
			createMonster.Generate(player, monsterList);
		}	
		
		// Read user's first input
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to {Applzor, add7, geniuus, Malfunction, bdong_ and Qasaur}'s text adventure game!");
		
		System.out.print("Please type in your name (this will be used as your character name): ");
		String userInput = scanner.next();
		player.setName(userInput);
		
		// Prints out all monsters in the list
		for (Monster monster : monsterList){
			if (monsterList.isEmpty()){
				System.out.println("- " + "Empty");
			}
			else {
				System.out.println("- " + monster.getName());
			}			
		}		
		
		// Read and evaluates user input in the loop
		game.Basic(player, monsterList, scanner);
		
		// Quits the game
		game.quit();
		
		
	}
}
