package com.jadventure.game.entities;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.jadventure.game.items.Item;
import com.jadventure.game.items.ItemStack;
import com.jadventure.game.items.Storage;

@RunWith(JUnit4.class)
public class EntityTest {

    private Entity entity;

    @Before
    public void setUp() {
        entity = new Player();
    }

    @After
    public void tearDown() {
        entity = null;
    }

    @Test
    public void testCreation() {
        assertThat(entity, instanceOf(Entity.class));
    }

    @Test
    public void testReturnValues() {
        assertThat(entity.getHealth(), instanceOf(int.class));
        assertThat(entity.getArmour(), instanceOf(int.class));
        assertThat(entity.getStorage(), instanceOf(Storage.class));
        assertThat(entity.getDamage(), instanceOf(double.class));
        assertThat(entity.getGold(), instanceOf(int.class));
        assertThat(entity.getName(), instanceOf(String.class));
        assertThat(entity.getLevel(), instanceOf(int.class));
    }

    @Test
    public void testSetters() {
        entity.setHealthMax(50);
        assertEquals(entity.getHealthMax(), 50);
        assertTrue(entity.getHealth() <= entity.getHealthMax());
    }

    @Test
    public void testStorage() {
        String id = "fram1";
        String name = "raw meat";
        String description = "some delicious raw meat";
        Map<String, Integer> properties = new HashMap<>();
        properties.put("health", 2);
        properties.put("weight", 2);
        properties.put("value", 5);
        
        Item item = new Item(id, name, description, properties);

        List<ItemStack> testArrayList = new ArrayList<>();
        testArrayList.add(new ItemStack(1, item));

        entity.addItemToStorage(item);
        assertEquals(entity.getStorage().getItems().get(0).getItem(), item);
    }

}
