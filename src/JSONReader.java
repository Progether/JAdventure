import org.json.simple.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class JSONReader {

	JSONReader() { }
	
	 // Global scanner to be used by this class
	static Scanner input = new Scanner(System.in);
	
	// Begin getMenu method
	public static void getMenu(String menuName){
		System.out.println("getMenu");
		// Get top-level menu
		try {
			int i = 1;
		
			JSONParser parser = new JSONParser();
			Object jsonFile = parser.parse(new FileReader("json/menus.json"));
			JSONObject jsonObject = (JSONObject) jsonFile;
		
			JSONArray menu = (JSONArray) jsonObject.get(menuName);
			Iterator<String> iter = menu.iterator();
		
			while (iter.hasNext()){
				System.out.println("[" + i + "] " + iter.next());
				i++;
			}
		
		
			} catch (Exception e){
					System.out.println("wrong choice");
			}
		// This is wrong, needs to be changed to 
		// check for exit value in json object
		// it pulls.
		if (input.nextInt() != 3){
			getSubMenus(menuName);
		}
	}
	// End getMenus method
	
	// Begin subMenus Method
	// Currently working on subMenus and how to handle
	// input from the other method.
	public static void getSubMenus(String topMenu){
		System.out.println("getSubMenus");
		while(input.nextInt() != 0){
			try {
				int i = 1;
			
				JSONParser parser = new JSONParser();
				Object jsonFile = parser.parse(new FileReader("json/menus.json"));
				JSONObject jsonObject = (JSONObject) jsonFile;
			
				JSONArray menu = (JSONArray) jsonObject.get(topMenu);
				Iterator<Integer> iter = menu.iterator();
			
				while (iter.hasNext()){
					System.out.println("[" + i + "] " + iter.next());
					i++;
				}
			
			} catch (Exception e){
						System.out.println("Incorrect choice");
		    }	
		}
	}
	// End subMenus method
}
		
		