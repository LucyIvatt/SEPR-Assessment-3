package com.dicycat.kroy.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;

/**
* Sprite used to represent where the player is shooting
* @author IsaacAlbiston
*
*/
public class WaterStream extends GameObject{

	/**
	 * initialises the water stream at a given position
	 * @param spawnPos
	 */
	public WaterStream(Vector2 spawnPos) {
		super(spawnPos, new Texture("lightBlue.png"), new Vector2(1,1));
	}
	
	/**
	 * Changes the length of the sprite to x
	 * @param x
	 */
	public void setRange(float x){
		sprite.setScale(x,2);
	}

	/**
	 *
	 */
	@Override
	public void update() {}
}
