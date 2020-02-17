import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.FireTruck;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class FireTruckTest {

    private Kroy testKroy;
    private FireTruck testTruck;
    private FireTruck testTruck2;

    private Float[] testTruckStats = new Float[] {1f, 1f, 100f, 1000f};
    private Float[]  fireTruckStats2 = new Float[] {50f, 3f, 10f, 7f};


    @Before
    public void init() {
        testKroy = new Kroy();
        testTruck = new FireTruck(new Vector2(0, 0), testTruckStats, 1);
        testTruck2 = new FireTruck(new Vector2(100, 100), fireTruckStats2, 2);
    }


    /**
     * Test ID: FireTruck_1.1
     *
     * Input: N/A
     * Expected Output: Correct initialized variables
     */
    //Testing that the FireTruck constructor is working as intended with standard input
    @Test
    public void fireTruckShouldInitializeCorrectly() {
        assertEquals(new Vector2(12.5f, 25f), testTruck.getCentre());
        assertEquals(new Vector2(112.5f, 125f), testTruck2.getCentre());

    }


    /**
     * Test ID: FireTruck_1.2
     *
     * Input: N/A (The initialized 'Direction')
     * Expected Output: New centre/position in relation to speed, direction and time in only the Y direction
     */
    //Testing that moveInDirection changes the Y position but not the X when no collision when Direction set to N (0)
    //This also tests part of 'update()' (Integration testing)
    @Test
    public void moveInDirectionShouldChangeYWhenNoCollisionAndDirectionSetToN() {
        testTruck.setSelected(true);
        testTruck.moveInDirection();

        //The calculations to find the new position
        Vector2 movement = new Vector2(1,0);
        movement.setAngle(0+90);
        float posChange = 1f * Gdx.graphics.getDeltaTime();
        Matrix3 distance = new Matrix3().setToScaling(posChange,posChange);
        movement.nor();
        movement.mul(distance);
        Vector2 newPos = new Vector2(12.5f, 25.0f);
        newPos.add(movement);

        assertEquals(newPos, testTruck.getCentre());
    }

    /**
     * Test ID: FireTruck_1.3
     *
     * Input: East ('E') direction - 270
     * Expected Output: New centre/position in relation to speed, direction and time in only the X direction
     */
    //Testing that moveInDirection changes the X position but not the Y when no collision when Direction set to E (270)
    @Test
    public void moveInDirectionShouldChangeXWhenNoCollisionAndDirectionSetToE() {
        testTruck.setDirection(270);
        testTruck.setSelected(true);
        testTruck.moveInDirection();

        //The calculations to find the new position
        Vector2 movement = new Vector2(1,0);
        movement.setAngle(270+90);
        float posChange = 1f * Gdx.graphics.getDeltaTime();
        Matrix3 distance = new Matrix3().setToScaling(posChange,posChange);
        movement.nor();
        movement.mul(distance);
        Vector2 newPos = new Vector2(12.5f, 25.0f);
        newPos.add(movement);

        assertEquals(newPos, testTruck.getCentre());

    }

    /**
     * Test ID: FireTruck_1.4
     *
     * Input: North East ('NE') direction - 315
     * Expected Output: New centre/position in relation to speed, direction and time in both the X and Y direction
     */
    //Testing that moveInDirection changes the X and Y position when no collision when Direction set to NE (270)
    @Test
    public void moveInDirectionShouldChangeXanadYWhenNoCollisionalDirectionSetToNE() {
        testTruck.setDirection(315);
        testTruck.setSelected(true);
        testTruck.moveInDirection();

        //The calculations to find the new position
        Vector2 movement = new Vector2(1,0);
        movement.setAngle(315+90);
        float posChange = 1f * Gdx.graphics.getDeltaTime();
        Matrix3 distance = new Matrix3().setToScaling(posChange,posChange);
        movement.nor();
        movement.mul(distance);
        Vector2 newPos = new Vector2(12.5f, 25.0f);
        newPos.add(movement);

        assertEquals(newPos, testTruck.getCentre());
    }


    /**
     * Test ID: FireTruck_1.5
     *
     * Input: New position where neither X not Y can move - (2903, 3211)
     * Expected Output: Same position as Input
     */
    //Testing that movePosition does not move when another object is in the way
    @Test
    public void moveInDirectionShouldNotChangeXandYIfGoingToCollisionTile() {

        testTruck.setPosition(new Vector2(2903, 3211));
        testTruck.setSelected(true);
        testTruck.moveInDirection();

        assertEquals(new Vector2(2903, 3211), testTruck.getPosition());
    }

    /**
     * Test ID: FireTruck_1.6
     *
     * Input: New position where in range of Fortress or Alien - (2903, 3211)
     * Expected Output: Decrease in water level by 1f
     */
    //Testing that when a FireTruck is in range of a 'enemy' the FireTruck will attack
    //Methods Tested: update, entitiesInRange, objectInRange, and playerFire
    @Test
    public void whenFireTruckInRangeToTargetItShouldAttack() {
        testTruck.setPosition(new Vector2(2903, 3211));
        testTruck.setSelected(true);

        testTruck.update();
        assertEquals(99f, testTruck.getCurrentWater(), 0.0f);
    }

    /**
     * Test ID: FireTruck_1.7
     *
     * Input: N/A
     * Expected Output: Nothing to water level
     */
    //Testing that when a FireTruck is not in range of an 'enemy' it does not attack
    @Test
    public void whenFireTruckNotInRangeToTargetItShouldNotAttack() {
        testTruck.setSelected(true);
        testTruck.update();

        assertEquals(100f, testTruck.getCurrentWater(), 0.0f);
    }

    /**
     * Test ID: FireTruck_1.8
     *
     * Input: applyDamage(10f) onto the FireTruck
     * Expected Output: FireTruck to heal the -10+2 amount.
     */
    //Testing that replenish adds to health when not max
    @Test
    public void whenFireTruckNotMaxHealthShouldRegainHealth() {
        testTruck.applyDamage(10f);
        System.out.println(testTruck.getHealthPoints());
        testTruck.repairTruck();

        assertEquals(92, testTruck.getHealthPoints());
    }

    /**
     * Test ID: FireTruck_1.9
     *
     * Input: New position where in range of Fortress or Alien - (2903, 3211)
     * Expected Output: Refills from not max to max water.
     */
    //Testing that refillWater refills water to max water
    @Test
    public void whenFireTruckWaterIsNotMaxRefillToMax() {
        testTruck.setPosition(new Vector2(2903, 3211));
        testTruck.setSelected(true);

        //Lose 3 units of water
        testTruck.update();
        testTruck.update();
        testTruck.update();

        testTruck.refillWater();
        assertEquals(100f, testTruck.getCurrentWater(), 0.0f);
    }






}
