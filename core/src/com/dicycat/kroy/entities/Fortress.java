package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.bullets.Bullet;
import com.dicycat.kroy.bullets.BulletDispenser;
import com.dicycat.kroy.bullets.Pattern;
import com.dicycat.kroy.misc.StatBar;

/**
 * Static hostile Entity.
 * Fires at the player when within its radius.
 * 
 * @author 
 *
 */
public class Fortress extends Entity {

	private BulletDispenser dispenser;
	private Texture deadTexture;
	private StatBar healthBar;

	/**
	 * @param spawnPos
	 * @param fortressTexture
	 * @param deadTexture
	 * @param size
	 */
	public Fortress(Vector2 spawnPos, Texture fortressTexture, Texture deadTexture, Vector2 size) {
		super(spawnPos, fortressTexture, size, 500);
		dispenser = new BulletDispenser(this);
		dispenser.addPattern(new Pattern(180, 300, 800, 0.1f, 20, 1, 0.5f));
		dispenser.addPattern(new Pattern(100, 500, 0.5f, 8, 5, 0.5f));
		dispenser.addPattern(new Pattern(0, 50, 800, 2f, 3, 36, 4));
		dispenser.addPattern(new Pattern(200, 600, 0.3f, 12, 2, 0.3f));
		dispenser.addPattern(new Pattern(false, 0, 3, 100, 900, 0.02f, 1, 0.2f));
		dispenser.addPattern(new Pattern(true, 0, 1, 100, 900, 0.02f, 1, 1.2f));

		this.deadTexture = deadTexture;
		Kroy.mainGameScreen.addFortress();
		healthBar = new StatBar(new Vector2(getCentre().x, getCentre().y + 100), "Red.png", 10);
		Kroy.mainGameScreen.addGameObject(healthBar);
	}

	/**
	 * Removes from active pool and displays destroyed state
	 */
	@Override
	public void die() {
		super.die();
		sprite.setTexture(deadTexture);
		Kroy.mainGameScreen.getHud().updateScore(1000);
		healthBar.setRemove(true);
		displayable = true;
		Kroy.mainGameScreen.removeFortress();
		if (Kroy.mainGameScreen.fortressesLeft() == 0) {	//If last fortress
			Kroy.mainGameScreen.gameOver(true); 					//End game WIN
		}
	}

	/**
	 * Apply x amount of damage to the entity
	 * Updates the health bar
	 * @param damage Amount of damage to inflict on the Entity
	 */
	@Override
	public void applyDamage(float damage) {
		super.applyDamage(damage);
		healthBar.setPosition(getCentre().add(0, (getHeight() / 2) + 25));
		healthBar.setBarDisplay((healthPoints*500)/maxHealthPoints);
	}

	/**
	 *
	 */
	@Override
	public void update() {
		//weapons
		Bullet[] toShoot = dispenser.update(playerInRadius());
		if (toShoot != null) {
			for (Bullet bullet : toShoot) {
				bullet.fire(getCentre());
				Kroy.mainGameScreen.addGameObject(bullet);
			}
		}

	}

}
