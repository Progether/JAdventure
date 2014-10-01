 package com.jadventure.game.menus;

 import com.jadventure.game.entities.Player;
 import com.jadventure.game.monsters.Monster;
 import com.jadventure.game.QueueProvider;

 public class BattleMenu extends Menus {

	 private Monster oponent;
	 private Player player;

	 public BattleMenu(Monster oponent, Player player) {
		 this.oponent = oponent;
		 this.player = player;
		 this.menuItems.add(new MenuItem("Attack", "Attack " + oponent.monsterType + "."));
		 this.menuItems.add(new MenuItem("Defend", "Defend against " + oponent.monsterType + "'s attack."));
		 this.menuItems.add(new MenuItem("Use", "Use item"));

		 while (true) {
			 QueueProvider.offer("What is your choice?");
			 MenuItem selectedItem = displayMenu(this.menuItems);
			 if (isSuccessful(selectedItem)) {

			 } 		 
		 }
	 }

	 private boolean isSuccessful(MenuItem m) {
		 String key = m.getKey();
		 switch (m.getKey()) {
			 case "attack": {
				return attack();
			 }
			 case "defend": {
				return defend();
			 }
			 case "use": {
				return use();
			 }
			 default: {
				return false;
			 }
		 }
	 }

	 private boolean attack() {
		 return true;
	 }

	 private boolean defend() {
		 return true;
	 }

	 private boolean use() {
		 new UseMenu(player.getStorage().getItems(), player);
		 return true;
	 }
 }

