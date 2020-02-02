package com.dicycat.kroy.scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;

public class Minigame {

    public Stage stage;
    public Table table = new Table();
    private SpriteBatch sb;
    private NinePatch patch = new NinePatch(new Texture("loool.jpg"), 3, 3, 3, 3);
    private NinePatchDrawable background = new NinePatchDrawable(patch);
    private ImageButton 


    public Minigame(Kroy game){
        sb = game.batch;
        Viewport viewport = new ScreenViewport(new OrthographicCamera());
        stage = new Stage(viewport, sb);
        table.reset();
        table.setBackground(background);
        updateDraw();
    }

    public void updateDraw(){
        table.setFillParent(true);
        stage.addActor(table);

    }

    public void visibility(boolean state){
        this.table.setVisible(state);
    }

}
