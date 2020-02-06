import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class GameObjectTest {

    private GameObjectTester gameObjectTest;

    @Before
    public void init() {
        gameObjectTest = new GameObjectTester(new Vector2(0, 0),
                new Texture("../core/assets/fireTruck1.png"), new Vector2(50, 50));
    }

    @Test
    public void initShouldInstantiateCorrectly() {
        assertFalse(gameObjectTest.isRemove());
    }

    @Test
    public void initShouldSetCorrectDimensions() {
        assertEquals(new Vector2(25, 25), gameObjectTest.getCentre());
    }
}


class GameObjectTester extends GameObject {

    public GameObjectTester(Vector2 spawnPos, Texture image, Vector2 imSize) {
        super(spawnPos, image, imSize);
    }

    @Override
    public void update() {

    }
}