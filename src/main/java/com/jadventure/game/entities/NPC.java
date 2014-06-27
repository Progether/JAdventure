package com.jadventure.game.entities;

/**
 * This class deals with Non Player Character (npc) and all of their properties.
 * Any method that changes a npc or a player  interacts with it should
 * be placed within this class. If a method deals with entities in general or
 * with variables not unique to the npc, place it in the entity class.
 */
public class NPC extends Entity {
    
    public NPC(String entityID){
        setName(entityID);
    }

}
