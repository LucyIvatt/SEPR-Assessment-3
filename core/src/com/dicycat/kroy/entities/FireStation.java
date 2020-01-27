package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;

/**
 * Static friendly Entity.
 * Player heals and replenishes water when within its radius.
 * 
 * @author 
 *
 */
public class FireStation extends Entity {
	
	private static Texture textureLiving = Kroy.mainGameScreen.textures.getFireStation();
	private static Texture texturedead = Kroy.mainGameScreen.textures.getFireStationDead();
	
	/**
	 * 
	 */
	public FireStation() {
		super(new Vector2(Kroy.mainGameScreen.getSpawnPosition().add(-(textureLiving.getWidth()/2), 100)), textureLiving, new Vector2(textureLiving.getWidth(),textureLiving.getHeight()), 100);
	}

	/**
	 * Removes from active pool and displays destroyed state
	 */
	@Override
	public void die() { 
		super.die();
		sprite.setTexture(texturedead);
		displayable = true;
	}

	/**
	 *
	 */
	public void update(){
		if(playerInRadius()){
			Kroy.mainGameScreen.getPlayer().replenish();
		}
		if (Kroy.mainGameScreen.gameTimer <= 0) {		//Once timer is over
			applyDamage(100);	//Destroy fire station
		}
	}
}
