import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	
	public ArrayList<Monster> monsterList = new ArrayList<Monster>();
	public Map map = new Map();
	public MonsterCreator createMonster = new MonsterCreator();
	public Player player = new Player();	
	public Scanner scanner = new Scanner(System.in);
	String userInput;
	
	public Game(){
		initialize();
	}
	
	public void commands(){
		String userInput;
		
        while (true)
        {
            // Displays the interface
        	System.out.println("\n" + "Enter a Command:\n");

            userInput = scanner.next();
            System.out.println();

            if (userInput.toLowerCase().equals("clear"))
            {
            	clear();
            }
            else if (userInput.toLowerCase().equals("go to") || userInput.toLowerCase().equals("goto"))
            {
            	go_to();
            }
            else if (userInput.toLowerCase().equals("help"))
            {
            	help();
            }
            else if (userInput.toLowerCase().equals("locations"))
            {
            	Locations();
            }
            else if (userInput.toLowerCase().equals("monsters"))
            {
            	monsters(monsterList);

            }
            else if (userInput.toLowerCase().equals("stats"))
            {
            	stats(player);
            }
            else if (userInput.toLowerCase().equals("quit"))
            {
                break;
            }
            else if (userInput.isEmpty()){
            	System.out.print("Please enter a command, type HELP for a list of commands.");
            }
            else
            {
            	System.out.print("\n" + "I don't know what '");
                //Console.ForegroundColor = ConsoleColor.DarkRed;
                System.out.print(userInput);
                //Console.ResetColor();
                System.out.print("' means.");
                System.out.println();
            }
        }
    }
	public void initialize(){
		
		// generates monsters
		for (int i = 0; i < 5; i++){
			createMonster.Generate(player, monsterList);
		}	
				
		// Read user's first input
		System.out.println("Welcome to {Applzor, add7, geniuus, Malfunction, bdong_ and Qasaur}'s text adventure game!");		
		System.out.print("Please type in your name (this will be used as your character name): ");
		String userInput = scanner.next();
		player.setName(userInput);
		
		
	}
		
	// COMMANDS
	private void clear() {		
		System.out.println("not implemented");
	}	
	private void go_to() {
		System.out.println("RUN 'GO TO' COMMAND");
	}	
	private void help() {
		System.out.println("Commands:");
    	System.out.println("- Clear:             Clears the screen.");
    	System.out.println("- Go to <Location>:  Takes you to a location.");
    	System.out.println("- Help:              Gives you a list of commands.");
    	System.out.println("- Locations:         Gives you a list of Locations.");
    	System.out.println("- Monsters:          Tells you if any monsters are nearby");
    	System.out.println("- Stats:             Shows your name, gold, location and backpack.");
    	System.out.println("- Quit:              Exits the game.");
	}	
	private void Locations() {
		System.out.println("Locations:");
    	System.out.println("- Arena");
    	System.out.println("- Blacksmith");
    	System.out.println("- Temple");
	}	
	private void monsters(ArrayList<Monster> monsterList) {
		System.out.println("Nearby Monsters:");
        if (monsterList.isEmpty())
        {
        	System.out.println("- None");
        }
        else
        {
            for (Monster monster : monsterList)
            {
            	System.out.println("- " + monster.getName());
            }
        }
	}	
	private void stats(Player player)
    {
		System.out.println("\n--------------------------------------------------------------------\n");
		System.out.println("Name: " + player.getName() + "       ");
		System.out.println("Gold: " + player.getGold() + "       ");
		//System.out.println("Location: " + player.getLocation + "      ");
		System.out.println("Backpack: ");
		
        if (player.getBackpack().isEmpty()){
        	System.out.println("- Empty");
        }
        else{
            for (Item item : player.getBackpack())
            {
            	System.out.println("- " + item.getName());
            }
        }
        System.out.println("\n--------------------------------------------------------------------");
    }  
}
