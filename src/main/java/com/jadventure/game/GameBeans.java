package com.jadventure.game;

import com.jadventure.game.repository.CharacterRepository;
import com.jadventure.game.repository.ItemRepository;
import com.jadventure.game.repository.MonsterRepository;
import com.jadventure.game.repository.WorldRepository;
import com.jadventure.game.repository.PlayerRepository;

public final class GameBeans {
    
    public static ItemRepository getItemRepository() {
        return ItemRepository.createRepo();
    }
    
    public static WorldRepository getWorldRepository() {
        return WorldRepository.createRepo();
    }
    
    public static PlayerRepository getPlayerRepository() {
        return PlayerRepository.createRepo();
    }

	public static CharacterRepository getCharacterRepository() {
		return CharacterRepository.createRepo();
	}

	public static MonsterRepository getMonsterRepository() {
		return MonsterRepository.createRepo();
	}

}
