package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.bullets.Bullet;
import com.dicycat.kroy.bullets.BulletDispenser;
import com.dicycat.kroy.bullets.Pattern;
import com.dicycat.kroy.misc.StatBar;

/**
 * Hostile building which fires at the player when within its radius.
 */

public class Fortress extends Entity {

	private BulletDispenser dispenser;
	private Texture deadTexture;
	private StatBar healthBar;
	private Texture aliveTexture;
	// FORTRESS_DAMAGE_1 - START OF MODIFICATION - NP STUDIOS - CASSANDRA LILLYSTONE
	// ----
	private int damage; // Added a new attribute 'damage'
	// FORTRESS_DAMAGE_1 - END OF MODIFICATION - NP STUDIOS

	/**
	 * @param spawnPos
	 * @param fortressTexture
	 * @param deadTexture
	 * @param size
	 */

	// FORTRESS_HEALTH_2 - START OF MODIFICATION - NP STUDIOS - CASSANDRA LILLYSTONE
	// ----
	// Added health parameter to Fortress constructor and changed it in the call to
	// super from "500" to "health"
	public Fortress(Vector2 spawnPos, Texture fortressTexture, Texture deadTexture, Vector2 size, int health,
			int fortressDamage) { ////
		super(spawnPos, fortressTexture, size, health, 500);
		// FORTRESS_HEALTH_2 - END OF MODIFICATION - NP STUDIOS
		this.damage = fortressDamage;
		// FORTRESS_DAMAGE_3 - START OF MODIFICATION - NP STUDIOS - CASSANDRA LILLYSTONE
		// ----
		// Added fortressDamage as a parameter to the constructor above
		// Passed the damage to the Pattern constructors

		dispenser = new BulletDispenser(this);
		dispenser.addPattern(new Pattern(180, 300, 800, 0.1f, 20, 1, 0.5f, this.getDamage()));
		dispenser.addPattern(new Pattern(100, 500, 0.5f, 8, 5, 0.5f, this.getDamage()));
		dispenser.addPattern(new Pattern(0, 50, 800, 2f, 3, 36, 4, this.getDamage()));
		dispenser.addPattern(new Pattern(200, 600, 0.3f, 12, 2, 0.3f, this.getDamage()));
		dispenser.addPattern(new Pattern(false, 0, 3, 100, 900, 0.02f, 1, 0.2f, this.getDamage()));
		dispenser.addPattern(new Pattern(true, 0, 1, 100, 900, 0.02f, 1, 1.2f, this.getDamage()));

		// FORTRESS_HEALTH_3 - END OF MODIFICATION - NP STUDIOS

		this.deadTexture = deadTexture;

		// END_GAME_FIX_2 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
		// Deleted addFortress call
		// END_GAME_FIX_2 - END OF MODIFICATION - NP STUDIOS

		healthBar = new StatBar(new Vector2(getCentre().x, getCentre().y + 100), "Red.png", 10);
		Kroy.mainGameScreen.addGameObject(healthBar);
		shouldSave = true;
		this.aliveTexture = fortressTexture;
	}

	/**
	 * Removes from active pool and displays destroyed texture
	 */
	@Override
	public void die() {
		super.die();
		sprite.setTexture(deadTexture);
		Kroy.mainGameScreen.getHud().updateScore(1000);
		// healthBar.setRemove(true);
		setHealthPoints(0);
		displayable = true;
		Kroy.mainGameScreen.removeFortress();
		if (Kroy.mainGameScreen.getFortressesCount() == 0) {// If last fortress
			// HIGHSCORE_5 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
			// Added a bonus to the score if the use finishes the game before the
			// firestation is destroyed. Calculated
			// using time remaining.
			Kroy.mainGameScreen.getHud().updateScore((int) ((15 * 60) - Kroy.mainGameScreen.getHud().timer) * 10); // time
																													// remaining
																													// bonus
			// HIGHSCORE_5 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----
			Kroy.mainGameScreen.gameOver(true); // End game WIN
		}
	}

	/**
	 * Apply certain amount of damage to the entity and updates the health bar
	 * 
	 * @param damage Amount of damage to apply to the Fortress
	 *
	 *               Edited by Lucy Ivatt - NP STUDIOS
	 */
	@Override
	public void applyDamage(float damage) {
		super.applyDamage(damage);
		healthBar.setPosition(getCentre().add(0, (getHeight() / 2) + 25));
		healthBar.setBarDisplay((healthPoints * 500) / maxHealthPoints);
	}

	/**
	 * Updates the dispenser associated with the fortress and adds bullets to the
	 * mainGameScreen
	 */
	@Override
	public void update() {
		// weapons
		Bullet[] toShoot = dispenser.update(playerInRadius());
		if (toShoot != null) {
			for (Bullet bullet : toShoot) {
				bullet.fire(getCentre());
				Kroy.mainGameScreen.addGameObject(bullet);
			}
		}

	}

	public int getDamage() { // FORTRESS_DAMAGE_2 - START OF MODIFICATION - NP STUDIOS - CASSANDRA LILLYSTONE
								// ----
		return this.damage; // Implemented a getter for damage
	} // FORTRESS_DAMAGE_2 - END OF MODIFICATION - NP STUDIOS


	@Override
	public String save() {
		// Return the health value
		return Integer.toString(this.healthPoints);
	}

	@Override
	public void load(String data) {
		// hdata is an integer representation of a fortress' health
		int hdata = Integer.parseInt(data);

		// if the health loaded is 0 or lower, kill the fortress. Else set its new
		// health value and change the texture. It will automatically be moved between
		// gameObjects and deadObjects in the main GameScreen class so we don't need to
		// worry about that here
		if (hdata < 1) {
			super.die();
			sprite.setTexture(deadTexture);
			displayable = true;
		} else {
			sprite.setTexture(aliveTexture);
		}
		setHealthPoints(hdata);
		healthBar.setBarDisplay((healthPoints * 500) / maxHealthPoints);
	}

	@Override
	public String getUUID() {
		// Each fortress has a unique uuid because there is only one at each location
		return ("fortress" + getPosition().toString());
	}
}
