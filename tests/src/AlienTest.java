import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.Alien;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class AlienTest {

    private Kroy testKroy;

    private Alien testAlien;
    private Alien shootingAlien;

    @Before
    public void init() {
        testKroy = new Kroy();
        testAlien = new Alien(1, 2, 100);

        shootingAlien = new Alien(2, 0, 100);
    }


    /**
     * Test ID: Alien_1.1
     *
     * Input: N/A
     * Expected Output: Correct initialized variables
     */
    //Testing that the Alien constructor is working as intended with standard input
    @Test
    public void alienShouldInitializeCorrectly() {
        assertEquals(new Vector2(268 * 16, (400 - 254) * 16), testAlien.getPosition());
    }


    /**
     * Test ID: Alien_1.2
     *
     * Input: update() once (movementCountDown > 0)
     * Expected Output: MovementCountdown-1 and position unchanged
     */
    //Testing that update will not move when movementCountdown is above 0
    @Test
    public void updateShouldNotChangePositionOrWayPointWhileMovementCountdownIsAboveZero() {
        //movementCountdown will equal 1 after this
        testAlien.update();

        assertEquals(new Vector2(268 * 16, (400 - 254) * 16), testAlien.getPosition());
        assertEquals(0, testAlien.getCurrentWaypoint());
    }

    /**
     * Test ID: Alien_1.3
     *
     * Input: update() twice (movementCountDown == 0)
     * Expected Output: currentWayPoint and position unchanged
     */
    //Testing that update will not move when movementCountdown is 0
    @Test
    public void updateShouldNotChangePositionOrWayPointWhileMovementCountdownIsZero() {
        //movementCountdown will equal 1 after this
        testAlien.update();
        //movementCountdown will equal 0 after this
        testAlien.update();

        assertEquals(new Vector2(268 * 16, (400 - 254) * 16), testAlien.getPosition());
        assertEquals(0, testAlien.getCurrentWaypoint());
    }

    /**
     * Test ID: Alien_1.4
     *
     * Input: update() thrice  (movementCountDown < 0)
     * Expected Output: currentWayPoint+1 and position changed in only X directions base on calculations
     */
    //Testing the patrol moves when below 0 on the movementCountdown - X direction
    @Test
    public void updateShouldChangePositionAndWayPointWhileMovementCountdownIsBelowZeroInXDirection() {
        Vector2 estimatedPosition = testAlien.getPosition();

        //movementCountdown will equal 1 after this
        testAlien.update();
        //movementCountdown will equal 0 after this
        testAlien.update();
        //movementCountdown will equal -1 after this
        testAlien.update();

        //Calculations
        estimatedPosition.x += 150 * Gdx.graphics.getDeltaTime();

        assertEquals(estimatedPosition, testAlien.getPosition());
        assertEquals(1, testAlien.getCurrentWaypoint());
    }

    /**
     * Test ID: Alien_1.5
     *
     * Input: new alien that needs only one update() to move
     * Expected Output: currentWayPoint+1 and position changed in only Y directions base on calculations
     */
    //Testing the patrol moves when below 0 on the movementCountdown - Y direction
    //This also checks that the correct calculations are done based on current position (the moveAlongPatrol)
    @Test
    public void updateShouldChangePositionAndWayPointWhileMovementCountdownIsBelowZeroInYDirection() {
        Alien alienYDirection = new Alien(4, 0, 10);
        Vector2 estimatedPosition = alienYDirection.getPosition();

        alienYDirection.update();

        estimatedPosition.y -= 150 * Gdx.graphics.getDeltaTime();

        assertEquals(estimatedPosition, alienYDirection.getPosition());
        assertEquals(1, alienYDirection.getCurrentWaypoint());
    }

    /**
     * Test ID: Alien_1.6
     *
     * Input: set currentWayPoint to last element, the alien position to that last element location and update()
     * Expected Output: currentWayPoint to reset to 0
     */
    //Testing that when at last wayPoint the currentWayPoint is reset to 0
    @Test
    public void nextWayPointShouldResetBackTo0WhenAtEndOfWayPoints() {
        //set to last element (WayPoint)
        testAlien.setCurrentWaypoint(5);

        //get movementCountDown to 0
        testAlien.update();
        testAlien.update();

        //set position so it has reach the final wayPoint
        testAlien.setPosition(new Vector2(268 * 16, (400 - 279) * 16));
        testAlien.update();

        assertEquals(0, testAlien.getCurrentWaypoint());
    }

    /**
     * Test ID: Alien_1.7
     *
     * Input: set currentWayPoint to last element, the alien position NOT to that last element location and update()
     * Expected Output: currentWayPoint remains at last element
     */
    //Testing that when currentWayPoint is at the last wayPoint but the position of alien is not - do not reset
    @Test
    public void nextWayPointShouldNotResetWayPointBeforeReachingFinalWayPoint() {
        //set to last element (WayPoint)
        testAlien.setCurrentWaypoint(5);

        //get movementCountDown to 0
        testAlien.update();
        testAlien.update();

        //This time it has not been set to final coordination
//        testAlien.setPosition(new Vector2(268 * 16, (400 - 279) * 16));
        testAlien.update();

        assertEquals(5, testAlien.getCurrentWaypoint());
    }




}
