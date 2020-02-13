package com.dicycat.kroy.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;

/**
* 
* Sprites used to display statistics for the player
* @author IsaacAlbiston
*
*/
public class StatBar extends GameObject{

	private int height;	//Height of the bar
	
	/**
	 * initialises the bar
	 * @param spawnPos
	 * @param texture
	 * @param height
	 */
	public StatBar(Vector2 spawnPos, String texture, int height) {
		super(spawnPos, new Texture(texture), new Vector2(1,1));
		this.height = height;
	}
	
	/**
	 * Changes the width of the bar to x
	 * @param x
	 */
	public void setBarDisplay(float x){
		sprite.setScale(x,height);
	}

	public void update() {}

	public int getStatBarHeight() {
		return height;
	}
}
