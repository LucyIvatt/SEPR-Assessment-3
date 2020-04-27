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
import com.dicycat.kroy.misc.Button;
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
  private Texture background,
// REFACTOR_CHANGE_1 - START OF MODIFICATION - NP STUDIOS - JORDAN SPOONER ---
  // renamed these variables to have a 'Texture' suffix as it is more conventional as these variables are no button objects
  // later on in this class, 'playButton' button instance can be seen for example and deserves this name more
    playButtonTexture, 
  	playButtonActiveTexture, 
  	optionsButtonTexture, 
  	optionsButtonActiveTexture, 
  	exitButtonTexture, 
  	exitButtonActiveTexture, 
  	minigameButtonTexture, 
  	minigameButtonActiveTexture, 
// REFACTOR_CHANGE_1 - END OF MODIFICATION - NP STUDIOS - JORDAN SPOONER -----

	// CONTROL_SCREEN_1 - START OF MODIFICATION - NP STUDIOS - JORDAN SPOONER ---
	// added the controls screen and need the button textures to be stored in these variables
  	controlsButtonTexture, 
  	controlsButtonActiveTexture;
	// CONTROL_SCREEN_1 - END OF MODIFICATION - NP STUDIOS - JORDAN SPOONER -----
  
  private Stage stage;
  
  private OptionsWindow optionsWindow;

  private Minigame minigame;

  // CONTROL_SCREEN_ 2 - START OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
  	// creating an instance of the ControlsWindow class
  private ControlsWindow controlsWindow; 
  // CONTROL_SCREEN_2 - END OF MODIFICATION - NP STUDIOS - JORDAN SPOONER

  
  public static Music music = Gdx.audio.newMusic(Gdx.files.internal("gamemusic.mp3"));
  public static float musicVolume = 0.5f;

  //coordinates for Play and Exit buttons 
  private int buttonWidth = 250;
  private int buttonHeight = 50;
  private int xAxisCentred = (Kroy.width/2) - (buttonWidth/2);
  private int playButtonY = (Kroy.height/2)+75;
  private int optionsButtonY = (Kroy.height/2);
  private int minigameButtonY = (Kroy.height/2)-75;

  // CONTROL_SCREEN_3 - START OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
  	// assigning this variable the y coordinate for where the controlsButton button instance will be placed on the main menu
  private int controlsButtonY = (Kroy.height/2)-150;
  // CONTROL_SCREEN_3 END OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
  private int exitButtonY = (Kroy.height/2)-225;
  
  private Pixmap pm = new Pixmap(Gdx.files.internal("handHD2.png")); //cursor
  private int xHotSpot = pm.getWidth() / 3;	//where the cursor's aim is 
  private int yHotSpot = 0;
  
  private FireTruckSelectionScene fireTruckSelector;
  private boolean currentlyRunningGame = false;
  
  public static int gameModeSelect;

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
	  CONTROLS, // adding a new window state, the controls window, has the code that calls the creation and setup of the controls window
	  // CONTROL_SCREEN_4 - END OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
	  LOADGAME
  }
  
  public MenuScreenState state = MenuScreenState.MAINMENU;

  public MenuScreen(Kroy game) { 
	  this.game = game; 
	  exitButtonTexture = new Texture("EXIT.png"); 	//in later stages we could also have buttonActive and buttonInactive
	  exitButtonActiveTexture = new Texture("exitActive.png");
	  optionsButtonTexture = new Texture("options.png");
	  optionsButtonActiveTexture = new Texture("optionsActive.png");
	  playButtonTexture = new Texture("newgame.png");
	  playButtonActiveTexture = new Texture("newActive.png");
	  minigameButtonTexture = new Texture("minigame.png");
	  minigameButtonActiveTexture = new Texture("minigameActive.png");

	// CONTROL_SCREEN_5 - START OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
	  controlsButtonTexture = new Texture("controls.png"); // control button texture
	  controlsButtonActiveTexture = new Texture("controls_ACTIVE.png"); // control button texture when the mouse is hovering over the button
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
			 
			  game.batch.draw(minigameButtonTexture, xAxisCentred, minigameButtonY, buttonWidth, buttonHeight);
			
			
			  // REFACTOR_CHANGE_2 - START OF MODIFICATION - NP STUDIOS - JORDAN SPOONER ------------------------------------------------------------------
			  
				// In this refactor, the format of the buttons has been changed to be more readable and easier to use.
				// To create a button you create a Button object and pass the constructor its y coordinate, image,
				// an on-hover image and the game instance that the button exists in.
				// The following if statements check if the button has been clicked and execute the necessary code if so
			  
			  //for play button: checks if the position of the cursor is inside the coordinates of the button
			  Button playButton = new Button(playButtonY, playButtonTexture, playButtonActiveTexture, game);
			  if (playButton.buttonAction()) {
				  this.dispose();
				  game.batch.end();
				  fireTruckSelector.visibility(true);// display the truck selection window
				  setGameState(MenuScreenState.TRUCKSELECT);// set the game state to run and run the selection screen code
				  return;
			  }
			  
			  
			  //for exit button
			  		// button created
			  Button test_exitButton = new Button(exitButtonY, exitButtonTexture, exitButtonActiveTexture, game);
			  		// if the button is pressed, execute the command inside the if statement
			  if (test_exitButton.buttonAction()) {
				  Gdx.app.exit();
			  }

			  // MINIGAME_IMPLEMENTATION_1 - START OF MODIFICATION - NP STUDIOS - BETHANY GILMORE
			  Button minigameButton = new Button(minigameButtonY, minigameButtonTexture, minigameButtonActiveTexture, game);
			  if (minigameButton.buttonAction()) {
				  minigame.visibility(true);
				  setGameState(MenuScreenState.MINIGAME);
			  // MINIGAME_IMPLEMENTATION_1 - END OF MODIFICATION - NP STUDIOS - BETHANY GILMORE
			  }
			  
			  //for options button
			  Button optionsButton = new Button(optionsButtonY, optionsButtonTexture, optionsButtonActiveTexture, game);
			  if (optionsButton.buttonAction()) {
				//game.batch.end();
				  optionsWindow.visibility(true);
				  setGameState(MenuScreenState.OPTIONS);
			  }
			  
			  // REFACTOR_CHANGE_2 - END OF MODIFICATION - NP STUDIOS - JORDAN SPOONER --------------------------------------------------------------------
			 
			  
			  // CONTROL_SCREEN_6 - START OF MODIFICATION - NP STUDIOS - JORDAN SPOONER
			  // for controls button
			  
			  // added a button to click, to direct the user to the controls window if clicked
			  
			  Button controlsButton = new Button(controlsButtonY, controlsButtonTexture, controlsButtonActiveTexture, game);
			  if (controlsButton.buttonAction()) {
				  controlsWindow.visibility(true);
				  setGameState(MenuScreenState.CONTROLS);
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
			  // CONTROLS switch statement added to set up the controls window
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
//		fireTruckSelector.startGameButton.addListener(new ClickListener() {
//			@Override
//	    	public void clicked(InputEvent event, float x, float y) {
//				startGame();// Starts game
//	    	}
//	    });
		
		fireTruckSelector.easyGameButton.addListener(new ClickListener() {
			@Override
	    	public void clicked(InputEvent event, float x, float y) {
				gameModeSelect = 1;
				startGame();// Starts game
	    	}
	    });
		
		fireTruckSelector.normalGameButton.addListener(new ClickListener() {
			@Override
	    	public void clicked(InputEvent event, float x, float y) {
				gameModeSelect = 2;
				startGame();// Starts game
	    	}
	    });
		
		fireTruckSelector.hardGameButton.addListener(new ClickListener() {
			@Override
	    	public void clicked(InputEvent event, float x, float y) {
				gameModeSelect = 3;
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



