package com.dicycat.kroy.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.scenes.HUD;

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
	private int bulletDamage;


	/**
	 * @param spawnPos Position to spawn the bullet
	 * @param direction direction the bullet will travel in
	 * @param speed speed the bullet should travel at
	 * @param range distance the bullet should travel before it is removed
	 */
	public Bullet(Vector2 spawnPos, Vector2 direction, int speed, float range, int patternDamage) {	//Constructor
		super(spawnPos, Kroy.mainGameScreen.textures.getBullet(), new Vector2(20,20));

		// FORTRESS_DAMAGE_6 - START OF MODIFICATION - NP STUDIOS - CASSANDRA LILLYSTONE ----
		// Created a new attribute "bulletDamage" and set it as patternDamage
		this.bulletDamage = patternDamage;
		// FORTRESS_HEALTH_6 - END OF MODIFICATION - NP STUDIOS
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
				// FORTRESS_IMPROVE_1 - START OF MODIFICATION - NP STUDIOS - CASSANDRA LILLYSTONE
				// After 7.5 minutes (half way through game) the damage of fortresses doubles
				if 	(HUD.worldTimer > 450){
					bulletDamage = bulletDamage * 2;
				}
				// FORTRESS_IMPROVE_1 - END OF MODIFICATION - NP STUDIOS

																// FORTRESS_DAMAGE_7 - START OF MODIFICATION - NP STUDIOS
				truck.applyDamage(bulletDamage);				// - CASSANDRA LILLYSTONE ----
																// Passed bulletDamage to applyDamage() function, allowing
																// fortresses to have different damage levels
				remove = true;									// FORTRESS_HEALTH_7 - END OF MODIFICATION - NP STUDIOS
			}
		}

	}

	public Circle GetHitbox(){
		return this.hitbox;
	}
}
