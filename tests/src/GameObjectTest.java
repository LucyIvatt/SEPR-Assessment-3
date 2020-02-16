import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;


//Mostly covered ~ calculate coverage (did not do all getters and setters since most are standard)
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

    /**
     * Test ID: GameObject_1.1
     *
     * Input: N/A
     * Expected Output: Correct initialized variables
     */
    //Testing that the GameObject Constructor is working as intended with a standard input
    @Test
    public void initGameObjectShouldInstantiateCorrectly() {
        assertFalse(gameObjectTest.isRemove());
    }

    /**
     * Test ID: GameObject_1.2
     *
     * Input: N/A
     * Expected Output: The 'middle' between the position vector and the top-right corner (calculated by width and
     * height)
     */
    //Testing that when the GameObject is at location (0,0) .getCentre() calculates the correct centre
    @Test
    public void getCentreShouldReturnCentreWithZerosAsPosition() {
        assertEquals(new Vector2(25, 25), gameObjectTest.getCentre());
    }

    /**
     * Test ID: GameObject_1.3
     *
     * Input: New position vector
     * Expected Output: The 'middle' between the new position vector and the top-right corner (calculated by width and
     * height)
     */
    //Testing that wen GameObject is at any 'middle' location .getCentre() calculates the correct centre
    @Test
    public void getCentreShouldReturnCentreAtStandardPosition() {
        //setting position to simulate the wanted conditions
        gameObjectTest.changePosition(new Vector2(50, 50));

        assertEquals(new Vector2(75, 75), gameObjectTest.getCentre());
    }

    /**
     * Test ID: GameObject_1.4
     *
     * Input: N/A
     * Expected Output: The centre calculated when dimensions (width / height) are (0,0)
     */
    //Testing that when the dimensions are (0, 0) .getCentre() calculates the correct centre
    @Test
    public void getCentreShouldReturnCentreWithZeroSize() {
        assertEquals(new Vector2(0, 0), gameObjectTestZeros.getCentre());
    }

    //Testing that .setPosition() accepts standard values and changes accordingly
    @Test
    public void setPositionShouldChangeGameObjectsPositionStandard() {
        //Setting gameObjectTest to a new position at (500,500)
        gameObjectTest.setPosition(new Vector2(500, 500));

        assertEquals(new Vector2(500, 500), gameObjectTest.getPosition());
    }

    //Testing that .setPosition() accepts negative values and changes accordingly
    @Test
    public void setPositionShouldSetToNegativePosition() {
        //Setting gameObjectTest to new position at (-10, -10)
        gameObjectTest.setPosition(new Vector2(-10, -10));

        assertEquals(new Vector2(-10, -10), gameObjectTest.getPosition());
    }

    //Testing that .changePosition() changes correctly with standard values (.changePosition changes current position by
    // vector v)
    @Test
    public void changePositionShouldShiftStandardVector() {
        //Setting position to better location than (0,0)
        gameObjectTest.setPosition(new Vector2(50, 50));

        //The changing factor that the position should shift by
        gameObjectTest.changePosition(new Vector2(100, 100));

        assertEquals(new Vector2(150, 150), gameObjectTest.getPosition());
    }

    //Testing that .changePosition() changes correctly with an all negative vector input
    @Test
    public void changePositionShouldShiftWithNegativeVector() {
        //Setting position to better location than (0,0)
        gameObjectTest.setPosition(new Vector2(50, 50));

        //The changing factor that the position should shift by
        gameObjectTest.changePosition(new Vector2(-25, -25));

        assertEquals(new Vector2(25, 25), gameObjectTest.getPosition());
    }

    //Testing that .changePosition() changes correctly with one part of the vector being negative
    @Test
    public void changePositionShouldShiftWithOneNegativeNumberVector() {
        //Setting position to better location than (0,0)
        gameObjectTest.setPosition(new Vector2(50, 50));

        //The changing factor that the position should shift by
        gameObjectTest.changePosition(new Vector2(25, -25));

        assertEquals(new Vector2(75, 25), gameObjectTest.getPosition());
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