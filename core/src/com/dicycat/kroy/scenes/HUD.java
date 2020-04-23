package com.dicycat.kroy.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;

import static java.lang.Math.abs;

/**
 * HUD window
 * 
 * @author Michele Imbriani
 *
 */
public class HUD {
	public Stage stage;
	private Viewport viewport;	//creating new port so that the HUD stays locked while map can move around independently
	private Integer trucks = 4;

	// FORTRESS_IMPROVE_2 - START OF MODIFICATION - NP STUDIOS - CASSANDRA LILLYSTONE
	//  Deleted world timer attribute and changed to timer
	public static float timer = 0;
	// FORTRESS_IMPROVE_2 - END OF MODIFICATION - NP STUDIOS

	private Integer score = 0;
	
	private Label scoreLabel;
	private Label timeLabel;
	private Label fortressLabel;
	private Label timerLabel;
	private Label scoreCountLabel;
	private Label fortressCountLabel;

	
	
	/**
	 */
	public HUD(SpriteBatch sb, float timeLimit) {
		float screenTimer = timeLimit;
		viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);	//Where we are going to put the HUD elements 
		
		Table tableHUD = new Table();	//this allows to put widgets in the scene in a clean and ordered way
		tableHUD.top();	// puts widgets from the top instead of from the centre
		tableHUD.setFillParent(true);	//makes the table the same size of the stage

		// SCREEN_COUNTDOWN_2 - START OF MODIFICATION - NP STUDIOS - CASSANDRA LILLYSTONE
		// Changed attribute being displayed in time label and changed the label text
		timerLabel = new Label(String.format("%.0f", screenTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeLabel = new Label("TIME LEFT UNTIL FIRE STATION DESTROYED:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		// SCREEN_COUNTDOWN_2 - END OF MODIFICATION - NP STUDIOS
		scoreCountLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label("SCORE:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		// FORTRESS_COUNT_1 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
		// Sets start value of fortress count to 6
		fortressLabel = new Label("FORTRESSES REMAINING:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		fortressCountLabel = new Label(String.format("%01d", 6), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		// FORTRESS_COUNT_1 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT
		

		tableHUD.add(timeLabel).expandX().padTop(10);
		tableHUD.add(timerLabel).expandX().padTop(10);
		tableHUD.add(scoreLabel).expandX().padTop(10);			// expandX so that all elements take up the same amount of space
		tableHUD.add(scoreCountLabel).expandX().padTop(10);
		// FORTRESS_COUNT_2 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
		tableHUD.add(fortressLabel).expandX().padTop(10);
		tableHUD.add(fortressCountLabel).expandX().padTop(10);
		// FORTRESS_COUNT_2 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT
		
		stage.addActor(tableHUD);
		
	}
	
	/**
	 * Increments the timer using delta time so that it will count up in seconds. Updates the labels in the HUD:
	 * the timer label, the score label and the fortress count label
	 * @param dt delta time
	 */
	public void update(float dt) {
		// SCREEN_COUNTDOWN_3 - START OF MODIFICATION - NP STUDIOS - CASSANDRA LILLYSTONE
		// Decrementing the timer shown on screen
		timer += dt;
		timerLabel.setText(String.format("%.0f", abs(Kroy.mainGameScreen.gameTimer)));
		// SCREEN_COUNTDOWN_3 - END OF MODIFICATION - NP STUDIOS

		scoreCountLabel.setText(String.format("%06d", score));

		// FORTRESS_COUNT_3 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
		// Updates the count depending on the amount of fortresses still alive
		fortressCountLabel.setText(String.format("%01d", Kroy.mainGameScreen.getFortressesCount()));
		// FORTRESS_COUNT_3 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT
	}

	public Integer getFinalScore() {
		return score;
	}

	// CODE_REFACTOR_5 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
	// Deleted unused methods
	// CODE_REFACTOR_5 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT

	/** Updates the score variable
	 * @param x	points to be added to the score
	 */
	public void updateScore(Integer x){
		score += x;
	}

	public int getScore() {
		return score;
	}
	
	/**
	 * @param score The score to display in the hud
	 */
	public void setScore(int score) {
		this.score = score;
	}

}

