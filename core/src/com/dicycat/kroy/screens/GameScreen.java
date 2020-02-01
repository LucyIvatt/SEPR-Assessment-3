package com.dicycat.kroy.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.debug.DebugCircle;
import com.dicycat.kroy.debug.DebugDraw;
import com.dicycat.kroy.debug.DebugLine;
import com.dicycat.kroy.debug.DebugRect;
import com.dicycat.kroy.entities.FireStation;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.entities.Fortress;
import com.dicycat.kroy.gamemap.TiledGameMap;
import com.dicycat.kroy.misc.WaterStream;
import com.dicycat.kroy.scenes.HUD;
import com.dicycat.kroy.scenes.OptionsWindow;
import com.dicycat.kroy.scenes.PauseWindow;


/**
 * Contains the main game logic
 * 
 * @author Riju De
 * @author lnt20
 *
 */
public class GameScreen implements Screen{

	public static enum GameScreenState{
		PAUSE,
		RUN,
		RESUME,
		OPTIONS
	}
	
	public Kroy game;
	public GameTextures textures;
	public static Boolean showDebug = false;
	public float gameTimer; //Timer to destroy station
	
	
	public GameScreenState state = GameScreenState.RUN;
	
	public static TiledGameMap gameMap;
	
	private OrthographicCamera gamecam;	//follows along what the port displays
	private Viewport gameport;
	
	private HUD hud;
	private PauseWindow pauseWindow;
	private OptionsWindow optionsWindow;

	private Float[][] truckStats = {	//Each list is a configuration of a specific truck. {speed, flowRate, capacity, range}
			{450f, 0.5f, 400f, 300f},		//Speed
			{300f, 1f, 400f, 300f},	//Flow rate
			{300f, 0.5f, 500f, 300f},		//Capacity
			{300f, 0.5f, 400f, 450f}		//Range
		};
	
	
	private int activeTruck; // Identifies the truck thats selected in the menu screen
	private ArrayList<FireTruck> players;
	private boolean[] alivePlayers;
	private int lives = 4;
	
	private int fortressesCount;
	private Vector2 spawnPosition;	//Coordinates the player spawns at
	
	private List<GameObject> gameObjects, deadObjects;	//List of active game objects
	private List<GameObject> objectsToRender = new ArrayList<GameObject>(); // List of game objects that have been updated but need rendering
	private List<GameObject> objectsToAdd;
	private List<DebugDraw> debugObjects; //List of debug items



	/**
	 * @param _game
	 */
	public GameScreen(Kroy _game) {
		game = _game;
		gamecam = new OrthographicCamera();
		gameport = new FitViewport(Kroy.width, Kroy.height, gamecam);	//Mic:could also use StretchViewPort to make the screen stretch instead of adapt
		hud = new HUD(game.batch, this.game);
		gameMap = new TiledGameMap();										//or FitPort to make it fit into a specific width/height ratio
		pauseWindow = new PauseWindow(game);
		pauseWindow.visibility(false);
		optionsWindow = new OptionsWindow(game);
		optionsWindow.visibility(false);
		textures = new GameTextures();
		spawnPosition = new Vector2(3750, 4000);
		gameTimer = 60 * 15; //Set timer to 15 minutes
		players = new ArrayList<>();
	}

	/**
	 * Screen first shown
	 */
	@Override
	public void show() {
		objectsToAdd = new ArrayList<GameObject>();
		gameObjects = new ArrayList<GameObject>();
		deadObjects = new ArrayList<GameObject>();
		debugObjects = new ArrayList<DebugDraw>();

		players.add(new FireTruck(new Vector2(spawnPosition.x + 50, spawnPosition.y), truckStats[0], 0));
		players.add(new FireTruck(new Vector2(spawnPosition.x - 50, spawnPosition.y), truckStats[1], 1));
		players.add(new FireTruck(new Vector2(spawnPosition.x, spawnPosition.y), truckStats[2], 2));
		players.add(new FireTruck(new Vector2(spawnPosition.x, spawnPosition.y - 50), truckStats[3], 3));
		alivePlayers = new boolean[]{true, true, true, true};

		for (FireTruck truck : players) {
			gameObjects.add(truck);	//Player
		}

		gamecam.translate(new Vector2(players.get(activeTruck).getX(),players.get(activeTruck).getY())); // sets initial Camera position
		
		gameObjects.add(new FireStation());
		// FORTRESS_HEALTH_1 - START OF MODIFICATION - NP STUDIOS - CASSANDRA LILLYSTONE ----
		// Added health and damage values for each fortress instantiation
		gameObjects.add(new Fortress(new Vector2(2903,3211),textures.getFortress(0), textures.getDeadFortress(0),
				new Vector2(256, 218), 400, 10));
		gameObjects.add(new Fortress(new Vector2(3200,5681), textures.getFortress(1), textures.getDeadFortress(1),
				new Vector2(256, 320), 500, 20));
		gameObjects.add(new Fortress(new Vector2(2050,1937), textures.getFortress(2), textures.getDeadFortress(2),
				new Vector2(400, 240), 600, 30));
		// FORTRESS_HEALTH_1 - END OF MODIFICATION - NP STUDIOS
	}

