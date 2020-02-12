package com.dicycat.kroy.screens;
  
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.scenes.ControlsWindow;
import com.dicycat.kroy.scenes.FireTruckSelectionScene;
import com.dicycat.kroy.minigame.Minigame;
import com.dicycat.kroy.scenes.OptionsWindow;
  
/**
 * Main Menu screen
 * 
 * @author Michele Imbriani
 *
 */
public class MenuScreen implements Screen{
  
  private Kroy game; 
  private OrthographicCamera gamecam;	//m
  private Viewport gameport; 	//m
  private Texture playButton, 
  	playButtonActive, 
  	optionsButton, 
  	optionsButtonActive, 
  	exitButton, 
  	exitButtonActive, 
  	minigameButton, 
  	minigameButtonActive, 
  	background,

	// CONTROL_SCREEN_1 - START OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
  	controlsButton, 
  	controlsButtonActive;
	// CONTROL_SCREEN_1 - END OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
  
  private Stage stage;
  
  private OptionsWindow optionsWindow;

  private Minigame minigame;

  // CONTROL_SCREEN_ 2 - START OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
  private ControlsWindow controlsWindow; 
  // CONTROL_SCREEN_2 - END OF MODIFICATION - NP STUDIOS - JORDAN SPOONER

  
  public static Music music = Gdx.audio.newMusic(Gdx.files.internal("gamemusic.mp3"));
  public static float musicVolume = 0.4f;

  //coordinates for Play and Exit buttons 
  private int buttonWidth = 250;
  private int buttonHeight = 50;
  private int xAxisCentred = (Kroy.width/2) - (buttonWidth/2);
  private int playButtonY = (Kroy.height/2)+75;
  private int optionsButtonY = (Kroy.height/2);
  private int minigameButtonY = (Kroy.height/2)-75;

  // CONTROL_SCREEN_3 - START OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
  private int controlsButtonY = (Kroy.height/2)-150;
  // CONTROL_SCREEN_3 END OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
  private int exitButtonY = (Kroy.height/2)-225;
  
  private Pixmap pm = new Pixmap(Gdx.files.internal("handHD2.png")); //cursor
  private int xHotSpot = pm.getWidth() / 3;	//where the cursor's aim is 
  private int yHotSpot = 0;
  
  private FireTruckSelectionScene fireTruckSelector;
  private boolean currentlyRunningGame = false;

  /**
   *  Used to define the current state of the screen, 
   *  MAINMENU is used mostly but then TRUCKSELECT used when the "NewGame" button has been pressed
   */
  public static enum MenuScreenState {
	  MAINMENU,
	  TRUCKSELECT,
	  OPTIONS,
	  MINIGAME,
	  // CONTROL_SCREEN_4 - START OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
	  CONTROLS // adding a new window state, the controls window
	  // CONTROL_SCREEN_4 - END OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
  }
  
  public MenuScreenState state = MenuScreenState.MAINMENU;

  public MenuScreen(Kroy game) { 
	  this.game = game; 
	  exitButton = new Texture("EXIT.png"); 	//in later stages we could also have buttonActive and buttonInactive
	  exitButtonActive = new Texture("exitActive.png");
	  optionsButton = new Texture("options.png");
	  optionsButtonActive = new Texture("optionsActive.png");
	  playButton = new Texture("newgame.png");
	  playButtonActive = new Texture("newActive.png");
	  minigameButton = new Texture("minigame.png");
	  minigameButtonActive = new Texture("minigameActive.png");

	// CONTROL_SCREEN_5 - START OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
	  controlsButton = new Texture("controls.png"); // control button texture
	  controlsButtonActive = new Texture("controls_ACTIVE.png"); // control button texture when the mouse is hovering over the button
	// CONTROL_SCREEN_5 - END OF MODIFICATION - NP STUDIOS - JORDAN SPOONER

	  background = new Texture ("fireforce.jpg");
	  
	  gamecam = new OrthographicCamera();    //m
	  gameport = new FitViewport(Kroy.width, Kroy.height, gamecam);
	  stage = new Stage(gameport);
	  
	  fireTruckSelector = new FireTruckSelectionScene(game);
	  fireTruckSelector.visibility(false);
	  
	  music.play();
	  music.setLooping(true);  
	  music.setVolume((float)musicVolume);  
	  
	  optionsWindow = new OptionsWindow(game);
	  optionsWindow.visibility(false);

	  minigame = new Minigame(game, false);
	  minigame.visibility(false);
	  
	  controlsWindow = new ControlsWindow(game);
	  controlsWindow.visibility(false);

  }
  
