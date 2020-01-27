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
	private Integer worldTimer = 0;	//change to float maybe
	private Integer score = 0;
	private float timeCount = 0;
	
	private Label scoreLabel;
	private Label timeLabel;
	private Label trucksLabel;
	private Label worldTimerLabel;
	private Label scoreCountLabel;
	private Label trucksCountLabel;	//we could put mini images of the trucks instead of using an int for the lives
	
	
	/**
	 * @param sb	SpriteBatch
	 * @param game	Kroy instance
	 */
	public HUD(SpriteBatch sb, Kroy game) {
		viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);	//Where we are going to put the HUD elements 
		
		Table tableHUD = new Table();	//this allows to put widgets in the scene in a clean and ordered way
		tableHUD.top();	// puts widgets from the top instead of from the centre
		tableHUD.setFillParent(true);	//makes the table the same size of the stage
		
		worldTimerLabel = new Label(String.format("%05d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeLabel = new Label("TIME:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreCountLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label("SCORE:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		trucksLabel = new Label("TRUCKS:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		trucksCountLabel = new Label(String.format("%01d", trucks), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		

		tableHUD.add(timeLabel).expandX().padTop(10);
		tableHUD.add(worldTimerLabel).expandX().padTop(10);
		tableHUD.add(scoreLabel).expandX().padTop(10);			// expandX so that all elements take up the same amount of space
		tableHUD.add(scoreCountLabel).expandX().padTop(10);
		tableHUD.add(trucksLabel).expandX().padTop(10);
		tableHUD.add(trucksCountLabel).expandX().padTop(10);
		
		stage.addActor(tableHUD);
		
	}
	
	/**
	 * Using delta time allows to operate with the real-world time (seconds)
	 * rather than the in-game time (which is computed using frames)
	 * 
	 * @param dt	Delta Time 
	 */
	public void update(float dt) {
		timeCount += dt;
		if (timeCount >= 1) {
			worldTimer++;
			worldTimerLabel.setText(String.format("%05d", worldTimer));
			timeCount =0;
			scoreCountLabel.setText(String.format("%06d", score));
			trucksCountLabel.setText(String.format("%01d", Kroy.mainGameScreen.getLives()));
		}
	}


	public Integer getFinalScore() {
		return score;
	}

	public void setScore(Integer x){
		score = x;
	}

	public Integer getScore(){
		return score;
	}

	/**
	 * @param x		Points to be added to the score
	 */
	public void updateScore(Integer x){
		score += x;
	}
}

