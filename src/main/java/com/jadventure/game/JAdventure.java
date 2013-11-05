package com.jadventure.game;

import java.util.Scanner;

public class JAdventure implements MenuListener {
    public static void main(String[] args) {
        new JAdventure().render();
    }

    public JAdventure() {
    }

    public void render() {
        Menu mainMenu = Menu.loadFromFile("json/mainmenu.json");
        mainMenu.setMenuListener(this);
        mainMenu.render();
    }

    @Override
    public void menuSelected(Menu menu) {
        Scanner input = new Scanner(System.in);

        String key = menu.getKey();
        if(key.equals("start")) {
            Game game = new Game();
            game.commands();
        }
        else if(key.equals("exit")) {
            System.out.println("Goodbye!");
            System.exit(0);
        }
        else if(key.equals("load")) {
            System.out.println("What is the name of the avatar you want to load?");
            Player player = null;

            while (player == null) {
                key = input.next();
                if (Player.profileExists(key)) {
                    player = Player.load(key);
                } else {
                    System.out.println("That user doesn't exist. Try again.");
                }
            }

            Game game = new Game(player);
            game.commands();
        }
    }
}
