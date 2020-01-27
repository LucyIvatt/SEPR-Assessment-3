package com.dicycat.kroy.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.FireTruck;

/**
 * Projectile fired by hostile entities
 * 
 * @author Riju De
 *
 */
public class Bullet extends GameObject {
	private int speed;			//Speed of the bullet
	private Vector2 velocity;	//Direction and amount to travel
	private float maxDist;		//Max distance to travel
	private float travelDist; 	//Distance left to travel
	private Circle hitbox;		//Bullet hit box


	/**
	 * @param spawnPos Position to spawn the bullet
	 * @param direction direction the bullet will travel in
	 * @param speed speed the bullet should travel at
	 * @param range distance the bullet should travel before it is removed
	 */
	public Bullet(Vector2 spawnPos, Vector2 direction, int speed, float range) {	//Constructor
		super(spawnPos, Kroy.mainGameScreen.textures.getBullet(), new Vector2(20,20));
		this.speed = speed;
		changeDirection(direction);
		maxDist = range;
		hitbox = new Circle(spawnPos.x, spawnPos.y, 10);
	}

	/**
	 * Reactivate the bullet and reset position
	 * @param initial position to set at
	 */
	public void fire(Vector2 initial) {
		travelDist = maxDist;
		setPosition(initial);
		changePosition(new Vector2(-getOriginX(), -getOriginY()));
		remove = false;
	}

	/**
	 * Calculate velocity of the bullet (Translation per frame)
	 * @param newDir New direction of the bullet
	 */
	public void changeDirection(Vector2 newDir) {
		velocity = newDir.scl(speed);
	}

	/**
	 *
	 */
	@Override
	public void update() {
		Vector2 posChange = velocity.cpy().scl(Gdx.graphics.getDeltaTime());	//Calculate distance to move
		travelDist -= posChange.len();
		if (travelDist <= 0) {	//Remove if travelled required distance
			remove = true;
		}
		Vector2 currentPos = new Vector2(getX(),getY());
		setPosition(currentPos.add(posChange));

		//Moves hit box according to the sprite.
		hitbox.x = getCentre().x;
		hitbox.y = getCentre().y;

		//Check to see if bullet collides with the players truck.
		FireTruck truck = Kroy.mainGameScreen.getPlayer();
		if (truck.isAlive()) {
			if(Intersector.overlaps(hitbox, truck.getHitbox())){
				truck.applyDamage(10);
				remove = true;
			}
		}

	}

	public Circle GetHitbox(){
		return this.hitbox;
	}
}
