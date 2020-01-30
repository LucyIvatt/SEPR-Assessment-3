package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
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
public class Alien extends Entity {

	BulletDispenser dispenser;
	int speed;

	// NEW SHIT
	private Vector2[] waypoints;
	private int currentWaypoint;
	private float movementCountdown;
	private boolean truckInRange;
	//

	/**
	 */
	public Alien(int patrolNumber, float movementCountdown, int radius) {
		super(new Vector2(0, 0), Kroy.mainGameScreen.textures.getUFO(),
				new Vector2(80, 80), 500, radius);

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

		setPosition(waypoints[0]);
		dispenser = new BulletDispenser(this);
		dispenser.addPattern(new Pattern(180, 300, 800, 0.1f, 20, 1, 0.5f, 10)); // SORT DAMAGE ATTRIBUTE OUT (HARDCODED AS 10 FOR NOW)
		currentWaypoint = 0;
		this.movementCountdown = movementCountdown;
		truckInRange = false;
		speed = 150;
	}

	/**
	 *
	 */
	@Override
	public void update() {
		movementCountdown -= 1; // Adds space between the aliens

		if(!playerInRadius() && movementCountdown < 0) {
				nextWayPoint();
				setPosition(new Vector2(moveAlongPatrol(waypoints[currentWaypoint])));
		}

		Bullet[] toShoot = dispenser.update(playerInRadius());
		if (toShoot != null) {
			for (Bullet bullet : toShoot) {
				bullet.fire(getCentre());
				Kroy.mainGameScreen.addGameObject(bullet);
			}
		}
	}

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

	private void nextWayPoint() {
		if (getPosition().y == waypoints[currentWaypoint].y && getPosition().x == waypoints[currentWaypoint].x) {
			currentWaypoint++;
			if (currentWaypoint >= (waypoints.length)) {
				currentWaypoint = 0;
			}
		}
	}
}
