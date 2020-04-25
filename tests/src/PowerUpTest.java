import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.dicycat.kroy.entities.PowerUp;
import com.dicycat.kroy.entities.PowerUp.PowerUpType;

import de.tomgrill.gdxtesting.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class PowerUpTest {

	private PowerUp testPowerUp;

	@Before
	public void init() {
		// Initialises testPowerUp with a random type and location
		testPowerUp = new PowerUp();
	}

	/**
	 * Asserts that the power up has a type and a health value of 1
	 */
	@Test
	public void initialisedProperlyTest() {
		assertFalse(testPowerUp.getType() == null);
		assertTrue(testPowerUp.getHealthPoints() == 1);
	}

	/**
	 * Asserts that the type of the power up can be changed from one type to
	 * another. (In this case, from damage to speed)
	 */
	@Test
	public void changeTypeTest() {
		testPowerUp.setType(PowerUpType.DAMAGE);
		PowerUpType origType = testPowerUp.getType();
		assertTrue(origType == PowerUpType.DAMAGE);

		testPowerUp.setType(PowerUpType.SPEED);
		PowerUpType newType = testPowerUp.getType();
		assertTrue(newType == PowerUpType.SPEED);

		assertFalse(origType == newType);
	}

	/**
	 * Asserts that the power up has a unique identifier
	 */
	@Test
	public void hasUUIDTest() {
		assertFalse(testPowerUp.getUUID() == null);
	}

	/**
	 * Asserts that the power up data can be saved, and different data can also be loaded
	 */
	@Test
	public void saveAndLoadTest() {
		testPowerUp.setType(PowerUpType.DAMAGE);
		assertTrue(testPowerUp.getType() == PowerUpType.DAMAGE);
		assertTrue(testPowerUp.save() == "DAMAGE");

		testPowerUp.load("SPEED");
		assertTrue(testPowerUp.getType() == PowerUpType.SPEED);
	}

	@After
	public void after() {
		testPowerUp.die();
	}

}
