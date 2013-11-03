import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.HashMap;

public class JSONReader {
    
    Scanner input = new Scanner(System.in);
    
    public void getMenu(String menuName) {
        int i = 1;
        
        JSONParser parser = new JSONParser();
        String json = getJsonData("json/menus.json");
        try {
            Object jsonFile = parser.parse(json);
            JSONObject jsonObject = (JSONObject) jsonFile;
            JSONArray menu = (JSONArray) jsonObject.get(menuName);
            Iterator<String> iter = menu.iterator();
            
            while (iter.hasNext()){
                System.out.println("[" + i + "] " + iter.next());
                i++;
            }
            
        } catch (Exception e){
            System.out.println(e);
        }
        // This is wrong, needs to be changed to 
        // check for exit value in json object
	// it pulls.
        //if (input.nextInt() != 3) {
        //getSubMenus(menuName);
        //}
    }

    // End getMenus method
    // Begin subMenus Method
    // Currently working on subMenus and how to handle
    // input from the other method.
    public void getSubMenus(String topMenu){
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
    
    public String getJsonData(String fileName) {
        String line = null;
        String json = line;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                json = line;
            }
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println( "Unable to open file '" + fileName + "'");
        } catch(IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void writeJsonData(String filename, JSONObject object) {
        File file = new File(filename);
        try {
            file.getParentFile().mkdirs();
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println(object);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    public HashMap getProfileData(String playerName) {
        String jsonData = getJsonData("json/profiles/"+playerName+"/"+playerName+"_profile.json");
        try {
            JSONParser parser = new JSONParser();
            Object jsonFile = parser.parse(jsonData);
            JSONObject jsonObject = (JSONObject) jsonFile;
            return jsonObject;
        } catch (Exception e){
            System.out.println(e);
        }
        return new HashMap();
    }

    public void saveProfileData(final Player player) {
        JSONObject playerData = new JSONObject()
        {{
             put("name", player.name);
             put("healthMax", player.healthMax);
             put("armour", player.armour);
             put("damage", player.damage);
             put("level", player.level);
        }};
        String filename = "json/profiles/"+player.name+"/"+player.name+"_profile.json";
        writeJsonData(filename, playerData);
    }

}
