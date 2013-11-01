package com.jadventure.game;

import java.util.Scanner;

public class JAdventure {

    public static void main(String[] args) {
        JSONReader json = new JSONReader();
        json.getMenu("Main");

        Scanner input = new Scanner(System.in);
        String userInput = input.next();
        new Menus().menuSwitch(userInput);
    }

}
