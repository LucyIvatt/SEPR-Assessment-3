package com.dicycat.kroy.entities;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;

/**
 * An entity which when driven over, gives a firetruck a "power up" (An increase
 * to their abilities eg. refill water).
 * 
 * @author Samuel Hutchings
 *
 */
public class PowerUp extends Entity {

	// Static variables that should be the same across all power ups, unless a fully
	// custom powerup is constructed.
	private static int radius = 25;
	private static int health = 1;
	private static Vector2 imSize = new Vector2(16, 16);
	
	private static Vector2[] powerUpLocations = new Vector2[] {
			new Vector2(268 * 16, (400 - 254) * 16), new Vector2(344 * 16, (400 - 254) * 16),
			new Vector2(344 * 16, (400 - 310) * 16), new Vector2(246 * 16, (400 - 310) * 16),
			new Vector2(246 * 16, (400 - 279) * 16), new Vector2(268 * 16, (400 - 279) * 16),
			new Vector2(101 * 16, (400 - 232) * 16), new Vector2(190 * 16, (400 - 232) * 16),
			new Vector2(190 * 16, (400 - 279) * 16), new Vector2(179 * 16, (400 - 279) * 16),
			new Vector2(179 * 16, (400 - 363) * 16), new Vector2(101 * 16, (400 - 362) * 16),
			new Vector2(211 * 16, (400 - 84 ) * 16), new Vector2(255 * 16, (400 - 84 ) * 16),
			new Vector2(255 * 16, (400 - 106) * 16), new Vector2(212 * 16, (400 - 106) * 16),
			new Vector2(111 * 16, (400 - 110) * 16), new Vector2(111 * 16, (400 - 157) * 16),
			new Vector2(120 * 16, (400 - 157) * 16), new Vector2(120 * 16, (400 - 176) * 16),
			new Vector2(91  * 16, (400 - 176) * 16), new Vector2(91  * 16, (400 - 138) * 16),
			new Vector2(49  * 16, (400 - 138) * 16), new Vector2(49  * 16, (400 - 110) * 16) };
	
	private enum PowerUpType {
		WATER, HEALTH, DAMAGE, SPEED, SHIELD;

		public static PowerUpType getRandomType() {
			Random random = new Random();
			return values()[random.nextInt(values().length)];
		}
	}

	private PowerUpType type = null;

	public PowerUp(Vector2 spawnPos, Texture img, Vector2 imSize, int health, int radius) {
		super(spawnPos, img, imSize, health, radius);

	}

	/**
	 * Generates a power up at a random location with a random ability.
	 */
	public PowerUp() {
		this(powerUpLocations[new Random().nextInt(powerUpLocations.length)]);
	}

	/**
	 * Generates a power up with the specified spawn position and texture.
	 * 
	 * @param spawnPos The position the power up is spawned at
	 * @param img      The texture of the power up
	 * @param type     The type of power up
	 */
	public PowerUp(Vector2 spawnPos, Texture img, PowerUpType type) {
		this(spawnPos, img, imSize, health, radius);
		this.type = type;
	}

	public PowerUp(Vector2 spawnPos) {
		this(spawnPos, new Texture("PowerUpGeneric.png"), imSize, health, radius);
		type = PowerUpType.getRandomType();
		//type = PowerUpType.SPEED;
		switch (type) {
		case DAMAGE:
			setTexture(new Texture("PowerUpRed.png"));
			break;
		case HEALTH:
			setTexture(new Texture("PowerUpGreen.png"));
			break;
		case SHIELD:
			setTexture(new Texture("PowerUpPurple.png"));
			break;
		case SPEED:
			setTexture(new Texture("PowerUpYellow.png"));
			break;
		case WATER:
			setTexture(new Texture("PowerUpBlue.png"));
			break;
		default:
			System.err.println("Power up texture selection error");
			break;

		}
	}

	/**
	 * Refills the trucks water; This will be delegated to its own subclass in the
	 * future, currently just here to test the class in development.
	 */
	@Override
	public void update() {
		if (playerInRadius()) {
			//System.out.println(type);
			FireTruck player = Kroy.mainGameScreen.getPlayer();
			switch (type) {
			case DAMAGE:
				player.increaseDamage();
				break;
			case HEALTH:
				player.refillHealth();
				break;
			case SHIELD:
				player.setShield(10);
				break;
			case SPEED:
				player.increaseSpeed();
				break;
			case WATER:
				player.refillWater();
				break;
			default:
				System.err.println("Power up had no type error");
				break;
			}
			applyDamage(1);
		}
	}
}
