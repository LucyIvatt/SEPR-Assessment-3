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
    private GameObjectTester gameObjectTestZeros;

    @Before
    public void init() {
        //standard init of GameObject at (0,0) position, a image of a firetruck, with size (50,50)
        gameObjectTest = new GameObjectTester(new Vector2(0, 0),
                new Texture("../core/assets/fireTruck1.png"), new Vector2(50, 50));

        //No size init of GameObject at (50,50) position, a image of a firetruck, with size (0,0)
        gameObjectTestZeros = new GameObjectTester(new Vector2(50, 50),
                new Texture("../core/assets/fireTruck1.png"), new Vector2(0, 0));
    }

    //Testing that the GameObject Constructor is working as intended with a standard input
    @Test
    public void initShouldInstantiateCorrectly() {
        assertFalse(gameObjectTest.isRemove());
    }

    //Testing that when the GameObject is at location (0,0) .getCentre() calculates the correct centre
    @Test
    public void getCentreShouldReturnCentreWithZerosAsPosition() {
        assertEquals(new Vector2(25, 25), gameObjectTest.getCentre());
    }

    //Testing that wen GameObject is at any 'middle' location .getCentre() calculates the correct centre
    @Test
    public void getCentreShouldReturnCentreAtStandardPosition() {
        //setting position to simulate the wanted conditions
        gameObjectTest.changePosition(new Vector2(50, 50));

        assertEquals(new Vector2(75, 75), gameObjectTest.getCentre());
    }

    //Testing that when the dimensions are (0, 0) .getCentre() calculates the correct centre
    @Test
    public void getCentreShouldReturnCentreWithZeroSize() {
        assertEquals(new Vector2(0, 0), gameObjectTestZeros.getCentre());
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