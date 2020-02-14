package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.GameScreen;

/**
 * Friendly building which the user can return to to repair the truck or trigger the minigame to replenish water
 *
 * Edited by Lucy Ivatt - NP STUDIOS
 */
public class FireStation extends Entity {
	
	private static Texture textureLiving = Kroy.mainGameScreen.textures.getFireStation();
	private static Texture texturedead = Kroy.mainGameScreen.textures.getFireStationDead();

	public FireStation() {
		super(new Vector2(Kroy.mainGameScreen.getSpawnPosition().add(-(textureLiving.getWidth()/2), 100)),
				textureLiving, new Vector2(textureLiving.getWidth(),textureLiving.getHeight()), 100, 300);
	}

	/**
	 * Removes from active pool and displays destroyed texture
	 */
	@Override
	public void die() { 
		super.die();
		sprite.setTexture(texturedead);
		displayable = true;
	}

	/**
	 * Checks if the player is in radius. If this is the case, calls repairTruck when the truck is damaged or calls the
	 * minigame if the players water tank is not full. Also checks if the gameTimer is over and if so destroys the
	 * firestation
	 *
	 * Added by Lucy Ivatt - NP STUDIOS
	 */
	public void update(){
		if(playerInRadius()){
			Kroy.mainGameScreen.getPlayer().repairTruck();
			if (!Kroy.mainGameScreen.getPlayer().isFull()) {
				Kroy.mainGameScreen.getPlayer().refillWater();
				Kroy.mainGameScreen.newMinigame();
				Kroy.mainGameScreen.setGameState(GameScreen.GameScreenState.MINIGAME);
			}
		}
		if (Kroy.mainGameScreen.gameTimer <= 0) {		//Once timer is over
			applyDamage(100);	//Destroy fire station
		}
	}
}
