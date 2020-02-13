package com.dicycat.kroy.bullets;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.Entity;

/**
 * Stores and handles firing patterns
 * 
 * @author Riju De
 *
 */
public class BulletDispenser {

	private List<Pattern> patterns;	//Stores all patterns
	private Pattern firingPattern; 	//Current pattern firing
	private Entity owner;			//Entity the bulletDispenser is attached to
	
	private int currentPattern;		//Current pattern to fire
	private int currentBullet;		//Current bullet to fire
	private float patternTime;		//Time between firing patterns
	private float patternTimer;		//Time since last pattern
	private float bulletTimer;		//Time since last bullet
	
	/**
	 * @param creator Owner of the BulletDispenser
	 */
	public BulletDispenser(Entity creator) {
		owner = creator;
		patterns = new ArrayList<Pattern>();
		currentPattern = 0;
		bulletTimer = 0;
		patternTimer = 0;
	}
	
	/**
	 * Adds a pattern to the bullet dispensers arsenal
	 * @param pattern Pattern to add
	 */
	public void addPattern(Pattern pattern) {
		patterns.add(pattern);
		if (patterns.size() == 1) {	//If only pattern, set as firing pattern
			firingPattern = patterns.get(0);
			patternTime = firingPattern.getCooldown();
		}
	}
	
	/**
	 * @param fire Whether or not the dispenser can currently fire
	 * @return array of the bullets fired
	 *
	 * Edited by Lucy Ivatt - NP Studios
	 */
	public Bullet[] update(Boolean fire) {		//Called every frame
		if (patterns.size() == 0) {	//No patterns -> no checks required
			return null;
		}
		patternTimer += Gdx.graphics.getDeltaTime();	//Increment timers by time passed
		bulletTimer += Gdx.graphics.getDeltaTime();
		
		//If should be firing, find any bullets that should be fired this frame
		//Then reset and timers and increment bullet/pattern as needed
		if (fire && patternTimer >= patternTime) {		
			if (bulletTimer >= firingPattern.getWaitTime()) {
				bulletTimer = 0;
				Bullet[] toFire;	//Stores bullets to be fired
				if (firingPattern.getAim()) {
					Vector2 targetDirection = new Vector2(Kroy.mainGameScreen.getPlayer().getCentre().x -
							owner.getCentre().x, Kroy.mainGameScreen.getPlayer().getCentre().y -
							owner.getCentre().y); //Aim from entity to player
					
					toFire = firingPattern.aimedSet(currentBullet, targetDirection);
				}else {
					toFire = firingPattern.bulletSet(currentBullet);
				}
				currentBullet++;
				if (currentBullet >= firingPattern.getBullets().length) {
					currentPattern++;
					currentBullet = 0;
					patternTime = firingPattern.getCooldown();
					patternTimer = 0;
					if (currentPattern >= patterns.size()) {
						currentPattern = 0;
					}
					firingPattern = patterns.get(currentPattern);
				}
				return toFire;
			}
		}
		return null;	//Not firing/no bullets
	}


	public Pattern getFiringPattern() {
		return firingPattern;
	}


	public float getPatternTime() {
		return patternTime;
	}



}

