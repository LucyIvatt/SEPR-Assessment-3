

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.entities.Fortress;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;



public class FortressTest {

    Fortress testFortress = new Fortress(new Vector2(100, 100), new Texture("fireTruck1.png"),
            new Texture("fireTruck2.png"), new Vector2(50, 50));

    @Test
    public void testTest() {
        testFortress.setPosition(new Vector2(200, 200));
        assertEquals(new Vector2(200, 200), testFortress.getPosition());

    }
}
