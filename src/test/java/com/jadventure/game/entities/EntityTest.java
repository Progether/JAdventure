package com.jadventure.game.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jadventure.game.GameBeans;
import com.jadventure.game.items.Item;
import com.jadventure.game.items.Storage;
import com.jadventure.game.repository.ItemRepository;

public class EntityTest {
    private Entity entity;

    @Before
    public void setUp() {
        entity = new Player();
    }

    @After
    public void destroy() {
        entity = null;
    }

    @Test
    public void testCreation() {
        assertTrue(entity instanceof Entity);
    }

    @Test
    public void testType() {
        testInt(entity.getHealthMax());
        testInt(entity.getHealth());
        Object test = entity.getName();
        assertTrue(test instanceof String);
        testInt(entity.getLevel());
        testInt(entity.getStrength());
        testInt(entity.getIntelligence());
        testInt(entity.getDexterity());
        testInt(entity.getLuck());
        testInt(entity.getStealth());
        testInt(entity.getGold());
        test = entity.getDamage();
        assertTrue(test instanceof Double);
        test = entity.getWeapon();
        assertTrue(test instanceof String);
        test = entity.getEquipment();
        assertTrue(test instanceof Map);
    }

    @Test
    public void testSetters() {
        entity.setHealth(50);
        assertEquals(entity.getHealth(), 50);
        assertTrue(entity.getHealthMax() >= entity.getHealth());
        entity.setGold(10);
        assertEquals(entity.getGold(), 10);
        entity.setArmour(20);
        assertEquals(entity.getArmour(), 20);
        entity.setHealthMax(30);
        assertEquals(entity.getHealthMax(), 30);
        assertTrue(entity.getHealth() <= entity.getHealthMax());
        entity.setLevel(3);
        assertEquals(entity.getLevel(), 3);
    }

    @Test
    public void testStorage() {
        String id = "pmil1";
        String type = "food-liquid";
        String name = "milk"; 
        String description = "";
        Item item = new Item(id, type, name, description, 1, null);
        entity.setStorage(new Storage(300));
        entity.addItemToStorage(item);
        assertEquals(entity.getStorage().getItems().get(0), item);
    }

    @Test
    public void testEquipItem_OneHanded() {
        ItemRepository itemRepo = GameBeans.getItemRepository();
        double oldDamage = entity.getDamage();
        Item item = itemRepo.getItem("wshi1");
        Map<String, String> result = entity.equipItem(item.getPosition(), item);
        assertFalse(result.get("damage") == null);
        double newDamage = entity.getDamage();
        double diffDamage = Double.parseDouble(result.get("damage"));

        assertTrue("wshi1".equals(entity.getWeapon()));
        assertEquals(diffDamage, newDamage - oldDamage, 0.2);

        Map<EquipmentLocation, Item> equipment = entity.getEquipment();
        assertEquals(item, equipment.get(EquipmentLocation.RIGHT_HAND));
    }

    @Test
    public void testUnequipItem_OneHand() {
        ItemRepository itemRepo = GameBeans.getItemRepository();
        double oldDamage = entity.getDamage();
        Item item = itemRepo.getItem("wshi1");
        Map<String, String> result = entity.unequipItem(item);
        assertFalse(result.get("damage") == null);
        double newDamage = entity.getDamage();
        double diffDamage = Double.parseDouble(result.get("damage"));

        assertTrue("hands".equals(entity.getWeapon()));
        assertEquals(diffDamage, newDamage - oldDamage, 0.2);

        Map<EquipmentLocation, Item> equipment = entity.getEquipment();
        assertEquals(null, equipment.get(EquipmentLocation.RIGHT_HAND));
    }

    @Test
    public void testEquipItem_TwoHanded() {
        ItemRepository itemRepo = GameBeans.getItemRepository();
        double oldDamage = entity.getDamage();
        Item item = itemRepo.getItem("wbrd1");
        Map<String, String> result = entity.equipItem(item.getPosition(), item);
        assertFalse(result.get("damage") == null);
        double newDamage = entity.getDamage();
        double diffDamage = Double.parseDouble(result.get("damage"));

        assertTrue("wbrd1".equals(entity.getWeapon()));
        assertEquals(diffDamage, newDamage - oldDamage, 0.2);

        Item empty = itemRepo.getItem("empty");
        Map<EquipmentLocation, Item> equipment = entity.getEquipment();
        assertEquals(item, equipment.get(EquipmentLocation.BOTH_HANDS));
        Item leftHand = equipment.get(EquipmentLocation.LEFT_HAND);
        Item rightHand = equipment.get(EquipmentLocation.RIGHT_HAND);
        assertTrue(leftHand == null || empty.equals(leftHand));
        assertTrue(rightHand == null || empty.equals(rightHand));
    }

    @Test
    public void testUnequipItem_TwoHanded() {
        ItemRepository itemRepo = GameBeans.getItemRepository();
        double oldDamage = entity.getDamage();
        Item item = itemRepo.getItem("wbrd1");
        Map<String, String> result = entity.unequipItem(item);
        assertFalse(result.get("damage") == null);
        double newDamage = entity.getDamage();
        double diffDamage = Double.parseDouble(result.get("damage"));

        assertTrue("hands".equals(entity.getWeapon()));
        assertEquals(diffDamage, newDamage - oldDamage, 0.2);

        Item empty = itemRepo.getItem("empty");
        Map<EquipmentLocation, Item> equipment = entity.getEquipment();
        Item bothHands = equipment.get(EquipmentLocation.BOTH_HANDS);
        assertTrue(bothHands == null || empty.equals(bothHands));
    }

    @Test
    public void testEquipItem_OneHandedWithTwoHandedEquip() {
        ItemRepository itemRepo = GameBeans.getItemRepository();
        entity.equipItem(itemRepo.getItem("wbrd1").getPosition(), itemRepo.getItem("wbrd1"));
        double oldDamage = entity.getDamage();
        Item item = itemRepo.getItem("wshi1");
        Map<String, String> result = entity.equipItem(item.getPosition(), item);
        assertFalse(result.get("damage") == null);
        double newDamage = entity.getDamage();
        double diffDamage = Double.parseDouble(result.get("damage"));

        assertTrue("wshi1".equals(entity.getWeapon()));
        assertEquals(diffDamage, newDamage - oldDamage, 0.2);

        Map<EquipmentLocation, Item> equipment = entity.getEquipment();
        assertEquals(item, equipment.get(EquipmentLocation.RIGHT_HAND));
        Item empty = itemRepo.getItem("empty");

        Item bothHands = equipment.get(EquipmentLocation.BOTH_HANDS);
        assertTrue(bothHands == null || empty.equals(bothHands));
    }

    private void testInt(Object test) {
        assertTrue(test instanceof Integer);
    }
}
