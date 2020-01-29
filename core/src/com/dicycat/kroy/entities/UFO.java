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
public class UFO extends Entity {

	BulletDispenser dispenser;
	int speed;

	// NEW SHIT
	private Vector2[] waypoints;
	private int currentWaypoint;
	//

	/**
	 */
	public UFO() {
		super(new Vector2(269 * 16, (400 - 254) * 16), Kroy.mainGameScreen.textures.getUFO(),
				new Vector2(80, 80), 100);
		// example waypoints
		waypoints = new Vector2[]{new Vector2(269 * 16, (400 - 254) * 16), new Vector2(344 * 16, (400 - 254) * 16),
				new Vector2(344 * 16, (400 - 310) * 16), new Vector2(246 * 16, (400 - 310) * 16),
				new Vector2(246 * 16, (400 - 279) * 16), new Vector2(269 * 16, (400 - 279) * 16)};
		dispenser = new BulletDispenser(this);
		dispenser.addPattern(new Pattern(180, 300, 800, 0.1f, 20, 1, 0.5f));
		currentWaypoint = 0;
		speed = 100;
	}

	/**
	 *
	 */
	@Override
	public void update() {
		//movement
		nextWayPoint();
		Vector2 newPos = moveAlongPatrol(waypoints[currentWaypoint]);
		changePosition(new Vector2(newPos.x, newPos.y));

		//weapons
//		Bullet[] toShoot = dispenser.update(true);
//		if (toShoot != null) {
//			for (Bullet bullet : toShoot) {
//				bullet.fire(getCentre());
//				Kroy.mainGameScreen.addGameObject(bullet);
//			}
//		}
	}

	private Vector2 moveAlongPatrol(Vector2 destination) {
		Vector2 nextPos = getPosition();
		if (getPosition().x == destination.x) {
			if (destination.y > getPosition().y) {
				nextPos.y += speed * Gdx.graphics.getDeltaTime();
			} else {
				nextPos.y -= speed * Gdx.graphics.getDeltaTime();
			}
		} else {
			if (destination.x > getPosition().x) {
				nextPos.x += speed * Gdx.graphics.getDeltaTime();
			} else {
				nextPos.x -= speed * Gdx.graphics.getDeltaTime();
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
