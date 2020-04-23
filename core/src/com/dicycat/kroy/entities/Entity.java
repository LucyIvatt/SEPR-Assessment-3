package com.dicycat.kroy.entities;
// JS test for using git with eclipse
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.Kroy;

/**
 * Class for interactive gameObjects
 * 
 * @author Riju De
 *
 */
public abstract class Entity extends GameObject{
	protected int healthPoints;
	protected int radius;
	protected int maxHealthPoints;



	/**
	 * @param spawnPos The position the entity will spawn at.
	 * @param img The texture of the entity.
	 * @param imSize Size of the entity. Can be used to resize large/small textures
	 * @param health Hit points of the entity
	 */
	// RANGE - START OF MODIFICATION - NP STUDIOS - LUCY IVATT ------------
	// Added radius to entity constructor to be able to change the value from the hardcoded 500.
	public Entity(Vector2 spawnPos, Texture img, Vector2 imSize,int health, int radius) {
		super(spawnPos, img, imSize);
		healthPoints = health;
		maxHealthPoints = health;
		this.radius = radius;
		// RANGE - END OF MODIFICATION - NP STUDIOS - LUCY IVATT ------------
		changePosition(spawnPos);
	}

	/**
	 * Method is called every frame (If added to the GameObjects list in GameScreen)
	 */
	@Override
	public void update() {}	//Called every frame

	/**
	 * Checks if the Entity still has health and is not marked for removal
	 * @return alive Is health above 0 and is not marked for removal
	 */
	public Boolean isAlive() {
		return (healthPoints > 0) && !remove;
	}

	/**
	 * Apply x amount of damage to the entity
	 * @param damage Amount of damage to inflict on the Entity
	 */
	public void applyDamage(float damage) {
		if (damage < 0){
			throw new IllegalArgumentException("applyDamage(float damage) cannot be passed a negative float");
		}
		healthPoints -= damage;
		if (healthPoints <= 0) {
			die();
		}
	}

	/**
	 * Checks if the player is within the radius of the Entity
	 * @return playerInRadius
	 */
	protected Boolean playerInRadius() {
		Vector2 currentCoords = Kroy.mainGameScreen.getPlayer().getCentre(); // get current player coordinates
		if (Vector2.dst(currentCoords.x, currentCoords.y, getCentre().x, getCentre().y) < radius ) { // checks the distance between the two entities
			return true; // returns true if distance between entity and player is less than radius of item
		} else {
			return false; // returns false otherwise
		}
	}
	
	public int getHealthPoints(){
		return healthPoints;
	}

	//Methods added by Sam Hutchings to implement power ups.
	
	public int getMaxHealthPoints() {
		return maxHealthPoints;
	}
	
	public void refillHealth() {
		setHealthPoints(getMaxHealthPoints());
	}

	public void setHealthPoints(int healthPoints) {
		if (healthPoints <= maxHealthPoints) {
			this.healthPoints = healthPoints;
		} else
			System.err.println("healthPoints > maxHealthPoints");
	}



}
