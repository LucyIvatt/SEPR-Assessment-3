import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.bullets.BulletDispenser;
import com.dicycat.kroy.bullets.Pattern;
import com.dicycat.kroy.entities.Entity;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//Limited testing due to 'Kroy.mainGameScreen. ...' error, find the link in the Assessment 3 zip to our GitHubs altered
//Unit tests.
@RunWith(GdxTestRunner.class)
public class BulletDispenserTest {

    private BulletDispenser testBulletDispenser;

    //The BulletDispenser needs to be passed the 'owner' of the bullet
    private entityTester creatorEntity;


    @Before
    public void init() {
        //creating the instance of the Entity which will be the bullet owner
        creatorEntity = new entityTester(new Vector2(0, 0), new Texture("../core/assets/fireTruck1.png"),
                new Vector2(50, 50), 100, 10);

        //creating the instance of BulletDispenser to test on
        testBulletDispenser = new BulletDispenser(creatorEntity);

    }

    /**
     * Test ID: BulletDispenser_1.1
     *
     * Input: N/A
     * Expected Output: Null
     */
    //Testing that null is returned when updating with no patterns in the List
    @Test
    public void updateShouldReturnNullWithNoPatterns() {
        assertNull(testBulletDispenser.update(true));
    }

    /**
     * BulletDispenser_1.2
     *
     * Input: A mocked Pattern class
     * Expected output: The time between firing patterns - 'PatternTime'
     */
    //Testing that when passed a pattern and with one pattern in the List, the PatternTime is set to the correct value
    @Test
    public void addPatternShouldSetTheCorrectValuesIfSizeIsOne() {
        Pattern mockPattern = mock(Pattern.class);
        when(mockPattern.getCooldown()).thenReturn(1f);

        testBulletDispenser.addPattern(mockPattern);
        assertEquals(1f, testBulletDispenser.getPatternTime(), 0.0f);
    }

    /**
     * BulletDispenser_1.3
     *
     * Input: A mocked Pattern class
     * Expected output: The same mocked Pattern Class as inputted - 'FiringPattern'
     */
    //Testing that the pattern passed is added and that the correct FiringPattern
    @Test
    public void addPatternShouldSetTheCorrectPatternIfSizeIsOne() {
        Pattern mockPattern = mock(Pattern.class);

        testBulletDispenser.addPattern(mockPattern);
        assertEquals(mockPattern, testBulletDispenser.getFiringPattern());
    }

    /**
     * BulletDispenser_1.4
     *
     * Input: Two mocked Pattern classes
     * Expected Output: The 'PatternTime' from the first passed Pattern class
     */
    //Testing that the if a second pattern was added the 'patternTime' do not change
    @Test
    public void addPatternShouldNotChangePatternTimeValueWhenSecondPatternIsAdded() {
        Pattern mockPatternOne = mock(Pattern.class);
        Pattern mockPatternTwo = mock(Pattern.class);

        when(mockPatternOne.getCooldown()).thenReturn(1f);
        when(mockPatternTwo.getCooldown()).thenReturn(2f);

        testBulletDispenser.addPattern(mockPatternOne);
        testBulletDispenser.addPattern(mockPatternTwo);

        assertEquals(1f, testBulletDispenser.getPatternTime(), 0.0f);
    }

    /**
     * BulletDispenser_1.5
     *
     * Input: Two mocked Pattern classes
     * Expected Output: The first passed Pattern class - 'FiringPattern'
     */
    //Testing that the if a second pattern was added the 'firingPattern' do not change
    @Test
    public void addPatternShouldNotChangeFiringPatternValueWhenSecondPatternIsAdded() {
        Pattern mockPatternOne = mock(Pattern.class);
        Pattern mockPatternTwo = mock(Pattern.class);

        testBulletDispenser.addPattern(mockPatternOne);
        testBulletDispenser.addPattern(mockPatternTwo);

        assertEquals(mockPatternOne, testBulletDispenser.getFiringPattern());
    }


}
