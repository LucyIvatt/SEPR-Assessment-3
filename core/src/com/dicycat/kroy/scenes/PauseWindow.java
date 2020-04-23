package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;

/**
 * Pause window
 * 
 * @author Michele Imbriani
 *
 */
public class PauseWindow {
	
	public Stage stage;
	public Table table = new Table();
	private SpriteBatch sb;
	private NinePatch patch = new NinePatch(new Texture("loool.jpg"), 3, 3, 3, 3);
	private NinePatchDrawable background = new NinePatchDrawable(patch);
	
    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    public TextButton resume = new TextButton("RESUME", skin);
    public TextButton exit = new TextButton("EXIT", skin);
    public TextButton menu = new TextButton("MENU", skin);
    public TextButton load = new TextButton("LOAD", skin);
	public TextButton save = new TextButton("SAVE", skin);
	public PauseWindow(Kroy game) {
		sb = game.batch;
		Viewport viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);

		table.setBackground(background);
		table.row();
	    table.add(resume).width(Kroy.CentreWidth());
		table.row();
		table.add(save).width(Kroy.CentreWidth());
		table.row();
		table.add(load).width(Kroy.CentreWidth());
		table.row();
	    table.add(menu).width(Kroy.CentreWidth());
	    table.row();
	    table.add(exit).width(Kroy.CentreWidth());
	    
		table.setFillParent(true);
	    stage.addActor(table);
	}

	/** Allows the window to be visible or hidden
	 * @param state	true means visible, false means hidden
	 */
	public void visibility(boolean state){
		this.table.setVisible(state);
	}
}
