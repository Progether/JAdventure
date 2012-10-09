import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	// Some instance variables?
	//	Player player;
	String playerName;
	boolean nameInput;
	String userInput;
	
	public Game(){
		
	}
	
	public void init(){
	//	this.player = new Player();
		nameInput = true;
	}
	
	public void input(String userInputString){
		if(nameInput){
			this.playerName = userInputString;
			System.out.println("Hello, " + playerName + "!");
			nameInput = false;
		}
		else{
			System.out.println(userInputString);
		}
	}
	
	public void quit(){
		System.out.println("Bye-bye!");
	}
	
	public void Basic(Player player, ArrayList<Monster> monsterList, Scanner scanner)
    {
		String userInput;
		
        while (true)
        {
            // Displays the interface
        	System.out.println("\n" + "Enter a Command:\n");

            userInput = scanner.next();
            System.out.println();

            if (userInput.toLowerCase().equals("clear"))
            {
            	System.out.println("not implemented");
            }
            else if (userInput.toLowerCase().equals("go to") || userInput.toLowerCase().equals("goto"))
            {
            	System.out.println("RUN 'GO TO' COMMAND");
            }
            else if (userInput.toLowerCase().equals("help"))
            {
            	System.out.println("Commands:");
            	System.out.println("- Clear:             Clears the screen.");
            	System.out.println("- Go to <Location>:  Takes you to a location.");
            	System.out.println("- Help:              Gives you a list of commands.");
            	System.out.println("- Locations:         Gives you a list of Locations.");
            	System.out.println("- Monsters:          Tells you if any monsters are nearby");
            	System.out.println("- Stats:             Shows your name, gold, location and backpack.");
            	System.out.println("- Quit:              Exits the game.");
            }
            else if (userInput.toLowerCase().equals("locations"))
            {
            	System.out.println("Locations:");
            	System.out.println("- Arena");
            	System.out.println("- Blacksmith");
            	System.out.println("- Temple");
            }
            else if (userInput.toLowerCase().equals("monsters"))
            {
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
            else if (userInput.toLowerCase().equals("stats"))
            {
                Interface(player);
            	//System.out.println("not implemented");
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
	
	public void Interface(Player player)
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