  @Override 
  public void show() {}
  
  /**
   *	Enum allows to make the MenuScreen behave differently depending 
   *	on whether it's in mainMenu, Options or fireTruckSelection
   */
  @Override 
  public void render(float delta) { 
	  
	  switch(state) {
		  case MAINMENU:	// Display all buttons and the main menu		  
			  stage.act();	//allows the stage to interact with user input
			  
			  game.batch.setProjectionMatrix(gamecam.combined);
			  game.batch.begin();
			  
			  Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot));
			  game.batch.draw(background, 0, 0);
			 
			  game.batch.draw(minigameButton, xAxisCentred, minigameButtonY, buttonWidth, buttonHeight);
			
			
			  //for play button: checks if the position of the cursor is inside the coordinates of the button
			  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > playButtonY ) && (Kroy.height - Gdx.input.getY() < (playButtonY + buttonHeight)) ) ){
				  game.batch.draw(playButtonActive, xAxisCentred, playButtonY, buttonWidth, buttonHeight);
				  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					  this.dispose();
					  game.batch.end();
					  fireTruckSelector.visibility(true);// display the truck selection window
					  setGameState(MenuScreenState.TRUCKSELECT);// set the game state to run and run the selection screen code
					  return;
				  }
			  } else {
				  game.batch.draw(playButton, xAxisCentred, playButtonY, buttonWidth, buttonHeight);
			  }
			  
			//for exit button
			  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > exitButtonY ) && (Kroy.height - Gdx.input.getY() < (exitButtonY + buttonHeight)) ) ){
				  game.batch.draw(exitButtonActive, xAxisCentred, exitButtonY, buttonWidth, buttonHeight);
				  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					  Gdx.app.exit();
				  }
			  } else {
				  game.batch.draw(exitButton, xAxisCentred, exitButtonY, buttonWidth, buttonHeight);
			  }
			  // MINIGAME_IMPLEMENTATION_1 - START OF MODIFICATION - NP STUDIOS - BETHANY GILMORE
			  //for minigame button
			  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > minigameButtonY ) && (Kroy.height - Gdx.input.getY() < (minigameButtonY + buttonHeight)) ) ){
				  game.batch.draw(minigameButtonActive, xAxisCentred, minigameButtonY, buttonWidth, buttonHeight);
				  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				  	  minigame = new Minigame(game, false);
					  minigame.visibility(true);
					  setGameState(MenuScreenState.MINIGAME);
						  }
					  } else {
						  game.batch.draw(minigameButton, xAxisCentred, minigameButtonY, buttonWidth, buttonHeight);
					  }
			  // MINIGAME_IMPLEMENTATION_1 - END OF MODIFICATION - NP STUDIOS - BETHANY GILMORE
						  //for options button
			  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > optionsButtonY ) && (Kroy.height - Gdx.input.getY() < (optionsButtonY + buttonHeight)) ) ){
				  game.batch.draw(optionsButtonActive, xAxisCentred, optionsButtonY, buttonWidth, buttonHeight);
				  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					  //game.batch.end();
					  optionsWindow.visibility(true);
					  setGameState(MenuScreenState.OPTIONS);
				  }
			  } else {
				  game.batch.draw(optionsButton, xAxisCentred, optionsButtonY, buttonWidth, buttonHeight);
			  }
			  
			  // CONTROL_SCREEN_6 - START OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
			  // for controls button
			  
			  //if the mouse is on the button ...
			  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > controlsButtonY ) && (Kroy.height - Gdx.input.getY() < (controlsButtonY + buttonHeight)) ) ){
				  // ... then display the 'controlsButtonActice' texture ...
				  game.batch.draw(controlsButtonActive, xAxisCentred, controlsButtonY, buttonWidth, buttonHeight);
				  
				  // if the controls button is pressed, show the visibility of the controls window to true and set the game state to CONTROLS
				  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					  controlsWindow.visibility(true);
					  setGameState(MenuScreenState.CONTROLS);
				  }
			  } else {
				  // ... otherwise, display the 'controlsButton' texture
				  game.batch.draw(controlsButton, xAxisCentred, controlsButtonY, buttonWidth, buttonHeight);
			  }
			  // CONTROL_SCREEN_6 - END OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
			  
			  game.batch.end();
				  
			  break;
		  case TRUCKSELECT: // Ran when the new game button pressed
			  Gdx.input.setInputProcessor(fireTruckSelector.stage);
			  fireTruckSelector.stage.act();
			  fireTruckSelector.stage.draw();
			  clickCheck();//Checks for any button presses
			  break;
		  case OPTIONS:
			  Gdx.input.setInputProcessor(optionsWindow.stage);
			  optionsWindow.stage.act();
			  optionsWindow.stage.draw();
			  optionsWindow.clickCheck(true);
			  break;
		  // MINIGAME_IMPLEMENTATION_2 - START OF MODIFICATION - NP STUDIOS - BETHANY GILMORE
		  case MINIGAME:
		  	  Gdx.input.setInputProcessor(minigame.stage);
		  	  minigame.stage.act();
		  	  minigame.stage.draw();
		  	  minigame.clickCheck();
		  	  break;
		  // MINIGAME_IMPLEMENTATION_2 - END OF MODIFICATION - NP STUDIOS - BETHANY GILMORE

		  // CONTROL_SCREEN_7 - START OF MODIFICATION - NP STUDIOS - JORDAN SPOONER ------------------------------------------------------------------
		  // Modification name: control_screen8
		  case CONTROLS: 
		  	  Gdx.input.setInputProcessor(controlsWindow.stage); // set inputs from the user only valid to the controlsWindow
		  	  controlsWindow.stage.act();
		  	  controlsWindow.stage.draw(); // draw the window
		  	  controlsWindow.clickCheck(); // constantly check for user inputs from the mouse
		  	  break;
		  // CONTROL_SCREEN_7 - END OF MODIFICATION - NP STUDIOS - JORDAN SPOONER --------------------------------------------------------------------
		  }
  	}

	public void setGameState(MenuScreenState state){
	    this.state = state;
	}
  
	/**
	 * Checks if any of the buttons have been pressed
	 * and the number of the fireTruck type is passed to the new GameScreen
	 */
	public void clickCheck() {
		// TRUCK_SELECT_CHANGE_19 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
		// Start Game Button click event
		fireTruckSelector.startGameButton.addListener(new ClickListener() {
			@Override
	    	public void clicked(InputEvent event, float x, float y) {
				startGame();// Starts game
	    	}
	    });

		// Deleted click check events for the buttons which used to be used to select the firetruck you wanted.

		// TRUCK_SELECT_CHANGE_19 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----
	}


	/**
	 * If the game isn't currently running, creates a new game
 	 */
	// TRUCK_SELECT_CHANGE_20 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
	// Removed unused parameters which were modified elsewhere
	public void startGame() {
		 if (!currentlyRunningGame) {	// Checks if a new GameScren is currently running and either makes one or ignores the commands
			 currentlyRunningGame = true; // Makes sure that only one GameScreen is opened at once
			 game.newGame(); // Calls the function in Kroy to start a new game
		 }
	}
	// TRUCK_SELECT_CHANGE_20 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----

  public void setCurrentlyRunningGame(boolean state) {
	  currentlyRunningGame = state;
  }

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

