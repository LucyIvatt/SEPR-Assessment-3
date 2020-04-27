package com.dicycat.kroy.screens;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.dicycat.kroy.entities.*;
import com.dicycat.kroy.gamemap.TiledGameMap;
import com.dicycat.kroy.minigame.Minigame;
import com.dicycat.kroy.misc.StatBar;
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
public class GameScreen implements Screen {

	public static enum GameScreenState {
		PAUSE, RUN, RESUME, MINIGAME
	}

	public Kroy game;
	public GameTextures textures;
	public static Boolean showDebug = false;
	public float gameTimer; // Timer to destroy station.
	// MINIMAP_1 - START OF MODIFICATION - NP STUDIOS - BETHANY GILMORE
	private Texture minimap = new Texture("YorkMap.png"); // A .png version of the tilemap background to use as the
															// background texture for the minimap.
	// MINIMAP_1 - END OF MODIFICATION - NP STUDIOS - BETHANY GILMORE

	public GameScreenState state = GameScreenState.RUN;

	public static TiledGameMap gameMap;

	private OrthographicCamera gamecam; // follows along what the port displays
	private Viewport gameport;

	private HUD hud;
	private PauseWindow pauseWindow;
	private OptionsWindow optionsWindow;
	private Minigame minigame;

	// TRUCK_SELECT_CHANGE_11 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
	// Slightly edited trucks statistics to make the game more balanced.
	private Float[][] truckStats = { // Each list is a configuration of a specific truck. {speed, flowRate, capacity,
										// range}
			{ 450f, 1f, 400f, 300f }, // Speed
			{ 300f, 2f, 400f, 300f }, // Flow rate
			{ 300f, 1f, 500f, 300f }, // Capacity
			{ 300f, 1f, 400f, 450f } // Range
	};

	// Changes variable of truckNum to activeTruck
	public int activeTruck; // Identifies the truck that is currently selected
	// Deleted the variable player and replaced it with an ArrayList containing the
	// 4 trucks and named it players
	private ArrayList<FireTruck> players;
	// TRUCK_SELECT_CHANGE_11 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----
	private int lives = 4;

	private int fortressesCount;
	private Vector2 spawnPosition; // Coordinates the player spawns at

	private List<GameObject> gameObjects, deadObjects; // List of active game objects
	private List<GameObject> objectsToRender = new ArrayList<GameObject>(); // List of game objects that have been updated but need rendering
	private List<GameObject> objectsToAdd;
	private List<DebugDraw> debugObjects; // List of debug items


	// TRUCK_SELECT_CHANGE_12 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
	// Removed truckNum from constructor parameters
	public GameScreen(Kroy _game, Preferences save) {
		// END_GAME_FIX_1 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
		fortressesCount = 6; // Initialize fortress count to 6
		// END_GAME_FIX_1 - END OF MODIFICATION - NP STUDIOS
		game = _game;
		gamecam = new OrthographicCamera();
		gameport = new FitViewport(Kroy.width, Kroy.height, gamecam); // Mic:could also use StretchViewPort to make the
																		// screen stretch instead of adapt
		gameMap = new TiledGameMap(); // or FitPort to make it fit into a specific width/height ratio
		pauseWindow = new PauseWindow(game);
		pauseWindow.visibility(false);
		optionsWindow = new OptionsWindow(game);
		optionsWindow.visibility(false);
//		minigame = new Minigame(game);
//		minigame.visibility(false);
		textures = new GameTextures(); // removed truckNum from GameTextures constructor call
		// FIRESTATION_RANGE_FIX_1 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
		// Edited coordinate so firestation is in the middle of the square
		spawnPosition = new Vector2(234 * 16, 3900);
		// FIRESTATION_RANGE_FIX_1 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT
		gameTimer = 60 * 15; // Set timer to 15 minutes
		hud = new HUD(game.batch, gameTimer);
		players = new ArrayList<>(); // Initialise the array which will contain the 4 fire trucks

	}

	// TRUCK_SELECT_CHANGE_12 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----

	// The constructor we use if we should load a game instead

