package com.dicycat.kroy.entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.bullets.Bullet;
import com.dicycat.kroy.bullets.BulletDispenser;
import com.dicycat.kroy.bullets.Pattern;

import java.util.Random;

/**
 * A mobile Alien which follows a predefined patrol coordinate path and will stop and attack
 * the player if the player is within range.
 * 
 * @author Lucy Ivatt
 */
public class Alien extends Entity {

	BulletDispenser dispenser;
	int speed;

	private Vector2[] waypoints; // The waypoints that the alien will follow
	private int currentWaypoint; // The waypoint index the alien is currently at
	private float movementCountdown; // Delays first movement of the alien, stops them from overlapping at beginning.

	public Alien(int patrolNumber, float movementCountdown, int radius) {
		// Edited Entity constructor to include radius so that alien radius can be changed.
		super(new Vector2(0, 0), Kroy.mainGameScreen.textures.getUFO(),
				new Vector2(80, 80), 500, radius);
		// Sets waypoints variable to be equal to the right array of pre-defined coordinates.
		if (patrolNumber == 1) {
			waypoints = new Vector2[]{new Vector2(268 * 16, (400 - 254) * 16), new Vector2(344 * 16, (400 - 254) * 16),
					new Vector2(344 * 16, (400 - 310) * 16), new Vector2(246 * 16, (400 - 310) * 16),
					new Vector2(246 * 16, (400 - 279) * 16), new Vector2(268 * 16, (400 - 279) * 16)};
		}
		else if (patrolNumber == 2) {
			waypoints = new Vector2[]{new Vector2(101 * 16, (400 - 232) * 16), new Vector2(190 * 16, (400 - 232) * 16),
					new Vector2(190 * 16, (400 - 279) * 16), new Vector2(179 * 16, (400 - 279) * 16),
					new Vector2(179 * 16, (400 - 363) * 16), new Vector2(101 * 16, (400 - 362) * 16)};
		}

		else if (patrolNumber == 3) {
			waypoints = new Vector2[]{new Vector2(211 * 16, (400 - 84) * 16), new Vector2(255 * 16, (400 - 84) * 16),
					new Vector2(255 * 16, (400 - 106) * 16), new Vector2(212 * 16, (400 - 106) * 16)};
		}

		else if (patrolNumber == 4) {
			waypoints = new Vector2[]{new Vector2(111 * 16, (400 - 110) * 16), new Vector2(111 * 16, (400 - 157) * 16),
					new Vector2(120 * 16, (400 - 157) * 16), new Vector2(120 * 16, (400 - 176) * 16),
					new Vector2(91 * 16, (400 - 176) * 16), new Vector2(91 * 16, (400 - 138) * 16),
					new Vector2(49 * 16, (400 - 138) * 16), new Vector2(49 * 16, (400 - 110) * 16)};
		}
		setPosition(waypoints[0]); // Sets the starting position of the alien to the first waypoint
		dispenser = new BulletDispenser(this); // Initialises the bullet dispenser
		Random random = new Random(); // Creates instance of Random class to give Aliens random damage.
		dispenser.addPattern(new Pattern(180, 300, 800, 0.1f, 20,
				1, 0.5f, random.nextInt((20 - 5) + 1) + 5));  // Adds pattern and random
																							// damage to the bullet dispenser.
		currentWaypoint = 0; // Sets current waypoint as the first waypoint in the array.
		this.movementCountdown = movementCountdown;
		speed = 150;
	}

	/**
	 * Called to update the Alien every game tick. Moves the alien around its patrol path.
	 * If a player is in range, shoots projectiles at the player.
	 */
	@Override
	public void update() {
		movementCountdown -= 1; // Decrements movement Countdown

		// If the countdown is over, the moves the alien.
		if(movementCountdown < 0) {
				nextWayPoint();
				setPosition(new Vector2(moveAlongPatrol(waypoints[currentWaypoint])));
		}

		// If player in radius then shoots bullets towards them
		Bullet[] toShoot = dispenser.update(playerInRadius());
		if (toShoot != null) {
			for (Bullet bullet : toShoot) {
				bullet.fire(getCentre());
				Kroy.mainGameScreen.addGameObject(bullet);
			}
		}
	}

	/**
	 * Calculates the position that the alien should be set to next game tick.
	 * @param destination the coordinate of the next waypoint which is the aliens destination
	 * @return Vector2 coordinate that the Aliens position will be set to when next updated.
	 */
	private Vector2 moveAlongPatrol(Vector2 destination) {
		Vector2 nextPos = getPosition();
		if (getPosition().x == destination.x) {
			if (destination.y > getPosition().y) {
				nextPos.y += speed * Gdx.graphics.getDeltaTime();
				if (nextPos.y > destination.y) {
					nextPos.y = destination.y;
				}
			} else {
				nextPos.y -= speed * Gdx.graphics.getDeltaTime();
				if (nextPos.y < destination.y) {
					nextPos.y = destination.y;
				}
			}
		} else {
			if (destination.x > getPosition().x) {
				nextPos.x += speed * Gdx.graphics.getDeltaTime();
				if (nextPos.x > destination.x) {
					nextPos.x = destination.x;
				}
			} else {
				nextPos.x -= speed * Gdx.graphics.getDeltaTime();
				if (nextPos.x < destination.x) {
					nextPos.x = destination.x;
				}
			}
		}
		return nextPos;
	}
	/**
	 * Checks if the alien has reached a waypoint and then increments currentWaypoint if so. If it has reached
	 * the end of the path, sets destination waypoint back to the beginning.
	 */
	private void nextWayPoint() {
		if (getPosition().y == waypoints[currentWaypoint].y && getPosition().x == waypoints[currentWaypoint].x) {
			currentWaypoint++;
			if (currentWaypoint >= (waypoints.length)) {
				currentWaypoint = 0;
			}
		}
	}

}
