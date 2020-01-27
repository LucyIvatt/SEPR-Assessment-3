package com.dicycat.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;

/**
* 
* @author 
*
*/
public class GameOverScreen implements Screen{
	
	private Kroy game; 
	  private OrthographicCamera gamecam;	//m
	  private Viewport gameport; 	//m
	  
	  private Stage stage;
	  
	  public boolean result; //to change based on whether game is won or lost
	  
	  private Texture gameOverImage= new Texture("gameover.png");
	  private Texture youWonImage= new Texture("youwon.png");
	  private Texture youLostImage= new Texture("youlost.png");
	  private Texture playButton= new Texture("newgame.png");
	  private Texture playButtonActive= new Texture("newActive.png");
	  private Texture menuButton = new Texture("menu.png");
	  private Texture menuButtonActive = new Texture("menuActive.png");
	  
	  private Integer score;
	  private Integer highScore; 
	  private Label scoreLabel;
	  private Label scoreNumberLabel;
	  private Label highScoreLabel;
	  private Label highScoreNumberLabel;
	  private Integer scaleScore = 2;
	  private float padScore;
	  private float padTop;
	  private int truckNum;

	  //coordinates for gameoverIMG, Play and Exit buttons 
	  private int gameOverImageWidth = 400;
	  private int gameOverImageHeight= 200;
	  private int gameOverImageY = ((Kroy.height/2)+75);
	  private int gameOverImageXAxisCentred = (Kroy.width/2) - (gameOverImageWidth/2);
	  
	  private int resultingImageWidth = 300;
	  private int resultingImgaeHeight= 100;
	  private int resultImageY = ((Kroy.height/2)-20);
	  private int resultImageXAxisCentred = (Kroy.width/2) - (resultingImageWidth/2);
	  
	  private int buttonWidth = 250;
	  private int buttonHeight = 50;
	  private int xAxisCentred = (Kroy.width/2) - (buttonWidth/2);
	  private int playButtonX = ((Kroy.height/2)-150);
	  private int minigameButtonY = (Kroy.height/2)-225;
	  
	  private Pixmap pm = new Pixmap(Gdx.files.internal("handHD2.png")); //cursor
	  private int xHotSpot = pm.getWidth() / 3;	//where the cursor's aim is 
	  private int yHotSpot = 0;
	  
	  
	  /**
	 * @param game
	 * @param truckNum
	 * @param result
	 */
	public GameOverScreen(Kroy game, int truckNum, Boolean result) { 
		  this.game = game; 
		  this.result = result;
		  gamecam = new OrthographicCamera();    //m
		  gameport = new FitViewport(Kroy.width, Kroy.height, gamecam);
		  stage = new Stage(gameport);
		  this.truckNum = truckNum;
		  
		  Table table = new Table();	//this allows to put widgets in the scene in a clean and ordered way
		  table.setFillParent(true);
		  table.top();
		  
		  score = Kroy.mainGameScreen.getHud().getFinalScore();
		  highScore = game.getHighScore();
		  if (score > highScore) {
			  highScore = score;
			  game.setHighScore(highScore);
		  }
		  
		  scoreLabel = new Label("YOUR SCORE:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		  scoreNumberLabel = new Label(String.format("%05d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		  highScoreLabel = new Label("HIGH SCORE:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		  highScoreNumberLabel = new Label(String.format("%05d", highScore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		  
		  padScore = (Kroy.width/2)-scoreLabel.getWidth()-10;
		  padTop = (Kroy.height/2);

		  scoreLabel.setFontScale(scaleScore);
		  scoreNumberLabel.setFontScale(scaleScore);
		  highScoreLabel.setFontScale(scaleScore);
		  highScoreNumberLabel.setFontScale(scaleScore);
		  
		  table.add(scoreLabel).padLeft(padScore).padTop(padTop);
		  table.add(scoreNumberLabel).padRight(padScore).padTop(padTop);
		  table.row();
		  table.add(highScoreLabel).padLeft(padScore);
		  table.add(highScoreNumberLabel).padRight(padScore);
		  
		  stage.addActor(table);
	  }
	  
	  @Override 
	  public void show() {}
	  
	  /**
	   *
	   */
	  @Override 
	  public void render(float delta) {
		  stage.act();	//allows the stage to interact with user input
		  
		  game.batch.setProjectionMatrix(gamecam.combined);
		  game.batch.begin();
		  
		  Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot));
		 
		  game.batch.draw(gameOverImage, gameOverImageXAxisCentred, gameOverImageY, gameOverImageWidth, gameOverImageHeight);
		  
		  if (result) {
			  game.batch.draw(youWonImage, resultImageXAxisCentred, resultImageY, resultingImageWidth, resultingImgaeHeight);
		  } else {
			  game.batch.draw(youLostImage, resultImageXAxisCentred, resultImageY, resultingImageWidth, resultingImgaeHeight);
		  }
		  

		  //for play button: checks if the position of the cursor is inside the coordinates of the button
		  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > playButtonX ) && (Kroy.height - Gdx.input.getY() < (playButtonX + buttonHeight)) ) ){
			  game.batch.draw(playButtonActive, xAxisCentred, playButtonX, buttonWidth, buttonHeight);
			  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				  this.dispose();
				  game.batch.end();
				  game.newGame(truckNum);
				  return;
			  }
		  } else {
			  game.batch.draw(playButton, xAxisCentred, playButtonX, buttonWidth, buttonHeight);
		  }
		  
		
			
		  //for minigame button
		  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > minigameButtonY ) && (Kroy.height - Gdx.input.getY() < (minigameButtonY + buttonHeight)) ) ){
			  game.batch.draw(menuButtonActive, xAxisCentred, minigameButtonY, buttonWidth, buttonHeight);
			  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				  dispose();
				  game.backToMenu();
			  }
		  } else {
			  game.batch.draw(menuButton, xAxisCentred, minigameButtonY, buttonWidth, buttonHeight);
		  }
		  game.batch.end();
		  
		  stage.draw();

		  
	  	}
	  
	  /**
	   *
	   */
	  @Override 
	  public void resize(int width, int height) { 
		  gameport.update(width, height);
	  }
	  
	  @Override 
	  public void pause() {}
	  
	  @Override 
	  public void resume() {}
	  
	  @Override 
	  public void hide() {}
	  
	  @Override 
	  public void dispose() {}
	  
}
	 


