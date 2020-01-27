package com.dicycat.kroy;

import com.badlogic.gdx.Game;
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
		super.render();
	}
	
	@Override
	public void dispose () {}
	
	/**
	 * Call to generate a brand new GameScreen which runs a new game
	 * @param truckNum  Selected truck
	 */
	public void newGame(int truckNum) {
		mainGameScreen = new GameScreen(this,truckNum);// Initialise new game
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
