package com.dicycat.kroy.entities;

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

	public PowerUp(Vector2 spawnPos, Texture img, Vector2 imSize, int health, int radius) {
		super(spawnPos, img, imSize, health, radius);
	}

	/**
	 * Generates a generic power up, with no attributes and a grey texture, at (0,0)
	 */
	public PowerUp() {
		this(new Vector2(0, 0), new Texture("PowerUpGeneric.png"), imSize, health, radius);
	}

	/**
	 * Generates a power up with the specified spawn position and texture.
	 * 
	 * @param spawnPos The position the power up is spawned at
	 * @param img      The texture of the power up
	 */
	public PowerUp(Vector2 spawnPos, Texture img) {
		this(spawnPos, img, imSize, health, radius);
	}

	/**
	 * Refills the trucks water; This will be delegated to its own subclass in the
	 * future, currently just here to test the class in development.
	 */
	@Override
	public void update() {
		if (playerInRadius()) {
			Kroy.mainGameScreen.getPlayer().refillWater();
			applyDamage(1);
		}
	}

}