	/**
	 * Initializes the screen which is first shown
	 */
	@Override
	public void show() {
		objectsToAdd = new ArrayList<GameObject>();
		gameObjects = new ArrayList<GameObject>();
		deadObjects = new ArrayList<GameObject>();
		debugObjects = new ArrayList<DebugDraw>();

		// TRUCK_SELECT_CHANGE_13 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
		// Adds all the different firetruck types to the players ArrayList
		players.add(new FireTruck(new Vector2(spawnPosition.x + 50, spawnPosition.y), truckStats[0], 0));
		players.add(new FireTruck(new Vector2(spawnPosition.x - 50, spawnPosition.y), truckStats[1], 1));
		players.add(new FireTruck(new Vector2(spawnPosition.x, spawnPosition.y), truckStats[2], 2));
		players.add(new FireTruck(new Vector2(spawnPosition.x, spawnPosition.y - 50), truckStats[3], 3));

		// Iterates through the players array lists and adds them to gameObjects.
		for (int i = 0; i < 4; i++) {
			gameObjects.add(players.get(i)); // Player
		}

		// Sets initial camera position to the active truck's position (set to arbitrary
		// truck at the beginning of the game)
		gamecam.translate(new Vector2(players.get(activeTruck).getX(), players.get(activeTruck).getY())); // sets
																											// initial
																											// Camera
																											// position
		// TRUCK_SELECT_CHANGE_13 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----
		gameObjects.add(new FireStation());

		/*gameObjects.add(new PowerUp(new Vector2(1772,4633)));
		gameObjects.add(new PowerUp(new Vector2(4344,3729)));
		gameObjects.add(new PowerUp(new Vector2(5512,2696)));
		gameObjects.add(new PowerUp(new Vector2(5055,1415)));
		gameObjects.add(new PowerUp(new Vector2(1608, 585)));
		gameObjects.add(new PowerUp(new Vector2(1919,3871)));*/

		// PATROLS_3 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT ------------
		// Creates the aliens for the patrols and adds them to gameObjects so they can
		// be updated each tick
		int timeBetween = 50;
		for (int patrolNum = 1; patrolNum <= 4; patrolNum++)
			for (int i = 0; i < 5; i++) {
				gameObjects.add(new Alien(patrolNum, i * timeBetween, 300));
			}
		// PATROLS_4 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT ------------

		// FORTRESS_HEALTH_1 - START OF MODIFICATION - NP STUDIOS - CASSANDRA LILLYSTONE
		// ----
		// Added health and damage values for each fortress instantiation

		// Added new fortresses and set position in accordance with collisions on tiled map
		switch(MenuScreen.gameModeSelect) {
		case 1:
			gameObjects.add(new Fortress(new Vector2(2903,3211),textures.getFortress(0), textures.getDeadFortress(0),
					new Vector2(256, 218), 200, 3));
			gameObjects.add(new Fortress(new Vector2(3200,5681), textures.getFortress(1), textures.getDeadFortress(1),
					new Vector2(256, 320), 250, 5));
			gameObjects.add(new Fortress(new Vector2(2050,1937), textures.getFortress(2), textures.getDeadFortress(2),
					new Vector2(400, 240), 300, 8));
			gameObjects.add(new Fortress(new Vector2(4496,960), textures.getFortress(3), textures.getDeadFortress(3),
					new Vector2(345, 213), 350, 10));
			gameObjects.add(new Fortress(new Vector2(6112,1100), textures.getFortress(4), textures.getDeadFortress(4),
					new Vector2(300, 240), 400, 12)); //382, 319
			gameObjects.add(new Fortress(new Vector2(600,4000), textures.getFortress(5), textures.getDeadFortress(5),
					new Vector2(300, 270), 450, 15)); //45, 166
			break;
		case 2:
			gameObjects.add(new Fortress(new Vector2(2903,3211),textures.getFortress(0), textures.getDeadFortress(0),
					new Vector2(256, 218), 400, 5));
			gameObjects.add(new Fortress(new Vector2(3200,5681), textures.getFortress(1), textures.getDeadFortress(1),
					new Vector2(256, 320), 500, 10));
			gameObjects.add(new Fortress(new Vector2(2050,1937), textures.getFortress(2), textures.getDeadFortress(2),
					new Vector2(400, 240), 600, 15));
			gameObjects.add(new Fortress(new Vector2(4496,960), textures.getFortress(3), textures.getDeadFortress(3),
					new Vector2(345, 213), 700, 20));
			gameObjects.add(new Fortress(new Vector2(6112,1100), textures.getFortress(4), textures.getDeadFortress(4),
					new Vector2(300, 240), 800, 25)); //382, 319
			gameObjects.add(new Fortress(new Vector2(600,4000), textures.getFortress(5), textures.getDeadFortress(5),
					new Vector2(300, 270), 900, 30)); //45, 166
			break;
		case 3:
			gameObjects.add(new Fortress(new Vector2(2903,3211),textures.getFortress(0), textures.getDeadFortress(0),
					new Vector2(256, 218), 500, 7));
			gameObjects.add(new Fortress(new Vector2(3200,5681), textures.getFortress(1), textures.getDeadFortress(1),
					new Vector2(256, 320), 700, 12));
			gameObjects.add(new Fortress(new Vector2(2050,1937), textures.getFortress(2), textures.getDeadFortress(2),
					new Vector2(400, 240), 700, 18));
			gameObjects.add(new Fortress(new Vector2(4496,960), textures.getFortress(3), textures.getDeadFortress(3),
					new Vector2(345, 213), 800, 22));
			gameObjects.add(new Fortress(new Vector2(6112,1100), textures.getFortress(4), textures.getDeadFortress(4),
					new Vector2(300, 240), 900, 27)); //382, 319
			gameObjects.add(new Fortress(new Vector2(600,4000), textures.getFortress(5), textures.getDeadFortress(5),
					new Vector2(300, 270), 1000, 33)); //45, 166
			break;
		
		}
		// FORTRESS_HEALTH_1 & NEW_FORTRESSES_2 - END OF MODIFICATION - NP STUDIOS - CASSANDRA LILLYSTONE  & ALASDAIR PILMORE-BEDFORD
	}