	/**
	 * Called every frame
	 */
	public void render(float delta) {
		Gdx.input.setInputProcessor(pauseWindow.stage);  //Set input processor
		pauseWindow.stage.act();

		switch (state) {
			case RUN:
				if (Gdx.input.isKeyPressed(Keys.P) || Gdx.input.isKeyPressed(Keys.O) || Gdx.input.isKeyPressed(Keys.M)|| Gdx.input.isKeyPressed(Keys.ESCAPE)){
					pauseWindow.visibility(true);
					pause();
				}
				if (Gdx.input.isKeyPressed(Keys.NUM_1) && alivePlayers[0]) {
					activeTruck = 0;
				}
				else if (Gdx.input.isKeyPressed(Keys.NUM_2) && alivePlayers[1]) {
					activeTruck = 1;
				}
				else if (Gdx.input.isKeyPressed(Keys.NUM_3) && alivePlayers[2]) {
					activeTruck = 2;
				}
				else if (Gdx.input.isKeyPressed(Keys.NUM_4) && alivePlayers[3]) {
					activeTruck = 3;
				}

				selectTruck();

				gameTimer -= delta;		//Decrement timer

				updateLoop(); //Update all game objects positions but does not render them as to be able to render everything as quickly as possible

				gameMap.renderRoads(gamecam); // Render the background roads, fields and rivers

				game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
				game.batch.setProjectionMatrix(gamecam.combined);	//Mic:only renders the part of the map where the camera is
				game.batch.begin(); // Game loop Start

				hud.update(delta);

				renderObjects(); // Renders objects specified in the UpdateLoop() called previously

				game.batch.end();

				gameMap.renderBuildings(gamecam); // Renders the buildings and the foreground items which are not entities

				hud.stage.draw();
				pauseWindow.stage.draw();

				if (showDebug) {
					DrawDebug(); //Draw all debug items as they have to be drawn outside the batch
				}

				break;
			case PAUSE:
				pauseWindow.stage.draw();
				clickCheck();
				break;
			case RESUME:
				pauseWindow.visibility(false);
				setGameState(GameScreenState.RUN);
				break;
			default:
				break;
		}
	}

	/**
	 * Updates all the active gameobjects and adds them to the render queue.
	 * Removes gameobjects from the active pool if they are marked for removal.
	 * Adds new gameobjects.
	 * Adds dead objects to render queue.
	 * Respawns the player if necessary.
	 */
	private void updateLoop() {
		List<GameObject> toRemove = new ArrayList<GameObject>();
		for (GameObject gObject : gameObjects) {	//Go through every game object
			gObject.update();						//Update the game object
			if (gObject.isRemove()) {				//Check if game object is to be removed
				toRemove.add(gObject);					//Set it to be removed
			}else {
				objectsToRender.add(gObject);
			}
		}
		for (GameObject rObject : toRemove) {	//Remove game objects set for removal
			gameObjects.remove(rObject);
			if (rObject.isDisplayable()) {
				deadObjects.add(rObject);
			}
		}
		for (GameObject aObject : objectsToAdd) {		//Add game objects to be added
			gameObjects.add(aObject);
		}

		objectsToAdd.clear();	// Clears list as not to add new objects twice

		for (GameObject dObject : deadObjects) { // loops through the destroyed but displayed items (such as destroyed bases)
			objectsToRender.add(dObject);
		}
		if (players.get(activeTruck).isRemove()) {	//If the player is set for removal, respawn
			alivePlayers[activeTruck] = false;
			updateLives();
		}
	}

	/**
	 * Renders the objects in "objectsToRender" then clears the list
	 */
	private void renderObjects() {
		for (GameObject object : objectsToRender) {
			object.render(game.batch);
		}
		objectsToRender.clear();
	}

	/**
	 * Add a game object next frame
	 * @param gameObject gameObject to be added
	 */
	public void addGameObject(GameObject gameObject) {
		objectsToAdd.add(gameObject);
	}

	/**
	 * Allows external classes to access the player
	 * @return player
	 */
	public FireTruck getPlayer() {
		return players.get(activeTruck);
	}

	/**
	 * Draws all debug objects for one frame
	 */
	private void DrawDebug() {
		for (DebugDraw dObject : debugObjects) {
			dObject.Draw(gamecam.combined);
		}
		debugObjects.clear();
	}

