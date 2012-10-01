
public class Game {
	// Some instance variables?
	//	Player player;
	String playerName;
	boolean nameInput;
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
	
}