	/**
	 * Called every frame and calls the methods to update and render the game
	 * objects, as well as handling input.
	 */
	public void render(float delta) {
		Gdx.input.setInputProcessor(pauseWindow.stage); // Set input processor
		pauseWindow.stage.act();

		switch (state) {
		case RUN:
			if (Gdx.input.isKeyPressed(Keys.P) || Gdx.input.isKeyPressed(Keys.O) || Gdx.input.isKeyPressed(Keys.M)
					|| Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				pauseWindow.visibility(true);
				pause();
			}
			// TRUCK_SELECT_CHANGE_14 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
			// Sets active truck depending on which number key is pressed
			if (Gdx.input.isKeyPressed(Keys.NUM_1) && !players.get(0).isRemove()) {
				activeTruck = 0;
			} else if (Gdx.input.isKeyPressed(Keys.NUM_2) && !players.get(1).isRemove()) {
				activeTruck = 1;
			} else if (Gdx.input.isKeyPressed(Keys.NUM_3) && !players.get(2).isRemove()) {
				activeTruck = 2;
			} else if (Gdx.input.isKeyPressed(Keys.NUM_4) && !players.get(3).isRemove()) {
				activeTruck = 3;
			}

			selectTruck();

			// This code was added by Sam Hutchings to implement power ups.
			Random random = new Random();
			if (random.nextFloat() < 0.01f) { // Every frame, there is a 1% chance of a power up spawning.
				PowerUp powerUpToAdd = new PowerUp();
				// This lambda function filters out any old power ups at the location of the new
				// power up to avoid stacking effects.
				gameObjects = gameObjects.stream()
						.filter(object -> (!((object instanceof PowerUp)
						&& (object.getPosition().equals(powerUpToAdd.getPosition())))))
						// This filter removes any PowerUps at the location of powerUpToAdd
						.collect(Collectors.toList());
				// The new power up is then added.
				gameObjects.add(powerUpToAdd);
			}

			// TRUCK_SELECT_CHANGE_14 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----

			gameTimer -= delta; // Decrement timer

			updateLoop(); // Update all game objects positions but does not render them as to be able to
							// render everything as quickly as possible

			gameMap.renderRoads(gamecam); // Render the background roads, fields and rivers

			game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
			game.batch.setProjectionMatrix(gamecam.combined); // Mic:only renders the part of the map where the camera is
			game.batch.begin(); // Game loop Start

			hud.update(delta);

			renderObjects(); // Renders objects specified in the UpdateLoop() called previously

			game.batch.end();

			gameMap.renderBuildings(gamecam); // Renders the buildings and the foreground items which are not entities

			hud.stage.draw();
			// MINIMAP_2 - START OF MODIFICATION - NP STUDIOS - BETHANY GILMORE
			// -----------------
			drawMinimap();
			// MINIMAP_2 - END OF MODIFICATION - NP STUDIOS - BETHANY GILMORE
			// ---------------------
			pauseWindow.stage.draw();

			if (showDebug) {
				DrawDebug(); // Draw all debug items as they have to be drawn outside the batch
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
		case MINIGAME:
			Gdx.input.setInputProcessor(minigame.stage);
			minigame.visibility(true);
			minigame.stage.draw();
			minigame.stage.act();
			minigame.clickCheck();
			break;
		default:
			break;
		}
	}

	public void newMinigame() {
		minigame = new Minigame(game, true);
	}

	/**
	 * Updates all the active gameobjects and adds them to the render queue. Removes
	 * gameobjects from the active pool if they are marked for removal. Adds new
	 * gameobjects. Adds dead objects to render queue. Respawns the player if
	 * necessary.
	 */
	private void updateLoop() {
		List<GameObject> toRemove = new ArrayList<GameObject>();
		List<GameObject> garbageCollection = new ArrayList<GameObject>();
		for (GameObject gObject : gameObjects) { // Go through every game object
			gObject.update(); // Update the game object
			if (gObject.isRemove()) { // Check if game object is to be removed
				toRemove.add(gObject); // Set it to be removed
			} else {
				objectsToRender.add(gObject);
			}
		}
		for (GameObject rObject : toRemove) { // Remove game objects set for removal
			gameObjects.remove(rObject);
			deadObjects.add(rObject);
		}
		for (GameObject aObject : objectsToAdd) { // Add game objects to be added
			gameObjects.add(aObject);
		}

		objectsToAdd.clear(); // Clears list as not to add new objects twice

		for (GameObject dObject : deadObjects) { // loops through the destroyed but displayed items (such as destroyed
													// bases)
			if (dObject.isDisplayable()) {
				objectsToRender.add(dObject);
			} else if (!dObject.shouldSave()){
				garbageCollection.add(dObject);
			}
		}
		// Remove any dead game objects that aren't displayable
		garbageCollection.forEach(deadObjects::remove);
		garbageCollection.clear();
		// TRUCK_SELECT_CHANGE_15 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
		// Changed to check if the active truck is destroyed and then updates lives if
		// so
		if (players.get(activeTruck).isRemove()) { // If the player is set for removal, respawn
			updateLives();
		}
		// TRUCK_SELECT_CHANGE_15 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----

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
	 * 
	 * @param gameObject gameObject to be added
	 */
	public void addGameObject(GameObject gameObject) {
		objectsToAdd.add(gameObject);
	}

	/**
	 * Allows external classes to access the player
	 * 
	 * @return player
	 */
	// TRUCK_SELECT_CHANGE_16 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
	// Updated to return the active truck
	public FireTruck getPlayer() {
		return players.get(activeTruck);
	}
	// TRUCK_SELECT_CHANGE_16 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----

	/**
	 * Draws the map's background as a texture in the bottom left corner. And also
	 * redraws all the objects in gameObjects scaled down to fit on the minimap. The
	 * firetrucks in their relative postions are also drawn on the minimap texture.
	 *
	 * @author Bethany Gilmore - NP STUDIOS
	 */
	public void drawMinimap() {
		game.batch.begin();
		game.batch.draw(minimap, 0, 0, 394, 350);

		for (GameObject object : gameObjects) {
			game.batch.draw(object.getTexture(), object.getX() / 19, object.getY() / 19, object.getWidth() / 10,
					object.getHeight() / 10);
		} // Draws the fortresses and patrols to a minimap scaled down to the in the
			// bottom left corner.
		for (FireTruck truck : players) {
			if (truck.getHealthPoints() > 0) {
				game.batch.draw(truck.getTexture(), truck.getX() / 19, truck.getY() / 19, 20, 25);
			}
			// Draws the firetrucks on their relative position on the minimap. size is not
			// to scale to make their position obvious and clear.
		}
		game.batch.end();
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
	 * 
	 * @param start     Start of the line
	 * @param end       End of the line
	 * @param lineWidth Width of the line
	 * @param colour    Colour of the line
	 */
	public void DrawLine(Vector2 start, Vector2 end, int lineWidth, Color colour) {
		// MEMORY LEAK FIX 2 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
		// Added an if statement to fully ensure debugging view is requested as we
		// noticed the original teams debug
		// code causes a memory leak and possibly crashes the game overtime.
		if (showDebug) {
			debugObjects.add(new DebugLine(start, end, lineWidth, colour));
		}
		// END OF MODIFICATION - NP STUDIOS -----------------------------------------
	}

	/**
	 * Draw a debug circle (outline)
	 * 
	 * @param position  Centre of the circle
	 * @param radius    Radius of the circle
	 * @param lineWidth Width of the outline
	 * @param colour    Colour of the line
	 */
	public void DrawCircle(Vector2 position, float radius, int lineWidth, Color colour) {
		// MEMORY LEAK FIX 3 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
		// Added an if statement to fully ensure debugging view is requested as we
		// noticed the original teams debug
		// code causes a memory leak and possibly crashes the game overtime.
		if (showDebug) {
			debugObjects.add(new DebugCircle(position, radius, lineWidth, colour));
		}
		// END OF MODIFICATION - NP STUDIOS -----------------------------------------
	}

	/**
	 * Draw a debug rectangle (outline)
	 * 
	 * @param bottomLeft Bottom left point of the rectangle
	 * @param dimensions Dimensions of the rectangle (Width, Length)
	 * @param lineWidth  Width of the outline
	 * @param colour     Colour of the line
	 */
	public void DrawRect(Vector2 bottomLeft, Vector2 dimensions, int lineWidth, Color colour) {
		// MEMORY LEAK FIX 4 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
		// Added an if statement to fully ensure debugging view is requested as we
		// noticed the original teams debug
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
		// TRUCK_SELECT_CHANGE_16 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
		// sets the new camera position based on the current position of the active
		// truck
		gamecam.position.lerp(
				new Vector3(players.get(activeTruck).getX(), players.get(activeTruck).getY(), gamecam.position.z),
				0.1f);// sets the new camera position based on the current position of the FireTruck
		// TRUCK_SELECT_CHANGE_16 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----
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
	public void hide() {
	}

	@Override
	public void dispose() {
		Kroy.mainGameScreen = null;
	}

	public void setGameState(GameScreenState s) {
		state = s;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public int getLives() {
		return lives;
	}

	/**
	 * Checks the pause buttons for input
	 */
	private void clickCheck() {
		// resume button
		pauseWindow.resume.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pauseWindow.visibility(false);
				resume();
			}
		});

		// exit button
		pauseWindow.exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		// menu button
		pauseWindow.menu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				game.backToMenu();
				return;
			}
		});

