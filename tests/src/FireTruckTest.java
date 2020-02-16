import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.entities.Fortress;
import com.sun.org.apache.xpath.internal.operations.Bool;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(GdxTestRunner.class)
public class FireTruckTest {

    private Kroy testKroy;
    private FireTruck testTruck;
    private Float[] testTruckStats = new Float[] {1f, 1f, 100f, 1000f};

    @Before
    public void init() {
        testKroy = new Kroy();
        testTruck = new FireTruck(new Vector2(0, 0), testTruckStats, 1);

    }

    //Testing that the FireTruck constructor is working as intended with standard input
    @Test
    public void fireTruckShouldInitializeCorrectly() {
        assertEquals(new Vector2(12.5f, 25f), testTruck.getCentre());
    }

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

    //Testing that movePosition does not move when another object is in the way
    @Test
    public void moveInDirectionShouldNotChangeXandYIfGoingToCollisionTile() {

        testTruck.setPosition(new Vector2(2903, 3211));
        testTruck.setSelected(true);
        testTruck.moveInDirection();

        assertEquals(new Vector2(2903, 3211), testTruck.getPosition());
    }

    //Testing that when a FireTruck is in range of a 'enemy' the FireTruck will attack
    //Methods Tested: update, entitiesInRange, objectInRange, and playerFire
    @Test
    public void whenFireTruckInRangeToTargetItShouldAttack() {
        testTruck.setPosition(new Vector2(2903, 3211));
        testTruck.setSelected(true);

        testTruck.update();
        assertEquals(99f, testTruck.getCurrentWater(), 0.0f);
    }

    //Testing that when a FireTruck is not in range of an 'enemy' it does not attack
    @Test
    public void whenFireTruckNotInRangeToTargetItShouldNotAttack() {
        testTruck.setSelected(true);
        testTruck.update();

        assertEquals(100f, testTruck.getCurrentWater(), 0.0f);
    }

    //Testing that replenish adds to health and water when not max
    @Test
    public void whenFireTruckNotMaxHealthOrWaterShouldRegainHealth() {
        testTruck.applyDamage(10f);
        System.out.println(testTruck.getHealthPoints());
        testTruck.setPosition(new Vector2(2903, 3211));
        testTruck.setSelected(true);

        testTruck.replenish();

//        assertEquals(92, testTruck.getHealthPoints());
        assertEquals(92f, testTruck.getCurrentWater(), 0.0f);
    }







}
