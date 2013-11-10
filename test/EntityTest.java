import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class EntityTest {

    private Entity entity;

    @Before
    public void setUp() {
        entity = new Entity();
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
        assertThat(entity.getHealthCurrent(), instanceOf(int.class));
        assertThat(entity.getArmour(), instanceOf(int.class));
        assertThat(entity.getBackpack(), instanceOf(ArrayList.class));
        assertThat(entity.getDamage(), instanceOf(double.class));
        assertThat(entity.getGold(), instanceOf(int.class));
        assertThat(entity.getName(), instanceOf(String.class));
        assertThat(entity.getLevel(), instanceOf(int.class));
    }

    @Test
    public void testSetters() {
        entity.setHealthMax(50);
        assertEquals(entity.getHealthMax(), 50);
        assertTrue(entity.getHealthCurrent() <= entity.getHealthMax());
    }

    @Test
    public void testBackpack() {
        Item testItem = new Item(1);

        ArrayList<Item> testArrayList = new ArrayList<Item>();
        testArrayList.add(testItem);

        entity.addBackpack(testItem);
        assertEquals(entity.getBackpack(), testArrayList);

    }
}