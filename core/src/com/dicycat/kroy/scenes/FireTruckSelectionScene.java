package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;

import java.util.ArrayList;

/**
 * Window for selecting FireTruck type
 * 
 * @author Luke Taylor
 *
 */
public class FireTruckSelectionScene {

	public Stage stage;
	public Table table = new Table();
	private SpriteBatch sb;
	private NinePatchDrawable background = new NinePatchDrawable(new NinePatch(new Texture("Grey.png"), 3, 3, 3, 3));
	
    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    
    //Buttons initialised, labelled and stylised
    public Label truck1 = new Label("Speed", skin);
    public Label truck2 = new Label("Damage", skin);
    public Label truck3 = new Label("Capacity", skin);
    public Label truck4 = new Label("Range", skin);
	public TextButton startGameButton = new TextButton("Start Game", skin);
    private float width = Gdx.graphics.getWidth();
    private float centre = width* 0.7f;
    
    
	/**
	 * @param game		Kroy instance
	 */
	public FireTruckSelectionScene(Kroy game) {
		sb = game.batch;
		Viewport viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);

		table.setBackground(background);
		
		// Images added to the screen
		table.add(new Image(new Texture("fireTruck1.png")));
		table.add(new Image(new Texture("fireTruck2.png")));
		table.add(new Image(new Texture("fireTruck3.png")));
		table.add(new Image(new Texture("fireTruck4.png")));
		
		table.row();

	    truck1.setAlignment(Align.center);
		truck2.setAlignment(Align.center);
		truck3.setAlignment(Align.center);
		truck4.setAlignment(Align.center);
		
		// Buttons added to the screen
		table.add(truck1).width(centre/3.0f).pad(0,0,50,0);
	    table.add(truck2).width(centre/3.0f).pad(0,0,50,0);
	    table.add(truck3).width(centre/3.0f).pad(0,0,50,0);
	    table.add(truck4).width(centre/3.0f).pad(0,0,50,0);

	    table.row();

	    table.add(startGameButton).width(centre/2.0f).colspan(4);
	    
		table.setFillParent(true);
	    stage.addActor(table);
	    
	    
	}
	
	/**
	 * @param state		Makes the window visible or hidden
	 */
	public void visibility(boolean state){
		this.table.setVisible(state);
	}
}
