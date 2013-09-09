import java.util.ArrayList;
import java.util.Scanner;

//	main class, gets user input and calls functions
public class Game {

    private static String licensing = "JAdventure  Copyright (C) 2013  Progether\n" +
            "This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.\n" +
            "This is free software, and you are welcome to redistribute it\n" +
            "under certain conditions; type `show c' for details.\n";

    private static String warranty = "THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY\n" +
            "APPLICABLE LAW.  EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT\n" +
            "HOLDERS AND/OR OTHER PARTIES PROVIDE THE PROGRAM \"AS IS\" WITHOUT WARRANTY\n" +
            "OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO,\n" +
            "THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR\n" +
            "PURPOSE.  THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE PROGRAM\n" +
            "IS WITH YOU.  SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF\n" +
            "ALL NECESSARY SERVICING, REPAIR OR CORRECTION.\n";

    private static String copying = "You may convey verbatim copies of the Program's source code as you\n" +
            "receive it, in any medium, provided that you conspicuously and\n" +
            "appropriately publish on each copy an appropriate copyright notice;\n" +
            "keep intact all notices stating that this License and any\n" +
            "non-permissive terms added in accord with section 7 apply to the code;\n" +
            "keep intact all notices of the absence of any warranty; and give all\n" +
            "recipients a copy of this License along with the Program.\n";

	public ArrayList<Monster> monsterList = new ArrayList<Monster>();
	public Map map = new Map();
	public MonsterCreator createMonster = new MonsterCreator();
	public Player player = new Player();
	public Scanner scanner = new Scanner(System.in);

	public Game(){
        System.out.println(licensing);
        initialize();
	}	

	// gets user input to call commands
	public void commands(){
		String userInput;

        while (true)
        {
            // Displays the interface
        	System.out.println("\n" + "Enter a Command:");

            userInput = scanner.nextLine().toLowerCase();
            switch (userInput.split(" ")[0]) {
//				case "attack":		attack(player,monster);
//									break;

				case "goto":

									goTo(userInput.substring(userInput.indexOf(' ')+1));
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

                case "show c":      System.out.println(copying);
                                    break;

                case "show w":      System.out.println(warranty);
                                    break;


				default:			System.out.print("\n" + "I don't know what '");
									//Console.ForegroundColor = ConsoleColor.DarkRed;
                                    /* String[] to a string that actually makes sense */
                                    System.out.print(userInput);
									//Console.ResetColor();
									System.out.println("' means.");
									System.out.println("Type 'help' for a list of "
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
//		System.out.println("Welcome to {Applzor, add7, geniuus, Malfunction, bdong_ and Qasaur}'s text adventure game!");		
//		System.out.print("Please type in your name (this will be used as your character name): ");
//		String userInput = scanner.nextLine();
//		player.setName(userInput);
		
		System.out.println("Hey... you alive?");
		System.out.println("*You let out a groan...*");
		System.out.println("Hey mate, you need to wake up. The guards will be coming around soon and they put a spear through the last guy they found still asleep.");
		System.out.println("*Slowly you sit up.*");
		System.out.println("That's the way! I'm Thorall, what's your name?");
		String userInput = scanner.nextLine();
		player.setName(userInput);
		System.out.println("Welcome to Silliya " + player.getName() + ".");
		
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
	private void goTo(String location) {
        /*
        TODO Change player's location
        Change String location to the related Location enum, then move player to the related Location enum
        */
    }
	private void help() {
		System.out.println("Commands:");
    	System.out.println("- goto location:  Takes you to a location.");
    	System.out.println("- help:              Gives you a list of commands.");
    	System.out.println("- locations:         Gives you a list of Locations.");
    	System.out.println("- monsters:          Tells you if any monsters are nearby");
    	System.out.println("- stats:             Shows your name, gold, location and backpack.");
    	System.out.println("- quit:              Exits the game.");
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

		System.out.println("Health: " + player.getHealthMax()
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