		// save
		pauseWindow.save.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				/**
				 * We save the following data: The health of each fortress, and how many are
				 * left The position, health and water of each living firetruck The current
				 * firetruck selected The position, and type of every powerup. The gametime
				 * (mostly for the station destruction) The score
				 */

				saveObjects(gameObjects, "gameObjects");
				saveObjects(deadObjects, "deadObjects");

				// gameData is the Preference name given to non-object data
				Preferences pref = Gdx.app.getPreferences("gameData");
				pref.putFloat("gametime", gameTimer);
				pref.putInteger("score", hud.getScore());
				pref.putInteger("fortressesCount", fortressesCount);
				pref.flush(); // Flush = save to file

			}
		});

		// load
		pauseWindow.load.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				// The swapObjects method exists to switch objects originally in the deadObjects
				// list to the gameObjects list and vice versa, for example if a fortress is
				// dead in the game, but alive in the save data, it is "revived"
				swapObjects(gameObjects, deadObjects, "deadObjects");
				swapObjects(deadObjects, gameObjects, "gameObjects");

				loadObjects(gameObjects, "gameObjects");
				loadObjects(deadObjects, "deadObjects");

				// Load non-object game data
				Preferences pref = Gdx.app.getPreferences("gameData");
				gameTimer = pref.getFloat("gametime");
				hud.setScore(pref.getInteger("score"));
				fortressesCount = pref.getInteger("fortressesCount");

				// TODO Figure out why the game gets loaded a bunch of times every click
				// -Could be because the click is held down and it just repeatedly loads;
				// -Requires more investigation

			}

		});

	}

	/**
	 * Use this method to swap between two GameObject lists, based on a Preference
	 *
	 * @param originalObjects The list to swap from
	 * @param newObjects      The list to swap to
	 * @param prefName        The Preference name (ideally similar to the newObjects
	 *                        parameter)
	 */
	public void swapObjects(List<GameObject> originalObjects, List<GameObject> newObjects, String prefName) {

		// objectsToRemove is a temporary list to hold all the objects that will be
		// removed, as originalObjects is immutable whilst iterating through it
		List<GameObject> objectsToRemove = new ArrayList<GameObject>();

		// Retrieved once to avoid repeated getPreferences method calls
		Preferences pref = Gdx.app.getPreferences(prefName);

		// trueIfDead sets whether the GameObject is dead or not
		Boolean trueIfDead = prefName.equals("deadObjects");

		// Each object in originalObjects is first checked if it should be saved, and
		// then if it's UUID is in the Preference name passed. If it is, then it's death
		// state is switched, and it is added to both the ObjectsToRemove and newObjects
		// lists. After all originalObjects have been looped through, the objects
		// swapped are removed from the originalObjects list
		originalObjects.forEach(o -> {
			if (o.shouldSave() && pref.contains(o.getUUID())) {
				o.setRemove(trueIfDead);
				objectsToRemove.add(o);
				newObjects.add(o);
			}
		});
		objectsToRemove.forEach(originalObjects::remove);
	}

	/**
	 * Load objects to a GameObject list from a Preference
	 * 
	 * @param data     The list to load to
	 * @param prefName The Preference to load from
	 */
	public void loadObjects(List<GameObject> data, String prefName) {
		// Retrieved once to avoid repeated getPreferences method calls
		Preferences pref = Gdx.app.getPreferences(prefName);
		

		// The data list is first turned into a stream and filtered to remove
		// GameObjects that shouldn't be saved, such as patrols. Then for each object,
		// it is loaded based on the data passed from the Preference
		data.stream().filter(GameObject::shouldSave)
		.forEach(gObject -> {
			try {
				// System.out.format("ID: %s, Data: %s\n",gObject.getUUID(),pref.getString(gObject.getUUID()));
				gObject.load(pref.getString(gObject.getUUID()));
			} catch (Exception e) {
				System.err.println(e);
			}
		});
	}

	/**
	 * Save objects from a GameObject list to a Preference
	 * 
	 * @param data The list to save from
	 * @param prefName The Preference to save to
	 */
	public void saveObjects(List<GameObject> data, String prefName) {
		// Retrieved once to avoid repeated getPreferences method calls
		Preferences pref = Gdx.app.getPreferences(prefName);
		
		// Empty the Preference to remove old save data
		pref.clear();
		
		// Filter to include only saveable GameObjects, Then each object is stored in a
		// map, with the key being it's UUID and the value being the data from it's
		// .save() method. The map is then put into the Preference, and saved using .flush()
		pref.put(data.stream().filter(GameObject::shouldSave)
				.collect(Collectors.toMap(GameObject::getUUID, GameObject::save)));
		pref.flush();
	}

	/**
	 * Remove one fortress to the fortressCount
	 */
	public void removeFortress() {
		fortressesCount--;
	}

	/**
	 * How many fortresses are left?
	 * 
	 * @return Number of fortresses remaining
	 */
	// FORTRESS_COUNT_3 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
	// Edited the name of the getter to improve consistency
	public int getFortressesCount() {
		return fortressesCount;
	}
	// FORTRESS_COUNT_3 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT

	/**
	 * Switch to the game over screen
	 * 
	 * @param won Did the player reach the win state?
	 */
	public void gameOver(boolean won) {
		game.setScreen(new GameOverScreen(game, activeTruck, won));
	}

	/**
	 * Calls game over if lives == 0, otherwise removes 1 from life counter
	 */
	public void updateLives() {
		if (lives > 1) {
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
		// TRUCK_SELECT_CHANGE_17 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
		// Picks first alive truck and sets this to the new active one when the current
		// active one is killed
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemove() == false) {
				activeTruck = i;
				break;
			}
		}
		// TRUCK_SELECT_CHANGE_17 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----
	}

	public HUD getHud() {
		return hud;
	}

	public Vector2 getSpawnPosition() {
		return spawnPosition;
	}

	// TRUCK_SELECT_CHANGE_18 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
	// Sets the selected variable on each of the trucks to false and then sets the
	// active trucks selected variable to true
	public void selectTruck() {
		for (FireTruck truck : players) {
			truck.setSelected(false);
		}
		players.get(activeTruck).setSelected(true);
	}
	// TRUCK_SELECT_CHANGE_18 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----

}
