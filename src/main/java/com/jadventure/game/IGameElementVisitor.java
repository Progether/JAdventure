package com.jadventure.game;

import com.jadventure.game.entities.Player;
import com.jadventure.game.items.Item;
import com.jadventure.game.items.Storage;
import com.jadventure.game.navigation.ILocation;

public interface IGameElementVisitor {

	void visit(Item item);
	
	void visit(ILocation location);

	void visit(Storage storage);

	void visit(Player player);
	
}
