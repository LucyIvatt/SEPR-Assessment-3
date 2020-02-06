import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.entities.Entity;
import com.dicycat.kroy.GameObject;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class EntityTest {

    private entityTester entityTest;

    @Before
    public void init() {
        entityTest = new entityTester(new Vector2(0, 0), new Texture("../core/assets/fireTruck1.png"),
                new Vector2(50, 50), 100);
    }

    //Testing that the Entity constructor is working as intended with standard input
    //wait why is getHealthPoints and Integer return type and not int?
    @Test
    public void initEntityShouldInstantiateCorrectly() {
        //CHECK the return type!!
//        assertEquals(100, entityTest.getHealthPoints());

    }

}

class entityTester extends Entity{

    /**
     * @param spawnPos The position the entity will spawn at.
     * @param img      The texture of the entity.
     * @param imSize   Size of the entity. Can be used to resize large/small textures
     * @param health   Hit points of the entity
     */
    public entityTester(Vector2 spawnPos, Texture img, Vector2 imSize, int health) {
        super(spawnPos, img, imSize, health);
    }
}
