import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void newPlayer() {
        Player player = new Player("new");
        int expected = 1;
        int actual = player.armour;
        assertEquals("Failure - new player not properly created", expected, actual);
    }

    @Test
    public void oldPlayer() {
        Player player = new Player("test");
        String expected = "test";
        String actual = player.name;
        assertEquals("Failure - old player not properly loaded", expected, actual);
    }

}
