package com.dicycat.kroy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dicycat.kroy.screens.GameScreen;
import com.dicycat.kroy.screens.MenuScreen;

/**
 * Main game class
 * 
 * @author Riju De
 *
 */

public class Kroy extends Game {
	public static final int width = 1080;
	public static final int height = 720;
	
	public static GameScreen mainGameScreen;
	public static MenuScreen mainMenuScreen;
	public SpriteBatch batch;
	
	private Integer highScore;
	
	@Override
	public void create () {
		highScore = 5000;		//TODO: Load high score from external 
		batch = new SpriteBatch();
		mainMenuScreen = new MenuScreen(this);
		this.setScreen(mainMenuScreen);
	}

	@Override
	public void render () {
		// WARPING - START OF MODIFICATION - NP STUDIOS - LUCY IVATT -----------------------------------------
		// Added in to prevent warping at the edge of the game map
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// WARPING - END OF MODIFICATION  - NP STUDIOS -----------------------------------------

		super.render();
	}
	
	@Override
	public void dispose () {}
	
	/**
	 * Call to generate a brand new GameScreen which runs a new game
	 */
	// TRUCK_SELECT_CHANGE_4- START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
	// Deleted truck num parameter as it is no longer needed because the user starts with 1 of each truck rather than
	// choosing one and having multiple lives.
	public void newGame() {
		mainGameScreen = new GameScreen(this);// Initialise new game
	// TRUCK_SELECT_CHANGE_4 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----
		setScreen(mainGameScreen);// Display new game
	}

	/**
	 * Return back to the menu screen
	 */
	public void backToMenu() {
		mainMenuScreen.state = MenuScreen.MenuScreenState.MAINMENU; // sets menu screen back to the original state
		mainMenuScreen.setCurrentlyRunningGame(false); //Tells the screen not to block any button pushes which would initialisze a new game again
		setScreen(mainMenuScreen); // displays the menu screen
	}
	
	/**
	 * Centre of the screen width
	 * @return centre of the screen width
	 */
	public static int CentreWidth() {
		return width / 3;
	}
	
	/**
	 * Set the high score
	 * @param highScore The new high score
	 */
	public void setHighScore(Integer highScore) {
		this.highScore = highScore;
		//TODO: Write new high score to external
	}
	
	/**
	 * Get the current high score
	 * @return highScore
	 */
	public Integer getHighScore() {
		return highScore;
	}
}
