package com.jadventure.game.menus;

import java.util.HashMap;
import java.util.Map;

import com.jadventure.game.Game;
import com.jadventure.game.GameBeans;
import com.jadventure.game.QueueProvider;
import com.jadventure.game.entities.Player;

/**
 * Called when creating a new Player
 */
public class ChooseClassMenu extends Menus {
	private static final String MENU_KEY_SEWER_RAT = "Sewer Rat";
	private static final String MENU_KEY_RECRUIT = "Recruit";
	private static Map<String, String> characterMap = new HashMap<String, String>();
	
	static {
		characterMap.put(MENU_KEY_RECRUIT, "recruit");
		characterMap.put(MENU_KEY_SEWER_RAT, "sewer-rat");
	}

    public ChooseClassMenu() {
        this.menuItems.add(new MenuItem(MENU_KEY_RECRUIT, "A soldier newly enlisted to guard the city of Silliya"));
        this.menuItems.add(new MenuItem(MENU_KEY_SEWER_RAT, "A member of the underground of Silliya"));

        while(true) {
            QueueProvider.offer("Choose a class to get started with:");
            MenuItem selectedItem = displayMenu(this.menuItems);
            if(testOption(selectedItem)) {
            	break;
            }
        }
    }

    private static boolean testOption(MenuItem menuItem) {
        String menuCommand = menuItem.getCommand();
        if (characterMap.containsKey(menuCommand)) {
            Player player = GameBeans.getPlayerRepository().create(characterMap.get(menuCommand));
            new Game(player, "new");
            return true;
        }
        return false;
    }

}
