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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.GameScreen;
import com.dicycat.kroy.screens.MenuScreen;

/**
 * Options window
 * 
 * @author Michele Imbriani
 *
 */
public class OptionsWindow {
	
	public Stage stage;
	public Table table = new Table();
	private SpriteBatch sb;
	private NinePatch patch = new NinePatch(new Texture("loool.jpg"), 3, 3, 3, 3);
	private NinePatchDrawable background = new NinePatchDrawable(patch);
	
    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    //options page 1
    private TextButton music = new TextButton("MUSIC", skin);
    private TextButton debug = new TextButton("DEBUG", skin);
    private TextButton back = new TextButton("BACK", skin);
    
    //music options page
    private TextButton stopMusic = new TextButton("STOP MUSIC", skin);
    private TextButton playMusic = new TextButton("PLAY MUSIC", skin);
    private TextButton volumeDown = new TextButton("MUTE VOLUME", skin);
    private TextButton volumeUp = new TextButton("UNMUTE VOLUME", skin);
    private TextButton backFromMusic = new TextButton("BACK", skin);
    
    //debug options page
    private TextButton showDebug = new TextButton("SHOW DEBUG", skin);
    private TextButton hideDebug = new TextButton("HIDE DEBUG", skin);
    private TextButton backFromDebug = new TextButton("BACK", skin);
    
    public static State state = State.PAGE1;
    
    /**
     *	Allows to have multiple 'pages' of the Option window without
     *	having to create several stages.
     */
    public enum State{
		PAGE1,
		MUSIC,
		DEBUG,
	}

	/**
	 * 	The important feature is the updateDraw() method
	 * 	which can be found at the bottom.
	 * 
	 * @param game
	 */
	public OptionsWindow(Kroy game) {
		sb = game.batch;
		Viewport viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);

		table.reset();
		table.setBackground(background);
		updateDraw();
	}

	/** Allows the window to be visible or hidden
	 * @param state	true means visible, false means hidden
	 */
	public void visibility(boolean state){
		this.table.setVisible(state);
	}
	
	/**
	 * Takes screen as attribute because the 'back' button behaves differently
	 *  based on whether the optionwindow was called from menu or gamescreen
	 *  
	 * @param fromMenu
	 */
	public void clickCheck(final boolean fromMenu) {
	//page 1
		//resume button
		this.music.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		table.reset();
	    		state = State.MUSIC;
	    		updateDraw();
	    	}
	    });

		//debug button
		this.debug.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		table.reset();
	    		state = State.DEBUG;
	    		updateDraw();
	    	}
	    });
		//back button
			this.back.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    		visibility(false);
		    		if (fromMenu == false) {
		    			Kroy.mainGameScreen.setGameState(GameScreen.GameScreenState.PAUSE);
		    			return;
		    		} else if (fromMenu) {
		    			Kroy.mainMenuScreen.state = MenuScreen.MenuScreenState.MAINMENU;
		    			return;
		    		}
		    	}
		    });
			
	//music page
			//playStopMusic button
			this.playMusic.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    		if (MenuScreen.music.isPlaying() == false) {
		    			MenuScreen.music.play();  
		    		}
		    		}
		    });
			//playStopMusic button
			this.stopMusic.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    		if (MenuScreen.music.isPlaying()){
		    			MenuScreen.music.stop();
		    		} 
		    		}
		    });
			//volumeDown button
			this.volumeDown.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    			MenuScreen.musicVolume = 0;
		    			MenuScreen.music.setVolume((float)MenuScreen.musicVolume);

		    	}
		    });
			//volumeUp button
			this.volumeUp.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    			MenuScreen.musicVolume = 1;
		    			MenuScreen.music.setVolume((float)MenuScreen.musicVolume);
		    	}
		    });
			//backFromMusic button
			this.backFromMusic.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    		table.reset();
		    		state = State.PAGE1;
		    		updateDraw();
		    	}
		    });
			
	//debug page
			//showDebug button
			this.showDebug.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    		GameScreen.showDebug = true;
		    		}
		    });
			//hideDebug button
			this.hideDebug.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    		GameScreen.showDebug = false;
		    		}
		    });
			//backFromDebug button
			this.backFromDebug.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    		table.reset();
		    		state = State.PAGE1;
		    		updateDraw();
		    	}
		    });
	}

	/**
	 *	This function allows the table to change its content based 
	 *	on which page of the OptionsWindow we are on.
	 *	
	 *	By being called in the Screen's render method, the Options window
	 *	gets reset and refreshed every rendering loop: this way we are able to 
	 *	have several 'pages' within the same table.
	 * 
	 */
	public void updateDraw() {
		switch(state) {
			case PAGE1:
				table.row();
			    table.add(music).width(Kroy.CentreWidth());
				table.row();
			    table.add(debug).width(Kroy.CentreWidth());
			    table.row();
			    table.add(back).width(Kroy.CentreWidth());
			    break;
			case MUSIC:
				table.row();
			    table.add(playMusic).width(Kroy.CentreWidth());
			    table.row();
			    table.add(stopMusic).width(Kroy.CentreWidth());
			    table.row();
			    table.add(volumeDown).width(Kroy.CentreWidth());
			    table.row();
			    table.add(volumeUp).width(Kroy.CentreWidth());
			    table.row();
			    table.add(backFromMusic).width(Kroy.CentreWidth());
			    break;
			case DEBUG:
				table.row();
			    table.add(showDebug).width(Kroy.CentreWidth());
			    table.row();
			    table.add(hideDebug).width(Kroy.CentreWidth());
			    table.row();
			    table.add(backFromDebug).width(Kroy.CentreWidth());
			    break;
		}
		
		table.setFillParent(true);
	    stage.addActor(table);
	}
}