	/**
	 * Draw a debug line
	 * @param start Start of the line
	 * @param end End of the line
	 * @param lineWidth Width of the line
	 * @param colour Colour of the line
	 */
	public void DrawLine(Vector2 start, Vector2 end, int lineWidth, Color colour) {
		// MEMORY LEAK FIX 2 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
		// Added an if statement to fully ensure debugging view is requested as we noticed the original teams debug
		// code causes a memory leak and possibly crashes the game overtime.
		if (showDebug) {
		debugObjects.add(new DebugLine(start, end, lineWidth, colour));
		}
		// END OF MODIFICATION - NP STUDIOS -----------------------------------------
	}

	/**
	 * Draw a debug circle (outline)
	 * @param position Centre of the circle
	 * @param radius Radius of the circle
	 * @param lineWidth Width of the outline
	 * @param colour Colour of the line
	 */
	public void DrawCircle(Vector2 position, float radius, int lineWidth, Color colour) {
		// MEMORY LEAK FIX 3 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
		// Added an if statement to fully ensure debugging view is requested as we noticed the original teams debug
		// code causes a memory leak and possibly crashes the game overtime.
		if (showDebug) {
			debugObjects.add(new DebugCircle(position, radius, lineWidth, colour));
		}
		// END OF MODIFICATION - NP STUDIOS -----------------------------------------
	}

	/**
	 * Draw a debug rectangle (outline)
	 * @param bottomLeft Bottom left point of the rectangle
	 * @param dimensions Dimensions of the rectangle (Width, Length)
	 * @param lineWidth Width of the outline
	 * @param colour Colour of the line
	 */
	public void DrawRect(Vector2 bottomLeft, Vector2 dimensions, int lineWidth, Color colour) {
		// MEMORY LEAK FIX 4 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
		// Added an if statement to fully ensure debugging view is requested as we noticed the original teams debug
		// code causes a memory leak and possibly crashes the game overtime.
		if (showDebug) {
			debugObjects.add(new DebugRect(bottomLeft, dimensions, lineWidth, colour));
		}
		// END OF MODIFICATION - NP STUDIOS -----------------------------------------
	}

	/**
	 * Updates the position of the camera to have the truck centre
	 */
	public void updateCamera() {
		gamecam.position.lerp(new Vector3(players.get(activeTruck).getX(),players.get(activeTruck).getY(),gamecam.position.z),0.1f);// sets the new camera position based on the current position of the FireTruck
		gamecam.update();
	}

	@Override
	public void resize(int width, int height) {
		gameport.update(width, height);
	}

	@Override
	public void pause() {
		setGameState(GameScreenState.PAUSE);
	}

	@Override
	public void resume() {
		setGameState(GameScreenState.RESUME);
	}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		Kroy.mainGameScreen = null;
	}

	/**
	 * @param s
	 */
	public void setGameState(GameScreenState s){
	    state = s;
	}

	/**
	 * @param index
	 * @return
	 */
	public GameObject getGameObject(int index) {
		if (index <= (gameObjects.size()-1)) {
			return gameObjects.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @return
	 */
	public List<GameObject> getGameObjects(){
		return gameObjects;
	}
	
	public int getLives() {
		return lives;
	}

	/**
	 * Checks the pause buttons
	 */
	private void clickCheck() {
		//resume button
		pauseWindow.resume.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		pauseWindow.visibility(false);
				resume();
	    	}
	    });

		//exit button
		pauseWindow.exit.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		Gdx.app.exit();
	    	}
	    });
		//menu button
		pauseWindow.menu.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		dispose();
	    		game.backToMenu();
	    		return;
	    	}
	    });
	}

	/**
	 * Add one fortress to the count
	 */
	public void addFortress() {
		fortressesCount++;
	}

	/**
	 * Remove one fortress to the count
	 */
	public void removeFortress() {
		fortressesCount--;
	}

	/**
	 * How many fortresses are left?
	 * @return Number of fortresses remaining
	 */
	public int fortressesLeft() {
		return fortressesCount;
	}

	/**
	 * Switch to the game over screen
	 * @param won Did the player reach the win state?
	 */
	public void gameOver(boolean won) {
		game.setScreen(new GameOverScreen(game, activeTruck, won));
	}

	/**
	 * 
	 */
	public void updateLives() {
		System.out.println(lives);
		if (lives>1) {
			lives -= 1;
			respawn();
		} else {
			gameOver(false);
		}
	}
	
	/**
	 * Respawns the player at the spawn position and updates the HUD
	 */
	public void respawn() {
		for (int i = 0; i < alivePlayers.length; i++) {
			if (alivePlayers[i] == true) {
				activeTruck = i;
				break;
			}
		}
	}
	public HUD getHud(){
		return hud;
	}

	public Vector2 getSpawnPosition() {
		return spawnPosition;
	}

	public void selectTruck () {
		for (FireTruck truck : players) {
			truck.setSelected(false);
		}
		players.get(activeTruck).setSelected(true);
	}
}
