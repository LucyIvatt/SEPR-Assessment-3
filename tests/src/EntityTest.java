import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.entities.Entity;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.entities.FireTruck;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class EntityTest {

    private entityTester entityTest;

    private FireTruck fireTruckTest;
    private Float fireTruckStats[] = new Float[] {100f, 1f, 50f, 5f};

    @Before
    public void init() {
        //standard init of Entity at (1,2) position, image of a firetruck, size (50,50), health 100 and radius 10
        entityTest = new entityTester(new Vector2(1, 2), new Texture("../core/assets/fireTruck1.png"),
                new Vector2(50, 50), 100, 10);


//        fireTruckTest = new FireTruck(new Vector2(55, 55), fireTruckStats, 1);
    }


    /**
     * Test ID: Entity_1.1
     *
     * Input: N/A
     * Expected Output: The correct initialized variables
     */
    //Testing that the Entity constructor is working as intended with standard input
    @Test
    public void initEntityShouldInstantiateCorrectly() {
        //Test that isAlive returns true
        assertTrue(entityTest.isAlive());
        //Test that getHealthPoints returns the passed variable when instantiated
        assertEquals(100, entityTest.getHealthPoints());
        //Test that getGetPosition returns the passed variable when instantiated
        assertEquals(new Vector2(1, 2), entityTest.getPosition());
    }

    /**
     * Test ID: Entity_1.2
     *
     * Input: int value into .applyDamage
     * Expected Output: Entities health minus the input value
     */
    //Testing that applyDamage removes the amount of health with standard value - with not killing
    @Test
    public void applyDamageShouldApplyGivenDamageAndNotKill() {
        entityTest.applyDamage(50);

        assertEquals(50, entityTest.getHealthPoints());
    }

    /**
     * Test ID: Entity_1.3
     *
     * Input: int value into .applyDamage greater than Entities max health
     * Expected Output: Entities health minus the input value - negative value
     */
    //Testing that applyDamage removes the amount of health with standard value - does kill - negative health
    @Test
    public void applyDamageShouldApplyGivenDamageAndKillWithNegativeHealth() {
        entityTest.applyDamage(110);

        assertEquals(-10, entityTest.getHealthPoints());
    }

    /**
     * Test ID: Entity_1.4
     *
     * Input: int value into .applyDamage equal to the Entities max health
     * Expected Output: Zero
     */
    //Testing that applyDamage removes the amount of health with standard value - does kill - at zero health
    @Test
    public void applyDamageShouldApplyDamageGivenAndKillWithZeroHealth() {
        entityTest.applyDamage(100);

        assertEquals(0, entityTest.getHealthPoints());
    }

    /**
     * Test ID: Entity_1.5
     *
     * Input: int value into .applyDamage that is negative
     * Expected Output: An IllegalArgumentException to be thrown
     */
    //Testing that applyDamage should throw IllegalArgumentException when passed a negative number
    @Test(expected = IllegalArgumentException.class)
    public void applyDamageShouldThrowIllegalArgumentExceptionWithNegativeDamage() {
        entityTest.applyDamage(-10);
    }

    /**
     * Test ID: Entity_1.6
     *
     * Input: N/A (positive health)
     * Expected Output: True when check if it isAlive()
     */
    //Testing that isAlive works with a standard positive health
    @Test
    public void isAliveShouldReturnTrueWithPositiveHealth() {
        assertTrue(entityTest.isAlive());
    }

    /**
     * Test ID: Entity_1.7
     *
     * Input: int value between 0 and max health but not equal
     * Expected Output: True when check if it isAlive()
     */
    @Test
    public void isAliveShouldReturnTrueWithPositiveHealthAfterTakingSomeDamage() {
        entityTest.applyDamage(50);

        assertTrue(entityTest.isAlive());
    }

    /**
     * Test ID: Entity_1.8
     *
     * Input: int value equal to max health
     * Expected Output: False when check if it isAlive()
     */
    //Testing that isAlive returns false when health is zero
    @Test
    public void isAliveShouldReturnFalseWithZeroHealth() {
        entityTest.applyDamage(100);

        assertFalse(entityTest.isAlive());
    }

    /**
     * Test ID: Entity_1.8
     *
     * Input: int value greater than max health
     * Expected Output: False when check if it isAlive()
     */
    //Testing that isAlive returns false when health is negative
    @Test
    public void isAliveShouldReturnFalseWithNegativeHealth() {
        entityTest.applyDamage(110);

        assertFalse(entityTest.isAlive());
    }


}

class entityTester extends Entity{

    /**
     * @param spawnPos The position the entity will spawn at.
     * @param img      The texture of the entity.
     * @param imSize   Size of the entity. Can be used to resize large/small textures
     * @param health   Hit points of the entity
     */
    public entityTester(Vector2 spawnPos, Texture img, Vector2 imSize, int health, int radius) {
        super(spawnPos, img, imSize, health, radius);
    }
}
