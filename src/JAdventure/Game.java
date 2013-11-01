import java.util.ArrayList;
import java.util.Scanner;

// main class, gets user input and calls functions
public class Game {
    
    public ArrayList<Monster> monsterList = new ArrayList<Monster>();
    public Map map = new Map();
    public MonsterCreator createMonster = new MonsterCreator();
    public Scanner input = new Scanner(System.in);
    public Monster monster = new Monster();
    Player player;
    
    public Game(String typeOfGame) {
        this.player = new Player(typeOfGame);
        initialize();
    }	

    // gets user input to call commands
    public void commands() {
        String userInput;
        while (true) {
            System.out.println("Enter a Command: ");
            userInput = input.next();
            
            String userInputLowerCase = userInput.toLowerCase();
            switch (userInputLowerCase) {
                // Doesn't actually do anything - partially implemented
                case "attack":
                    this.player.basicAttack(monster);
                    break;
                // Not implemented yet
                case "clear":	
                    clear();
                    break;
                // Not implemented yet                    
                case "go to": case "goto":
                    go_to();
                    break;
                case "help":
                    help();
                    break;
                // Doesn't work 
                case "locations":
                    System.out.println(Map.Locations.valueOf("Arena"));
                    break;
                case "monsters":
                    monsters(monsterList);
                    break;
                case "stats":		
                    stats(this.player);
                    break;
                case "quit":	
                    System.exit(0);
                    break;
                default:
                    System.out.println("I don't know what '" + userInput + "' means");
                    System.out.println("Type HELP for a list of commands.");
                    break;
            }
        }
    }
    
    public void initialize() {
        for (int i = 0; i < 1; i++) {
            createMonster.Generate(monsterList);
        }	
        
        // Opening dialog
        System.out.println("Hey... you alive?");
            System.out.println("*You let out a groan...*");
            System.out.println("Hey mate, you need to wake up. The guards will be coming around soon and they put a spear through the last guy they found still asleep.");
        System.out.println("*Slowly you sit up.*");
        System.out.println("That's the way! I'm Thorall, what's your name? ");
        String userInput = input.next();
        this.player.name = (userInput);
        System.out.println("Welcome to Silliya " + this.player.name + ".");
        
    }
    
    // COMMANDS
    // Command is being used for debugging at this time
    private void attackStats(Entity player, Entity monster) {
        System.out.println("----------------------------------");
        
        System.out.println("p_damage: " + this.player.damage);
        System.out.println("m_armour: " + monster.armour);
        
        System.out.println("mh_before: " + monster.health);
        // Replacing with new attack method
        // player.basicAttack(monster);
        System.out.println("mh_after: " + monster.health);
        
        System.out.println("----------------------------------");
        
        System.out.println("----------------------------------");
        
        System.out.println("m_damage: " + monster.damage);
        System.out.println("p_armour: " + this.player.armour);
        
        System.out.println("ph_before: " + this.player.health);
        // Replacing with new attack method
        // monster.attack(monster, player);
        System.out.println("ph_after: " + this.player.health);
        
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
        //System.out.println("- Clear:             Clears the screen.");
        //System.out.println("- Go to <Location>:  Takes you to a location.");
        System.out.println("- Help:              Gives you a list of commands.");
        //System.out.println("- Locations:         Gives you a list of Locations.");
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
        if (monsterList.isEmpty()) {
            System.out.println("- None");
        }
        else {
            for (Monster monster : monsterList) {
                System.out.println("- " + monster.name);
            }
        }
    }	
    
    private void stats(Player player) {
        System.out.println("\n--------------------------------------------------------------------\n");
        System.out.println("Name: " + this.player.name + "       ");
        System.out.println("Gold: " + this.player.gold + "       ");
        
        System.out.println("Health: " + this.player.health
                + " / " + this.player.healthMax);
        System.out.println("Damage: " + this.player.damage);
        
        // System.out.println("Location: " + player.getLocation + "      ");
        System.out.println("Backpack: ");
        
        if (this.player.backpack.isEmpty()) {
            System.out.println("- Empty");
        }
        else {
            for (Item item : this.player.backpack) {
                System.out.println("- " + item.getName());
            }
        }    System.out.println("\n--------------------------------------------------------------------");
    }  
    
}