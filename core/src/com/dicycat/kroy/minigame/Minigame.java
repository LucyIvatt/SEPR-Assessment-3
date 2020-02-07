package com.dicycat.kroy.minigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.scenes.OptionsWindow;
import com.dicycat.kroy.screens.GameScreen;
import com.dicycat.kroy.screens.MenuScreen;

import java.util.ArrayList;

public class Minigame {

    public Stage stage;
    public Table table = new Table();
    private SpriteBatch sb;
    private NinePatch patch = new NinePatch(new Texture("loool.jpg"), 3, 3, 3, 3);
    private NinePatchDrawable background = new NinePatchDrawable(patch);
    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    private ArrayList<Pipe> pipes = new ArrayList<>();
    private ImageButton check = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("check.png"))));

    private TextButton back = new TextButton("RETURN", skin);

    public static Minigame.State state = State.GAME1;

    /**
     *	Allows to have multiple 'pages' of the Option window without
     *	having to create several stages.
     */
    public static enum State{
        GAME1,
        WON,
        TEST
    }

    public Minigame(Kroy game){
        sb = game.batch;
        Viewport viewport = new ScreenViewport(new OrthographicCamera());
        stage = new Stage(viewport, sb);
        table.reset();
        table.setBackground(background);
        pipes.add(new Pipe(1, 1));
        pipes.add(new Pipe(1, 0));
        pipes.add(new Pipe(1, 1));
        pipes.add(new Pipe(2, 0));
        pipes.add(new Pipe(3, 0));
        pipes.add(new Pipe(2, 2));
        pipes.add(new Pipe(1, 3));
        pipes.add(new Pipe(1, 2));
        pipes.add(new Pipe(1, 3));

        updateDraw();
    }

    public void clickCheck() {
        for (int i = 0; i < 9; i += 1){
            final int finalI = i;
            pipes.get(i).getButton().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent input, float x, float y) {
                    table.reset();
                    pipes.get(finalI).rotate();
                    updateDraw();
                }
            });
        }
        this.check.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent input, float x, float y) {
                boolean flag = true;
                for (Pipe pipe : pipes){
                   flag = flag & pipe.isCorrect();
                }
                if (flag == true){
                    table.reset();
                    state = State.WON;
                    updateDraw();
                }else{
                    table.reset();
                    state = State.TEST;
                    updateDraw();
                }
            }
        });
        this.back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                visibility(false);
                    Kroy.mainGameScreen.setGameState(GameScreen.GameScreenState.RUN);
                    return;
            }
        });
    }

    public void updateDraw(){
        switch(state) {
            case GAME1:
                table.row();
                table.add(new Image(new Texture("waterarrow.png")));
                table.add(pipes.get(0).getButton());
                table.add(pipes.get(1).getButton());
                table.add(pipes.get(2).getButton());
                table.add().width(320);


                table.row();
                table.add().width(160);
                table.add(pipes.get(3).getButton());
                table.add(pipes.get(4).getButton());
                table.add(pipes.get(5).getButton());
                table.add().width(320);


                table.row();
                table.add().width(160);
                table.add(pipes.get(6).getButton());
                table.add(pipes.get(7).getButton());
                table.add(pipes.get(8).getButton());
                table.add(new Image(new Texture("waterout.png")));
                table.add().width(160);

                table.row();
                table.add().width(160);
                table.add().width(128);
                table.add().width(128);
                table.add().width(128);
                table.add(check);
                table.add().width(160);


                table.setFillParent(true);
                stage.addActor(table);
                break;
            case WON:
                table.row();
                table.add(new Image(new Texture("minigamewin.png")));
                table.row();
                table.add(back).width(Kroy.width/3);
                break;
            case TEST:
                table.row();
                break;
        }
    }

    public void visibility(boolean state){
        this.table.setVisible(state);
    }

}
