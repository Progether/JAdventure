import java.util.ArrayList;
import java.util.Scanner;

//	main class, gets user input and calls functions
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

	// gets user input to call commands
	public void commands(){
		String userInput;
		
        while (true)
        {
            // Displays the interface
        	System.out.println("\n" + "Enter a Command:");

            userInput = scanner.next();

			String userInputLowerCase = userInput.toLowerCase();
			switch (userInputLowerCase) {
				case "clear":	clear();
								break;

				case "go to": case "goto":
									go_to();
									break;

				case "help":		help();
									break;

				case "locations":	locations();
									break;

				case "monsters":	monsters(monsterList);
									break;

				case "stats":		stats(player);
									break;

				case "quit":		System.exit(0);
									break;


				default:			System.out.print("\n" + "I don't know what '");
									//Console.ForegroundColor = ConsoleColor.DarkRed;
									System.out.print(userInput);
									//Console.ResetColor();
									System.out.println("' means.");
									System.out.println("Type HELP for a list of"
											+ "commands.");
									break;

			}
		}
	}

	public void initialize(){
		// generates monsters
		for (int i = 0; i < 1; i++){
			createMonster.Generate(monsterList);
		}	
				
		// Read user's first input
		System.out.println("Welcome to {Applzor, add7, geniuus, Malfunction, bdong_ and Qasaur}'s text adventure game!");		
		System.out.print("Please type in your name (this will be used as your character name): ");
		String userInput = scanner.next();
		player.setName(userInput);
	}
		
	// COMMANDS
	private void attack(Entity player, Entity monster) {
		System.out.println("----------------------------------");
		
		System.out.println("p_damage: " + player.getDamage());
		System.out.println("m_armour: " + monster.getArmour());
				
		System.out.println("mh_before: " + monster.getHealthCurrent());
		player.attack(player, monster);
		System.out.println("mh_after: " + monster.getHealthCurrent());
		
		System.out.println("----------------------------------");
		
		System.out.println("----------------------------------");
		
		System.out.println("m_damage: " + monster.getDamage());
		System.out.println("p_armour: " + player.getArmour());
				
		System.out.println("ph_before: " + player.getHealthCurrent());
		monster.attack(monster, player);
		System.out.println("ph_after: " + player.getHealthCurrent());
		
		System.out.println("----------------------------------");
		
	}
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
	private void locations() {
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

		System.out.println("Max Health / Health: " + player.getHealthMax()
				+ " / " + player.getHealthCurrent());
		System.out.println("Damage: " + player.getDamage());


		System.out.println("Max Health / Health: " + player.getHealthMax()
				+ " / " + player.getHealthCurrent());
		System.out.println("Damage: " + player.getDamage());

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
