package com.jadventure.game;

import com.jadventure.game.entities.Player;
import com.jadventure.game.navigation.Coordinate;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.repository.PlayerRepository;
import com.jadventure.game.repository.WorldRepository;

public class World {
	private WorldRepository worldRepo = GameBeans.getWorldRepository();
	private PlayerRepository playerRepo = GameBeans.getPlayerRepository();


	public ILocation getLocation(Coordinate coordinate) {
		return worldRepo.getLocation(coordinate);
	}

	public Player getPlayer() {
		return playerRepo.getPlayer();
	}

	public void getNonPlayerCharacters(Coordinate coordinate) {
		throw new RuntimeException("Not implemented yet.");
	}

	public void getMonsters(Coordinate coordinate) {
		throw new RuntimeException("Not implemented yet.");
	}

}
