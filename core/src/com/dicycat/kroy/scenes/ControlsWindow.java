package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.MenuScreen;

/**
 * Scene which shows the controls to the user to allow them to play the game easily.
 * 
 * @author Jordan Spooner (NP Studios)
 *
 */
public class ControlsWindow {
	
	public Stage stage;
	public Table table = new Table();
	private SpriteBatch sb;
	private NinePatch patch = new NinePatch(new Texture("loool.jpg"), 3, 3, 3, 3);   // splits texture into nine 'patches' and gives a border of 3 pixels wide/tall on (texture,left,right,top,bottom)
	private NinePatchDrawable background = new NinePatchDrawable(patch);
	
	private Skin skin = new Skin(Gdx.files.internal("uiskin.json")); 
	private TextButton back = new TextButton("BACK", skin); // back button texture
	

	// Control Labels
	private Label pause = new Label("Pauses game", skin, "default-font", "white");
	private Label up = new Label("Drive up", skin, "default-font", "white");
	private Label left = new Label("Drive left", skin, "default-font", "white");
	private Label down = new Label("Drive down", skin, "default-font", "white");
	private Label right = new Label("Drive right", skin, "default-font", "white");
	private Label blank = new Label(" ", skin);
	private Label select = new Label("Select fire truck", skin, "default-font", "white");
	
	private float width = Gdx.graphics.getWidth();
	private float centre = width* 0.7f;
	
	
	public ControlsWindow(Kroy game) {
		sb = game.batch;
		Viewport viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		formatControlsScreen();
		
		table.setFillParent(true);
	    stage.addActor(table);
	}
	
	/** Allows the window to be visible or hidden
	 * @param state	true means visible, false means hidden
	 */
	public void visibility(boolean state){
		this.table.setVisible(state);
	}
	
	/**
	 *  Check if the back button is pressed and goes back to the menu if so
	 */
	public void clickCheck() {
		//back button
		this.back.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		visibility(false);
				Kroy.mainMenuScreen.state = MenuScreen.MenuScreenState.MAINMENU;
				return;
	    		}
	    });
		
	}

	/**
	 *  Sets the layout for the controls screen
	 */
	private void formatControlsScreen() {
		table.setBackground(background);
		table.add(new Image(new Texture("pkey.png")));
		table.add(new Image(new Texture("esckey.png")));
		table.add(new Image(new Texture("mkey.png")));
		table.add(blank);
		table.add(pause);
		table.row();
		table.add(new Image(new Texture("up.png")));
		table.add(blank);
		table.add(blank);
		table.add(blank);
		table.add(up);
		table.row();
		table.add(new Image(new Texture("left.png")));
		table.add(blank);
		table.add(blank);
		table.add(blank);
		table.add(left);
		table.row();
		table.add(new Image(new Texture("down.png")));
		table.add(blank);
		table.add(blank);
		table.add(blank);
		table.add(down);
		table.row();
		table.add(new Image(new Texture("right.png")));
		table.add(blank);
		table.add(blank);
		table.add(blank);
		table.add(right);
		table.row();
		table.add(new Image(new Texture("1key.png")));
		table.add(new Image(new Texture("2key.png")));
		table.add(new Image(new Texture("3key.png")));
		table.add(new Image(new Texture("4key.png")));
		table.add(select);
		table.row();
		table.add(blank);
		table.row();
		table.add(blank);
		table.add(blank);
		table.add(blank);
		table.add(blank);
		table.add(back).width(centre/3.0f);
	}
}
