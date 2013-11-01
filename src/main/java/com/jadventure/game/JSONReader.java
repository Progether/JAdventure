package com.jadventure.game;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

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

            @SuppressWarnings("unchecked")
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

                @SuppressWarnings("unchecked")
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
    
}
