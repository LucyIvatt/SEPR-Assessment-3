package com.dicycat.kroy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dicycat.kroy.screens.GameScreen;
import com.dicycat.kroy.screens.MenuScreen;

import java.util.Map;

/**
 * Main game class
 * 
 * @author Riju De
 *
 */

public class Kroy extends Game {

	// GAME_SIZE - START OF MODIFICATION - NP STUDIOS - LUCY IVATT -----------------------------------------
	// Made the game window larger
	public static final int width = 1280; //Changed window size to run better on MacOS
	public static final int height = 720;
	// GAME_SIZE - END OF MODIFICATION - NP STUDIOS - LUCY IVATT -----------------------------------------
	
	public static GameScreen mainGameScreen;
	public static MenuScreen mainMenuScreen;
	// HIGHSCORE_1 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT -----------------------------------------
	public static Preferences highscore;
	// HIGHSCORE_1 - END OF MODIFICATION  - NP STUDIOS -----------------------------------------
	public SpriteBatch batch;
	
	@Override
	public void create () {
		// HIGHSCORE_2 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT -----------------------------------------
		highscore = Gdx.app.getPreferences("Kroy_Highscore"); // Loaded save file and deleted to-comment
		// HIGHSCORE_2 - END OF MODIFICATION  - NP STUDIOS -----------------------------------------
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
		mainGameScreen = new GameScreen(this, null);// Initialise new game
	// TRUCK_SELECT_CHANGE_4 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----
		setScreen(mainGameScreen);// Display new game
	}


	/**
	 * Resume a game with entities
	 */
	public void resumeSavedGame(Preferences load) {
		mainGameScreen = new GameScreen(this, load);
		setScreen(mainGameScreen);
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
		// HIGHSCORE_3 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT -----------------------------------------
		if (highScore > highscore.getInteger("highscore", 0)) { // If the new score is bigger than the saved highscore
			highscore.putInteger("highscore", highScore); // replace the saved highscore with the newly achieves score
			highscore.flush(); // updates the file
		}
		// HIGHSCORE_3 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT -----------------------------------------
	}
	
	/**
	 * Get the current high score
	 * @return highScore
	 */
	public Integer getHighScore() {
		// HIGHSCORE_4 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT -----------------------------------------
		return highscore.getInteger("highscore", 0); // accesses high-score file and returns integer value
		// HIGHSCORE_4 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT -----------------------------------------
	}
}
