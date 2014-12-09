package com.jadventure.game;

import com.jadventure.game.repository.ItemRepository;

/**
 * There is no Dependency Injection framework yet used / chosen, for creating Beans (runtime singletons / prototypes). 
 * So this class will contain those Beans.
 */
public final class GameBeans {

    public static ItemRepository getItemRepository() {
        return ItemRepository.createRepo();
    }

}
