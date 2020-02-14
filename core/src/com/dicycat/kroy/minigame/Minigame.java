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
import com.dicycat.kroy.screens.GameScreen;
import com.dicycat.kroy.screens.MenuScreen;
import java.util.ArrayList;
import java.util.Random;

/**
 * A window to display the minigame.
 *
 * @author Bethany Gilmore - NP STUDIOS
 *
 */

public class Minigame {

    public Stage stage;
    public Table table = new Table();
    private SpriteBatch sb;
    private NinePatch patch;
    private NinePatchDrawable background;
    private Skin skin;

    private ArrayList<Pipe> pipes = new ArrayList<>(); //stores all of the pipes, which are each an individual piece of the puzzle.
    private ImageButton check;

    private TextButton back;
    private boolean inGame;

    private static Minigame.State state;

    /**
     *	Allows to have multiple versions of the Minigame without needing to create multiple Stages.
     */
    public static enum State{
        GAME1,
        GAME2,
        GAME3,
        WON,
    }

    public Minigame(Kroy game, boolean inGame){
        patch = new NinePatch(new Texture("loool.jpg"), 3, 3, 3, 3);
        background = new NinePatchDrawable(patch);
        skin = new Skin(Gdx.files.internal("uiskin.json")); //Allows for text to be written in the table
        check = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(
                "check.png"))));
        back = new TextButton("RETURN", skin);
        sb = game.batch;
        this.inGame = inGame;
        Viewport viewport = new ScreenViewport(new OrthographicCamera());
        stage = new Stage(viewport, sb);
        table.reset();
        table.setBackground(background);

        Random rand = new Random();
        Integer config = rand.nextInt(3);
        if (config == 0) {
            state = State.GAME1;
            pipes.add(new Pipe(1, 1));
            pipes.add(new Pipe(1, 0));
            pipes.add(new Pipe(1, 1));
            pipes.add(new Pipe(2, 0));
            pipes.add(new Pipe(3, 0));
            pipes.add(new Pipe(2, 2));
            pipes.add(new Pipe(1, 3));
            pipes.add(new Pipe(1, 2));
            pipes.add(new Pipe(1, 3));
        }else if (config == 1){
            state = State.GAME2;
            pipes.add(new Pipe(0, 1));
            pipes.add(new Pipe(2, 1));
            pipes.add(new Pipe(1, 1));
            pipes.add(new Pipe(3, 0));

            pipes.add(new Pipe(3, 0));
            pipes.add(new Pipe(2, 0));
            pipes.add(new Pipe(1, 2));
            pipes.add(new Pipe(3, 0));

            pipes.add(new Pipe(1, 0));
            pipes.add(new Pipe(2, 3));
            pipes.add(new Pipe(0, 1));
            pipes.add(new Pipe(1, 1));

            pipes.add(new Pipe(1, 3));
            pipes.add(new Pipe(0, 1));
            pipes.add(new Pipe(0, 1));
            pipes.add(new Pipe(2, 3));
        }else{
            state = State.GAME3;
            pipes.add(new Pipe(2, 1));
            pipes.add(new Pipe(2, 1));
            pipes.add(new Pipe(2, 1));
            pipes.add(new Pipe(1, 1));

            pipes.add(new Pipe(2, 0));
            pipes.add(new Pipe(3, 0));
            pipes.add(new Pipe(1, 2));
            pipes.add(new Pipe(0, 0));

            pipes.add(new Pipe(0, 0));
            pipes.add(new Pipe(1, 3));
            pipes.add(new Pipe(0, 1));
            pipes.add(new Pipe(1, 2));

            pipes.add(new Pipe(1, 3));
            pipes.add(new Pipe(0, 1));
            pipes.add(new Pipe(0, 1));
            pipes.add(new Pipe(0, 1));
        }

        updateDraw();
    }

    /**
     * Allows for the user to interact with the minigame pipes and buttons via clicking. Executes the necessary code
     * when this occurs.
     */
    public void clickCheck() {
        for (final Pipe pipe : pipes){
            pipe.getButton().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent input, float x, float y) {
                    table.reset();
                    pipe.rotate();
                    updateDraw();
                }
            });
        }// Adds a listener to each pipe in the puzzle, to allow the pipe to rotate on click.
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
                }
            }
        }); // Adds a listener to the check button. Upon being clicked the validity of each pipes configuration is tested.
        this.back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                visibility(false);
                    if (!inGame) {
                        Kroy.mainMenuScreen.state = MenuScreen.MenuScreenState.MAINMENU;
                    }
                    else {
                        Kroy.mainGameScreen.setGameState(GameScreen.GameScreenState.RUN);
                    }
                    return;
            }
        });// Returns the user to the screen they were in before the minigame was started.
    }

    /**
     * Draws the pipes in their current rotation once a click event occurs. Also draws the end of game screen
     * if the user correctly completes the puzzle.
     */
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

                // Draws the puzzle out in a table where each cell is a pipe in the puzzle.
                table.setFillParent(true);
                stage.addActor(table);
                break;
            case GAME2:
            case GAME3:
                table.row();
                table.add(new Image(new Texture("waterarrow.png")));
                table.add(pipes.get(0).getButton());
                table.add(pipes.get(1).getButton());
                table.add(pipes.get(2).getButton());
                table.add(pipes.get(3).getButton());
                table.add().width(160);

                table.row();
                table.add().width(160);
                table.add(pipes.get(4).getButton());
                table.add(pipes.get(5).getButton());
                table.add(pipes.get(6).getButton());
                table.add(pipes.get(7).getButton());
                table.add().width(160);

                table.row();
                table.add().width(160);
                table.add(pipes.get(8).getButton());
                table.add(pipes.get(9).getButton());
                table.add(pipes.get(10).getButton());
                table.add(pipes.get(11).getButton());
                table.add().width(160);

                table.row();
                table.add().width(160);
                table.add(pipes.get(12).getButton());
                table.add(pipes.get(13).getButton());
                table.add(pipes.get(14).getButton());
                table.add(pipes.get(15).getButton());
                table.add(new Image(new Texture("waterout.png")));

                table.row();
                table.add().width(160);
                table.add().width(128);
                table.add().width(128);
                table.add().width(128);
                table.add().width(128);
                table.add(check);

                table.setFillParent(true);
                stage.addActor(table);
                break;

            case WON:
                table.row();
                table.add(new Image(new Texture("minigamewin.png")));
                table.row();
                table.add(back).width(Kroy.width/3);
                // Displays the win screen, including a button to return out of the minigame.

                table.setFillParent(true);
                stage.addActor(table);
                break;
        }
    }

    /**
     * Determines if the minigame is visible to the user
     * @param state true if visible, false if not
     */
    public void visibility(boolean state){
        this.table.setVisible(state);
    }

}
