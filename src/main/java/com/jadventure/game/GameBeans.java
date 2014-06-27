package com.jadventure.game;

import com.jadventure.game.repository.ItemRepository;
import com.jadventure.game.repository.LocationRepository;
import com.jadventure.game.repository.PlayerRepository;

public final class GameBeans {
    
    public static ItemRepository getItemRepository() {
        return ItemRepository.createRepo();
    }
    
    public static LocationRepository getLocationRepository() {
        return LocationRepository.createRepo();
    }
    
    public static PlayerRepository getPlayerRepository() {
        return PlayerRepository.createRepo();
    }

}
