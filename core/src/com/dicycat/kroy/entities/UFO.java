package com.dicycat.kroy.entities;

import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.bullets.Bullet;
import com.dicycat.kroy.bullets.BulletDispenser;
import com.dicycat.kroy.bullets.Pattern;

/**
 * Mobile hostile entity.
 * Fires at the player when within range.
 * 
 * @author Riju De
 *
 */
public class UFO extends Entity {

	BulletDispenser dispenser;

	/**
	 * @param spawnPos
	 */
	public UFO(Vector2 spawnPos) {
		super(spawnPos, Kroy.mainGameScreen.textures.getUFO(), new Vector2(80, 80), 100);
		dispenser = new BulletDispenser(this);
		dispenser.addPattern(new Pattern(180, 300, 800, 0.1f, 20, 1, 0.5f));
	}

	/**
	 *
	 */
	@Override
	public void update() {
		//movement

		//weapons
		Bullet[] toShoot = dispenser.update(true);
		if (toShoot != null) {
			for (Bullet bullet : toShoot) {
				bullet.fire(getCentre());
				Kroy.mainGameScreen.addGameObject(bullet);
			}
		}
	}
}
