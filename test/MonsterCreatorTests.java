import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class MonsterCreatorTests {

    MonsterCreator monsterCreator;
    ArrayList<Monster> monsterArrayList;

    @Before
    public void setUp() {
        monsterCreator = new MonsterCreator();
        monsterArrayList = new ArrayList<Monster>();
        monsterCreator.Generate(monsterArrayList);
    }

    @After
    public void tearDown() {
        monsterCreator = null;
    }

    @Test
    public void testGenerate() {
        assertEquals(monsterArrayList.isEmpty(), false);
        assertEquals(monsterArrayList.size(), 1);
    }

    @Test
    public void testGenerateName() {
        assertThat(monsterArrayList.get(0).getName(), instanceOf(String.class));
    }

    @Test
    public void testGenerateDamage() {
        assertThat(monsterArrayList.get(0).getDamage(), instanceOf(double.class));
    }

    @Test
    public void testGenerateHealth() {
        assertThat(monsterArrayList.get(0).getHealthMax(), instanceOf(int.class));
    }

    @Test
    public void testGenerateArmour() {
        assertThat(monsterArrayList.get(0).getArmour(), instanceOf(int.class));
    }
}
