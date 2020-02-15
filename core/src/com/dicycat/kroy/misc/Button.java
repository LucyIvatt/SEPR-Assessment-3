package com.dicycat.kroy.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dicycat.kroy.Kroy;
import com.badlogic.gdx.graphics.Texture;

/**
* Class which handles the buttons used on the main menu. Contains the two textures need for active and incactive states
 * as well as the y coordinate. X coordinate is not needed as menu buttons are centered to the screen.
* @author Jordan Spooner - NP STUDIOS
*
*/

public class Button {

	private int buttonY;
	private Texture buttonActive;
	private Texture button;
	private Kroy game;
	
	private final int BUTTON_WIDTH = 250;
	private final int BUTTON_HEIGHT = 50;

	private int xAxisCentred = (Kroy.width/2) - (BUTTON_WIDTH /2);

	public Button(int buttonY, Texture button, Texture buttonActive, Kroy game) {
		this.buttonY = buttonY;
		this.button = button;
		this.buttonActive = buttonActive;
		this.game = game;
	}
	
	/**
	 * Button code, returns true if the button is clicked and false otherwise
	 * Also, if the mouse is on the button, the button changes to a different image of the button.
	 */
	public boolean buttonAction() {
		//if the mouse is on the button ...
		if(( (Gdx.input.getX() < (xAxisCentred + BUTTON_WIDTH)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > buttonY ) && (Kroy.height - Gdx.input.getY() < (buttonY + BUTTON_HEIGHT)) ) ){
			// ... then display the 'controlsButtonActice' texture ...
			game.batch.draw(buttonActive, xAxisCentred, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT);
			
			  // if the controls button is pressed, show the visibility of the controls window to true and set the game state to CONTROLS
			  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				  // if button pressed, return true
				  return true;
			  }
		  } else {
			// ... otherwise, display the 'controlsButton' texture (as the mouse is neither clicking nor on the button, so display the default texture)
			  game.batch.draw(button, xAxisCentred, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT);
		  }
		
		// nothing is happening with the button so just return false, no actions are being performed on it
		return false;
	}
}
