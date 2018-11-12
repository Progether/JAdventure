package com.jadventure.game.conversation;

import com.jadventure.game.entities.EquipmentLocation;
import com.jadventure.game.entities.NPC;
import com.jadventure.game.entities.Player;
import com.jadventure.game.items.Item;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ConversationManagerTest {
    ConversationManager cm;

    @Before
    public void setUp() {
        cm = ConversationManager.getInstance();
    }

    @Test 
    public void testCreation() {
        assertNotNull(cm);
        assertTrue(cm instanceof ConversationManager);
    }

    @Test
    public void matchConditionsShouldMatchAlly() {
        NPC npc = new NPC();
        npc.setAllies(Collections.singletonList("Recruit"));
        Player player = new Player();
        player.setCurrentCharacterType("Recruit");
        Line line = new Line(1,
                "",
                "",
                ConditionType.ALLY,
                "",
                Collections.<Integer>emptyList(),
                ActionType.NONE
        );
        assertTrue(ConversationManager.matchesConditions(npc, player, line));
    }

    @Test
    public void matchConditionsShouldMatchEnemy() {
        NPC npc = new NPC();
        npc.setEnemies(Collections.singletonList("Recruit"));
        Player player = new Player();
        player.setCurrentCharacterType("Recruit");
        Line line = new Line(1,
                "",
                "",
                ConditionType.ENEMY,
                "",
                Collections.<Integer>emptyList(),
                ActionType.NONE
        );
        assertTrue(ConversationManager.matchesConditions(npc, player, line));
    }

    @Test
    public void matchConditionsShouldNotMatchAlly() {
        NPC npc = new NPC();
        npc.setAllies(Collections.singletonList("Syndicate Member"));
        Player player = new Player();
        player.setCurrentCharacterType("Recruit");
        Line line = new Line(1,
                "",
                "",
                ConditionType.ALLY,
                "",
                Collections.<Integer>emptyList(),
                ActionType.NONE
        );
        assertFalse(ConversationManager.matchesConditions(npc, player, line));
    }

    @Test
    public void matchConditionsShouldNotMatchEnemy() {
        NPC npc = new NPC();
        npc.setEnemies(Collections.singletonList("Syndicate Member"));
        Player player = new Player();
        player.setCurrentCharacterType("Recruit");
        Line line = new Line(1,
                "",
                "",
                ConditionType.ENEMY,
                "",
                Collections.<Integer>emptyList(),
                ActionType.NONE
        );
        assertFalse(ConversationManager.matchesConditions(npc, player, line));
    }

    @Test
    public void matchConditionShouldMatchLevel() {
        Player player = new Player();
        player.setLevel(3);
        Line line = new Line(1,
                "",
                "",
                ConditionType.LEVEL,
                "2",
                Collections.<Integer>emptyList(),
                ActionType.NONE
        );
        assertTrue(ConversationManager.matchesConditions(null, player, line));
    }

    @Test
    public void matchConditionShouldNotMatchLevel() {
        Player player = new Player();
        player.setLevel(1);
        Line line = new Line(1,
                "",
                "",
                ConditionType.LEVEL,
                "2",
                Collections.<Integer>emptyList(),
                ActionType.NONE
        );
        assertFalse(ConversationManager.matchesConditions(null, player, line));
    }

    @Test
    public void matchConditionShouldMatchItem() {
        Player player = new Player();

        Map<String, Integer> properties = new HashMap<>();
        properties.put("damage", 5);
        properties.put("value", 50);
        properties.put("weight", 4);


        Item item = new Item("wspe1",
                "weapon",
                "spear",
                "a basic spear",
                EquipmentLocation.RIGHT_HAND,
                1,
                Collections.unmodifiableMap(properties)
        );
        Line line = new Line(1,
                "",
                "",
                ConditionType.ITEM,
                "wspe1",
                Collections.<Integer>emptyList(),
                ActionType.NONE
        );

        player.addItemToStorage(item);
        assertTrue(ConversationManager.matchesConditions(null, player, line));

    }



   @Test
    public void matchConditionShouldNotMatchItem() {
       Player player = new Player();

       Map<String, Integer> properties = new HashMap<>();
       properties.put("damage", 5);
       properties.put("value", 50);
       properties.put("weight", 4);


       Item item = new Item("wspe1",
               "weapon",
               "spear",
               "a basic spear",
               EquipmentLocation.RIGHT_HAND,
               1,
               Collections.unmodifiableMap(properties)
       );
       Line line = new Line(1,
               "",
               "",
               ConditionType.ITEM,
               "hands",
               Collections.<Integer>emptyList(),
               ActionType.NONE
       );

       player.addItemToStorage(item);
       assertFalse(ConversationManager.matchesConditions(null, player, line));

    }

    @Test
    public void matchConditionShouldMatchCharType() {
        Player player = new Player();
        player.setCurrentCharacterType("Recruit");
        Line line = new Line(1,
                "",
                "",
                ConditionType.CHAR_TYPE,
                "Recruit",
                Collections.<Integer>emptyList(),
                ActionType.NONE
        );
        assertTrue(ConversationManager.matchesConditions(null, player, line));

    }

    @Test
    public void matchConditionShouldMatchNotCharType() {
        Player player = new Player();
        player.setCurrentCharacterType("Recruit");
        Line line = new Line(1,
                "",
                "",
                ConditionType.CHAR_TYPE,
                "Brotherhood Member",
                Collections.<Integer>emptyList(),
                ActionType.NONE
        );
        assertFalse(ConversationManager.matchesConditions(null, player, line));
    }
}
