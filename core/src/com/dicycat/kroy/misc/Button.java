package com.dicycat.kroy.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.MenuScreen;
import com.badlogic.gdx.graphics.Texture;

public class Button {

	private int buttonY;
	private Texture buttonActive;
	private MenuScreen menu;
	private Texture button;
	
	
	public Button(int buttonY, Texture button, Texture buttonActive, MenuScreen menu) {
		this.buttonY = buttonY;
		this.button = button;
		this.buttonActive = buttonActive;
		this.menu = menu;
	}
	
	public boolean buttonAction() {
		
		//if the mouse is on the button ...
		if(( (Gdx.input.getX() < (menu.getXAxisCentred() + menu.getButtonWidth())) && (Gdx.input.getX() > menu.getXAxisCentred()) ) && ( (Kroy.height - Gdx.input.getY() > buttonY ) && (Kroy.height - Gdx.input.getY() < (buttonY + menu.getButtonHeight())) ) ){
			// ... then display the 'controlsButtonActice' texture ...
			menu.getGame().batch.draw(buttonActive, menu.getXAxisCentred(), buttonY, menu.getButtonWidth(), menu.getButtonHeight());
			
			  // if the controls button is pressed, show the visibility of the controls window to true and set the game state to CONTROLS
			  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				  // if button pressed, return true
				  return true;
			  }
		  } else {
			// ... otherwise, display the 'controlsButton' texture (as the mouse is neither clicking nor on the button, so display the default texture)
			  menu.getGame().batch.draw(button, menu.getXAxisCentred(), buttonY, menu.getButtonWidth(), menu.getButtonHeight());
		  }
		
		// nothing is happening with the button so just return false, no actions are being performed on it
		return false;
	}
}
