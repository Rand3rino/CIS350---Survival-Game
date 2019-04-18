package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SurvivalGame;

/**
 * Screen to be displayed when the game is beaten
 * @author Randy Nguyen, Edited by Scott
 */
public class WinScreen implements Screen {

    /**
     * Viewport for the stage
     */
    private Viewport gamePort;

    /**
     * Stage overlay for labels to be set upon
     */
    private Stage stage;

    /**
     * Current state of Game
     */
    private Game game;

    /**
     * Music
     */
    private Music music;

    /**
     * Sound effects for when certain key is pressed
     */
    private Sound sfx;

    /**
     * Constructor of WinScreen class
     * @param game current state of game
     */
    public WinScreen(Game game) {
        this.game = game;
        gamePort = new StretchViewport(SurvivalGame.WIDTH, SurvivalGame.HEIGHT, new OrthographicCamera());
        stage = new Stage(gamePort, ((SurvivalGame) game).batch);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/8bitvictory.ogg"));
        music.setVolume(0.1f);
        music.setLooping(true);

        sfx = Gdx.audio.newSound(Gdx.files.internal("sounds/hits/swish_2.wav"));

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);


        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label titleLabel = new Label("Winner Winner, Discount Dinner", font);
        Label playAgainLabel = new Label("Press ENTER to Play Again", font);

        table.add(titleLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(50f);
        table.row();

        stage.addActor(table);
    }

    /**
     * Method to display the win screen
     */
    @Override
    public void show() {

    }

    /**
     * Method to render the assets of WinScreen
     * @param delta amount of time
     */
    @Override
    public void render(float delta) {
        music.play();
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            sfx.play();
            game.setScreen(new PlayScreen((SurvivalGame) game));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    /**
     * Method to resize the win screen
     * @param width amount of new width
     * @param height amount of new height
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Method to pause the screen
     */
    @Override
    public void pause() {

    }

    /**
     * Method to resume the screen
     */
    @Override
    public void resume() {

    }

    /**
     * Method to hide the screen
     */
    @Override
    public void hide() {

    }

    /**
     * Method to dispose of class assets
     */
    @Override
    public void dispose() {
        music.dispose();
        sfx.dispose();
    }
}